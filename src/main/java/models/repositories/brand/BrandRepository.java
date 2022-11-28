package models.repositories.brand;

import models.entities.Brand;
import models.entities.Product;
import models.view_models.brands.BrandCreateRequest;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandUpdateRequest;
import models.view_models.brands.BrandViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.constants.BRAND_STATUS;
import utils.constants.PRODUCT_STATUS;

import java.util.ArrayList;
import java.util.List;

public class BrandRepository implements IBrandRepository{
    private static BrandRepository instance = null;

    public static BrandRepository getInstance(){
        if (instance == null)
            instance = new BrandRepository();
        return instance;
    }
    @Override
    public int insert(BrandCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Brand brand = new Brand();

        brand.setBrandName(request.getBrandName());
        brand.setOrigin(request.getOrigin());
        brand.setImage(FileUtil.encodeBase64(request.getImage()));
        brand.setStatus(request.getStatus());
        int brandId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(brand);
            brandId = brand.getBrandId();

            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return brandId;
    }

    @Override
    public boolean update(BrandUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Brand brand = session.find(Brand.class, request.getBrandId());

        brand.setBrandName(request.getBrandName());
        brand.setOrigin(request.getOrigin());
        if(!request.getImage().getSubmittedFileName().equals("")){
            brand.setImage(FileUtil.encodeBase64(request.getImage()));
        }
        if(request.getStatus() == BRAND_STATUS.IN_ACTIVE){
            Query q1 = session.createQuery("select productId from Product where brand.brandId=:s1");
            q1.setParameter("s1",brand.getBrandId());
            List<Integer> productIds = q1.list();
            for(Integer id:productIds){
                Product subProduct = session.find(Product.class, id);
                if(subProduct.getStatus() != PRODUCT_STATUS.SUSPENDED)
                    return false;
            }
        }
        brand.setStatus(request.getStatus());
        return HibernateUtils.merge(brand);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Brand brand = session.find(Brand.class, entityId);
        Query q3 = session.createQuery("select productId from Product where brand.brandId=:s1");
        q3.setParameter("s1",brand.getBrandId());
        List<Integer> productIds = q3.list();
        for(Integer id:productIds){
            Product subProduct = session.find(Product.class, id);
            if(subProduct.getStatus() != PRODUCT_STATUS.SUSPENDED)
                return false;
        }
        brand.setStatus(BRAND_STATUS.IN_ACTIVE);
        session.close();
        return HibernateUtils.merge(brand);
    }
    private String getStatus(int i){
        String status = "";
        switch (i){
            case BRAND_STATUS.ACTIVE:
                status = "Đang dùng";
                break;
            case BRAND_STATUS.IN_ACTIVE:
                status = "Đã xoá";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private BrandViewModel getBrandViewModel(Brand brand, Session session){
        BrandViewModel brandViewModel = new BrandViewModel();

        brandViewModel.setBrandId(brand.getBrandId());
        brandViewModel.setBrandName(brand.getBrandName());
        brandViewModel.setOrigin(brand.getOrigin());
        brandViewModel.setImage(brand.getImage());
        brandViewModel.setStatus(brand.getStatus());
        brandViewModel.setStatusCode(getStatus(brand.getStatus()));
        Query q = session.createQuery("select sum(quantity) from Product p where p.brand.brandId=:s1");
        q.setParameter("s1",brand.getBrandId());
        Object o = q.getSingleResult();
        brandViewModel.setTotalProducts(o == null ? 0 : (long)o);

        return brandViewModel;
    }
    @Override
    public BrandViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Brand brand = session.find(Brand.class, entityId);

        BrandViewModel brandViewModel = getBrandViewModel(brand, session);
        session.close();

        return brandViewModel;
    }

    @Override
    public ArrayList<BrandViewModel> retrieveAll(BrandGetPagingRequest request) {
        ArrayList<BrandViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Brand", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Brand> brands = q.list();

        for(Brand brand:brands){
            BrandViewModel v = getBrandViewModel(brand, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
