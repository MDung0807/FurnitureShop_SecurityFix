package models.repositories.wish;

import models.entities.Product;
import models.entities.WishItem;
import models.entities.WishList;
import models.services.product.ProductService;
import models.view_models.products.ProductViewModel;
import models.view_models.wish_items.WishItemCreateRequest;
import models.view_models.wish_items.WishItemGetPagingRequest;
import models.view_models.wish_items.WishItemUpdateRequest;
import models.view_models.wish_items.WishItemViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.constants.PRODUCT_STATUS;

import java.util.ArrayList;
import java.util.List;

public class WishRepository implements IWishRepository {
    private static WishRepository instance = null;
    public static WishRepository getInstance(){
        if(instance == null)
            instance = new WishRepository();
        return instance;
    }
    @Override
    public int insert(WishItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        WishList wishList = session.find(WishList.class, request.getWishId());
        Product product = session.find(Product.class, request.getProductId());
        WishItem wishItem = new WishItem();
        wishItem.setProduct(product);
        wishItem.setWishList(wishList);
        wishItem.setDateAdded(DateUtils.dateTimeNow());
        wishItem.setStatus(request.getStatus());

        int wishItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(wishItem);
            wishItemId = wishItem.getWishItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return wishItemId;
    }

    @Override
    public boolean update(WishItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        WishItem wishItem = session.find(WishItem.class, request.getWishListItemId());

        wishItem.setStatus(request.getStatus());

        return HibernateUtils.merge(wishItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        WishItem wishItem = session.find(WishItem.class, entityId);
        return HibernateUtils.remove(wishItem);
    }
    private String getProductStatus(int i){
        String status = "";
        switch (i){
            case PRODUCT_STATUS.IN_STOCK:
                status = "Còn hàng";
                break;
            case PRODUCT_STATUS.OUT_STOCK:
                status = "Hết hàng";
                break;
            case PRODUCT_STATUS.SUSPENDED:
                status = "Ngưng kinh doanh";
                break;
            default:
                status = "Không xác định";
                break;
        }
        return status;
    }
    private WishItemViewModel getWishListItemViewModel(WishItem wishItem, Session session){
        WishItemViewModel wishListItemViewModel = new WishItemViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveProductById(wishItem.getProduct().getProductId());
        wishListItemViewModel.setWishItemId(wishItem.getWishItemId());
        wishListItemViewModel.setWishId(wishItem.getWishList().getWishListId());
        wishListItemViewModel.setProductImage(product.getImage());
        wishListItemViewModel.setProductName(product.getName());
        wishListItemViewModel.setUnitPrice(product.getPrice());
        wishListItemViewModel.setStatus(wishItem.getStatus());
        wishListItemViewModel.setDateAdded(wishItem.getDateAdded());
        wishListItemViewModel.setProductId(product.getProductId());
        wishListItemViewModel.setProductStatus(getProductStatus(product.getStatus()));
        return wishListItemViewModel;
    }
    @Override
    public WishItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        WishItem wishItem = session.find(WishItem.class, entityId);

        WishItemViewModel wishListItemViewModel = getWishListItemViewModel(wishItem, session);
        session.close();

        return wishListItemViewModel;
    }

    @Override
    public ArrayList<WishItemViewModel> retrieveAll(WishItemGetPagingRequest request) {
        ArrayList<WishItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("WishItem", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<WishItem> wishItems = q.list();

        for(WishItem wishItem : wishItems){
            WishItemViewModel v = getWishListItemViewModel(wishItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public int getWishIdByUserId(int userId) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("select wishListId from WishList where user.userId =:s1");
        q.setParameter("s1",userId);
        Object o = q.getSingleResult();
        session.close();
        if(o == null)
            return -1;
        return (int)o;
    }

    @Override
    public ArrayList<WishItemViewModel> retrieveWishListByUserId(int userId) {
        ArrayList<WishItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int wishId = getWishIdByUserId(userId);
        Query q = session.createQuery("from WishItem where wishList.wishListId=:s1");
        q.setParameter("s1", wishId);
        List<WishItem> wishItems = q.list();
        for(WishItem wishItem : wishItems){
            WishItemViewModel v = getWishListItemViewModel(wishItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
