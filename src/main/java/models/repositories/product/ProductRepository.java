package models.repositories.product;

import models.entities.Brand;
import models.entities.Category;
import models.entities.Product;
import models.entities.ProductImage;
import models.repositories.category.CategoryRepository;
import models.repositories.review.ReviewRepository;
import models.services.product.ProductService;
import models.services.review.ReviewService;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;
import models.view_models.review_items.ReviewItemViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.HtmlClassUtils;
import utils.constants.PRODUCT_STATUS;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository{
    private static ProductRepository instance = null;
    public static ProductRepository getInstance() {
        if(instance == null)
            instance = new ProductRepository();
        return instance;
    }
    @Override
    public int insert(ProductCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Category category = session.find(Category.class, request.getCategoryId());
        Brand brand = session.find(Brand.class, request.getBrandId());
        Product product = new Product();
        if(request.getStatus() == PRODUCT_STATUS.OUT_STOCK || request.getStatus() == PRODUCT_STATUS.SUSPENDED){
            request.setQuantity(0);
        }
        if(request.getQuantity() == 0){
            request.setStatus(PRODUCT_STATUS.OUT_STOCK);
        }
        product.setName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setOrigin(request.getOrigin());
        product.setDateCreated(DateUtils.dateTimeNow());
        product.setStatus(request.getStatus());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);
        product.setBrand(brand);

        int productId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(product);
            productId = product.getProductId();
            if(productId == -1){
                return -1;
            }
            if(request.getImage() != null && !request.getImage().getSubmittedFileName().equals("")){
                ProductImage img = new ProductImage();
                img.setDefault(true);
                img.setProduct(product);
                img.setImage(FileUtil.encodeBase64(request.getImage()));
                session.persist(img);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        session.close();
        return productId;
    }

    @Override
    public boolean update(ProductUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Product product = session.find(Product.class, request.getProductId());
        Category category = session.find(Category.class, request.getCategoryId());
        Brand brand = session.find(Brand.class, request.getBrandId());
        if(request.getQuantity() == 0){
            if(request.getStatus() != PRODUCT_STATUS.SUSPENDED)
                request.setStatus(PRODUCT_STATUS.OUT_STOCK);
        }
        if(request.getStatus() == PRODUCT_STATUS.OUT_STOCK || request.getStatus() == PRODUCT_STATUS.SUSPENDED){
            request.setQuantity(0);
        }
        //BigDecimal prevPrice = product.getPrice();
        product.setName(request.getProductName());
        product.setOrigin(request.getOrigin());
        product.setDescription(request.getDescription());
        product.setStatus(request.getStatus());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        if(request.getCategoryId() > 0)
            product.setCategory(category);
        if(request.getBrandId() > 0)
            product.setBrand(brand);

        try {
            tx = session.beginTransaction();
            session.merge(product);

            if(request.getImage() != null && !request.getImage().getSubmittedFileName().equals("")){

                Query q = session.createQuery("from ProductImage where product.productId=:s1 and isDefault = true");
                q.setParameter("s1", product.getProductId());

                ProductImage image = (ProductImage)q.getSingleResult();
                image.setImage(FileUtil.encodeBase64(request.getImage()));

                session.merge(image);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Product product = session.find(Product.class, entityId);
        product.setStatus(PRODUCT_STATUS.SUSPENDED);
        product.setQuantity(0);
        session.close();
        return HibernateUtils.merge(product);
    }
    private String getStatus(int i){
        String status = "";
        switch (i){
            case PRODUCT_STATUS.IN_STOCK:
                status = "Còn hàng";
                break;
            case PRODUCT_STATUS.OUT_STOCK:
                status = "Hết hàng";
                break;
            case PRODUCT_STATUS.SUSPENDED:
                status = "Ngừng kinh doanh";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private ProductViewModel getProductViewModel(Product product, Session session){
        Query q1 = session.createQuery("select image from ProductImage where product.productId =:s1 and isDefault = true");
        q1.setParameter("s1", product.getProductId());
        String image = q1.getSingleResult().toString();
        Query q2 = session.createQuery("select brandName from Brand where brandId =:s1" );
        q2.setParameter("s1", product.getBrand().getBrandId());
        String brandName = q2.getSingleResult().toString();
        Query q3 = session.createQuery("select categoryName from Category where categoryId =:s1" );
        q3.setParameter("s1", product.getCategory().getCategoryId());
        String categoryName = q3.getSingleResult().toString();
        Query q4 = session.createQuery("select productImageId from ProductImage where  product.productId=:s1 and isDefault = false");
        q4.setParameter("s1", product.getProductId());
        List<Integer> subProductImageIds = q4.list();
        Query q5 = session.createQuery("select sum(quantity) from OrderItem  where product.productId=:s1");
        q5.setParameter("s1",product.getProductId());
        Object res = q5.getSingleResult();
        long totalPurchased = res == null ? 0 : (long)res;

        ProductViewModel productViewModel = new ProductViewModel();

        productViewModel.setProductId(product.getProductId());
        productViewModel.setName(product.getName());
        productViewModel.setDescription(product.getDescription());
        productViewModel.setOrigin(product.getOrigin());
        productViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(product.getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        productViewModel.setStatus(product.getStatus());
        productViewModel.setStatusCode(getStatus(product.getStatus()));
        productViewModel.setPrice(product.getPrice());
        productViewModel.setQuantity(product.getQuantity());
        productViewModel.setImage(image);
        productViewModel.setBrandName(brandName);
        productViewModel.setCategoryName(categoryName);
        productViewModel.setCategoryId(product.getCategory().getCategoryId());
        productViewModel.setBrandId(product.getBrand().getBrandId());
        productViewModel.setTotalPurchased(totalPurchased);
        productViewModel.setStatusClass(HtmlClassUtils.generateProductStatusClass(product.getStatus()));
        List<ProductImageViewModel> productImageViewModels = new ArrayList<>();
        subProductImageIds.forEach(id -> {
            productImageViewModels.add(ProductService.getInstance().retrieveImageById(id));
        });
        productViewModel.setProductImages(productImageViewModels);
        ArrayList<ReviewItemViewModel> productReviews = ReviewRepository.getInstance().retrieveByProductId(product.getProductId());
        productReviews.removeIf(x -> x.getStatus() == 0);
        int totalRating = productReviews.stream().mapToInt(ReviewItemViewModel::getRating).sum();
        long avgRating = Math.round((totalRating * 1.0)/productReviews.size());

        productViewModel.setProductReviews(productReviews);
        productViewModel.setAvgRating(avgRating);
        return productViewModel;
    }
    @Override
    public ProductViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Product product = session.find(Product.class, entityId);

        ProductViewModel productViewModel = getProductViewModel(product, session);
        session.close();

        return productViewModel;
    }

    @Override
    public ArrayList<ProductViewModel> retrieveAll(ProductGetPagingRequest request) {
        ArrayList<ProductViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Product", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Product> products = q.list();

        for(Product product:products){
            ProductViewModel v = getProductViewModel(product, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public boolean updateQuantity(int productId, int quantity) {
        Session session = HibernateUtils.getSession();
        Product product = session.find(Product.class, productId);
        product.setQuantity(product.getQuantity() - quantity);
        session.close();
        if(product.getQuantity() == 0)
            product.setStatus(PRODUCT_STATUS.OUT_STOCK);
        else if(product.getQuantity() < 0){
            return false;
        }
        return HibernateUtils.merge(product);
    }

    @Override
    public int getQuantity(int productId) {
        Session s = HibernateUtils.getSession();
        Product p = s.find(Product.class, productId);
        return p.getQuantity();
    }

    @Override
    public int insertImage(ProductImageCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Product product = session.find(Product.class, request.getProductId());
        List<Part> files = request.getImages();
        try {
            tx = session.beginTransaction();
            for(Part f: files){
                if(f != null && !f.getSubmittedFileName().equals("")){
                    ProductImage productImage = new ProductImage();

                    productImage.setProduct(product);
                    productImage.setDefault(false);
                    productImage.setImage(FileUtil.encodeBase64(f));

                    session.persist(productImage);
                }
            }
            tx.commit();
        }
        catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return -1;
        }
        return 1;
    }

    @Override
    public boolean updateImage(ProductImageUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        session.close();
        try {
            tx = session.beginTransaction();
            request.getProductImages().forEach((id, f) -> {
                ProductImage productImage = session.find(ProductImage.class, id);
                productImage.setDefault(false);
                if(f!= null && !f.getSubmittedFileName().equals(""))
                    productImage.setImage(FileUtil.encodeBase64(f));

                session.merge(productImage);
            });
            tx.commit();
        }
        catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteImage(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImage img = session.find(ProductImage.class, entityId);
        session.close();
        return HibernateUtils.remove(img);
    }
    private ProductImageViewModel getProductImageViewModel(ProductImage productImage){
        ProductImageViewModel productImageViewModel = new ProductImageViewModel();

        productImageViewModel.setId(productImage.getProductImageId());
        productImageViewModel.setDefault(productImage.getDefault());
        productImageViewModel.setImage(productImage.getImage());
        productImageViewModel.setProductId(productImage.getProduct().getProductId());

        return productImageViewModel;
    }
    @Override
    public ProductImageViewModel retrieveImageById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImage productImage = session.find(ProductImage.class, entityId);

        ProductImageViewModel productImageViewModel = getProductImageViewModel(productImage);
        session.close();
        return productImageViewModel;
    }

    @Override
    public ArrayList<ProductImageViewModel> retrieveAllImage(ProductImageGetPagingRequest request) {
        ArrayList<ProductImageViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();

        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("ProductImage", request);

        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<ProductImage> productImages = q.list();

        for(ProductImage productImg:productImages){
            ProductImageViewModel v = getProductImageViewModel(productImg);
            list.add(v);
        }
        session.close();
        return list;
    }
}
