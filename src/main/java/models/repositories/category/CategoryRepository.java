package models.repositories.category;

import models.entities.Category;
import models.entities.Product;
import models.view_models.categories.CategoryCreateRequest;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryUpdateRequest;
import models.view_models.categories.CategoryViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.constants.BRAND_STATUS;
import utils.constants.CATEGORY_STATUS;
import utils.constants.PRODUCT_STATUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryRepository implements ICategoryRepository{
    private static CategoryRepository instance = null;
    public static CategoryRepository getInstance(){
        if(instance == null)
            instance = new CategoryRepository();
        return instance;
    }
    @Override
    public int insert(CategoryCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Category category = new Category();

        category.setCategoryName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setImage(FileUtil.encodeBase64(request.getImage()));
        category.setStatus(request.getStatus());
        int categoryId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(category);
            categoryId = category.getCategoryId();

            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return categoryId;
    }

    @Override
    public boolean update(CategoryUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Category category = session.find(Category.class, request.getCategoryId());

        category.setCategoryName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setStatus(request.getStatus());
        if(!request.getImage().getSubmittedFileName().equals(""))
            category.setImage(FileUtil.encodeBase64(request.getImage()));

        Query q2 = session.createQuery("select categoryId from Category where parentCategoryId=:s1");
        q2.setParameter("s1",category.getCategoryId());
        List<Integer> categoryIds = q2.list();
        session.close();
        for(Integer id:categoryIds){
            Category sub = session.find(Category.class, id);
            if(sub.getStatus() != CATEGORY_STATUS.IN_ACTIVE)
                return false;
        }
        if(request.getStatus() == CATEGORY_STATUS.IN_ACTIVE){
            Query q3 = session.createQuery("select productId from Product where category.categoryId=:s1");
            q3.setParameter("s1",category.getCategoryId());
            List<Integer> productIds = q3.list();
            for(Integer id:productIds){
                Product subProduct = session.find(Product.class, id);
                if(subProduct.getStatus() != PRODUCT_STATUS.SUSPENDED)
                    return false;
            }
        }
        return HibernateUtils.merge(category);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Category category = session.find(Category.class, entityId);

        Query q2 = session.createQuery("select categoryId from Category where parentCategoryId=:s1");
        q2.setParameter("s1",category.getCategoryId());
        List<Integer> categoryIds = q2.list();

        for(Integer id:categoryIds){
            Category sub = session.find(Category.class, id);
            if(sub.getStatus() != CATEGORY_STATUS.IN_ACTIVE)
                return false;
        }

        Query q3 = session.createQuery("select productId from Product where category.categoryId=:s1");
        q3.setParameter("s1",category.getCategoryId());
        List<Integer> productIds = q3.list();
        for(Integer id:productIds){
            Product subProduct = session.find(Product.class, id);
            if(subProduct.getStatus() != PRODUCT_STATUS.SUSPENDED)
                return false;
        }
        category.setStatus(CATEGORY_STATUS.IN_ACTIVE);
        return HibernateUtils.merge(category);
    }
    private String getStatus(int i){
        String status = "";
        switch (i){
            case CATEGORY_STATUS.ACTIVE:
                status = "Đang dùng";
                break;
            case CATEGORY_STATUS.IN_ACTIVE:
                status = "Đã xoá";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private CategoryViewModel getCategoryViewModel(Category category, Session session){
        CategoryViewModel categoryViewModel = new CategoryViewModel();

        categoryViewModel.setCategoryId(category.getCategoryId());
        categoryViewModel.setName(category.getCategoryName());

        String parentCategoryName = "";
        if(category.getParentCategoryId() > 0) {
            Query q1 = session.createQuery("select categoryName from Category where categoryId =:s1");
            q1.setParameter("s1", category.getParentCategoryId());
            parentCategoryName = q1.getSingleResult() == null ? "" : q1.getSingleResult().toString();
        }
        categoryViewModel.setParentCategoryId(category.getParentCategoryId());
        categoryViewModel.setParentCategoryName(parentCategoryName);
        categoryViewModel.setStatus(category.getStatus());
        categoryViewModel.setStatusCode(getStatus(category.getStatus()));

        categoryViewModel.setImage(category.getImage());
        categoryViewModel.setDescription(category.getDescription());

        Query q2 = session.createQuery("select sum(quantity) from Product p where p.category.categoryId=:s1");
        q2.setParameter("s1",category.getCategoryId());
        Object o2 = q2.getSingleResult();
        categoryViewModel.setTotalProduct(o2 == null ? 0 : (long)o2);

        Query q3 = session.createQuery("select sum(o.quantity) from OrderItem o inner join Product p on o.product.productId = p.productId where p.category.categoryId =:s1");
        q3.setParameter("s1",category.getCategoryId());
        Object o3 = q3.getSingleResult();
        categoryViewModel.setTotalSell(o3 == null ? 0 : (long)o3);


        Query q4 = session.createQuery("from Category where parentCategoryId=:s1");
        q4.setParameter("s1",category.getCategoryId());
        List<Category> categories = q4.list();
        if(categories.size() == 0){
            return categoryViewModel;
        }
        List<CategoryViewModel> subCategories = new ArrayList<>();
        for (Category c:categories){
            CategoryViewModel cate = getCategoryViewModel(c, session);
            subCategories.add(cate);
        }
        categoryViewModel.setSubCategories(subCategories);
        return categoryViewModel;
    }
    @Override
    public CategoryViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Category category = session.find(Category.class, entityId);

        CategoryViewModel categoryViewModel = getCategoryViewModel(category, session);
        session.close();

        return categoryViewModel;
    }

    @Override
    public ArrayList<CategoryViewModel> retrieveAll(CategoryGetPagingRequest request) {
        ArrayList<CategoryViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Category", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Category> categories = q.list();

        for(Category category:categories){
            CategoryViewModel v = getCategoryViewModel(category, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public HashMap<Integer, String> getParentCategory() {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from Category where parentCategoryId = 0");
        List<Category> l = q.list();
        HashMap<Integer, String> parentCategory = new HashMap<>();
        l.forEach(category ->
                parentCategory.put(category.getCategoryId(), category.getCategoryName())
        );

        return parentCategory;
    }
}
