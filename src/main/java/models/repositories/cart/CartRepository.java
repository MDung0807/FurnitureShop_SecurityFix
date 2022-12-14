package models.repositories.cart;

import models.entities.Cart;
import models.entities.CartItem;
import models.entities.Product;
import models.repositories.product.ProductRepository;
import models.services.cart.CartService;
import models.services.product.ProductService;
import models.view_models.cart_items.CartItemCreateRequest;
import models.view_models.cart_items.CartItemGetPagingRequest;
import models.view_models.cart_items.CartItemUpdateRequest;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.products.ProductViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.constants.PRODUCT_STATUS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartRepository implements ICartRepository {
    private static CartRepository instance = null;
    public static CartRepository getInstance(){
        if(instance == null)
            instance = new CartRepository();
        return instance;
    }
    @Override
    public int insert(CartItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Cart cart = session.find(Cart.class, request.getCartId());
        Product product = session.find(Product.class, request.getProductId());
        CartItem cartItem = new CartItem();

        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setDateAdded(DateUtils.dateTimeNow());

        Query q = session.createQuery("select price from Product where productId =:s1");
        q.setParameter("s1",request.getProductId());
        BigDecimal unitPrice = (BigDecimal)q.getSingleResult();
        cartItem.setStatus(request.getStatus());

        int cartItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(cartItem);
            cartItemId = cartItem.getCartItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return cartItemId;
    }

    @Override
    public boolean update(CartItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        if(request.getQuantity() < 0)
            return false;
        CartItem cartItem = session.find(CartItem.class, request.getCartItemId());
        ProductViewModel product = ProductRepository.getInstance().retrieveById(cartItem.getProduct().getProductId());
        if(request.getQuantity() > product.getQuantity())
            return false;

        cartItem.setQuantity(request.getQuantity());
        session.close();
        return HibernateUtils.merge(cartItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        CartItem cartItem = session.find(CartItem.class, entityId);
        session.close();
        return HibernateUtils.remove(cartItem);
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
    private CartItemViewModel getCartItemViewModel(CartItem cartItem, Session session){
        CartItemViewModel cartItemViewModel = new CartItemViewModel();

        ProductViewModel product = ProductService.getInstance().retrieveProductById(cartItem.getProduct().getProductId());
        if(cartItem.getQuantity() > product.getQuantity()){
            CartItemUpdateRequest req = new CartItemUpdateRequest();
            req.setCartItemId(cartItem.getCartItemId());
            req.setQuantity(product.getQuantity());
            update(req);
        }
        cartItemViewModel.setCartItemId(cartItem.getCartItemId());
        cartItemViewModel.setDateAdded(cartItem.getDateAdded());
        cartItemViewModel.setQuantity(cartItem.getQuantity());
        cartItemViewModel.setProductImage(product.getImage());
        cartItemViewModel.setProductName(product.getName());
        cartItemViewModel.setCartId(cartItem.getCart().getCartId());
        cartItemViewModel.setUnitPrice(product.getPrice());
        cartItemViewModel.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItemViewModel.setProductId(cartItem.getProduct().getProductId());
        cartItemViewModel.setStatus(cartItem.getStatus());
        cartItemViewModel.setProductStatus(getProductStatus(product.getStatus()));

        return cartItemViewModel;
    }
    @Override
    public CartItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        CartItem cartItem = session.find(CartItem.class, entityId);

        CartItemViewModel cartItemViewModel = getCartItemViewModel(cartItem, session);
        session.close();

        return cartItemViewModel;
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveAll(CartItemGetPagingRequest request) {

        ArrayList<CartItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("CartItem", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<CartItem> cartItems = q.list();

        for(CartItem cartItem:cartItems){
            CartItemViewModel v = getCartItemViewModel(cartItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveCartByUserId(int userId) {
        ArrayList<CartItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();

        int cartId = getCartIdByUserId(userId);

        Query q = session.createQuery("from CartItem where cart.cartId=:s1");

        q.setParameter("s1", cartId);
        List<CartItem> cartItems = q.list();
        for(CartItem cartItem : cartItems){
            list.add(getCartItemViewModel(cartItem, session));
        }
        session.close();
        return list;
    }

    @Override
    public int getCartIdByUserId(int userId) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("select cartId from Cart where user.userId =:s1");
        q.setParameter("s1",userId);
        Object o = q.getSingleResult();
        session.close();
        if(o == null)
            return -1;
        return (int)o;
    }

    @Override
    public CartItemViewModel getCartItemContain(int cartId, int productId) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from CartItem where cart.cartId=:s1 and product.productId=:s2");
        q.setParameter("s1", cartId);
        q.setParameter("s2",productId);
        Object o = null;
        try {
            o = q.getSingleResult();
        }catch(Exception e){
            return null;
        }
        if(o == null)
            return null;
        return getCartItemViewModel((CartItem) o, session);
    }

    @Override
    public boolean deleteCartByUserId(int userId) {
        int cartId = getCartIdByUserId(userId);
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from CartItem where cart.cartId=:s1");
        q.setParameter("s1", cartId);
        List<CartItem> cartItems = q.list();
        for(CartItem c:cartItems){
            boolean res = delete(c.getCartItemId());
            if(!res)
                return false;
        }
        return true;
    }

    @Override
    public int canUpdateQuantity(int cartItemId, int quantity) {
        CartItemViewModel cartItem = CartService.getInstance().retrieveCartItemById(cartItemId);
        ProductViewModel product = ProductService.getInstance().retrieveProductById(cartItem.getProductId());
        if(product.getQuantity() < quantity) {
            CartItemUpdateRequest req = new CartItemUpdateRequest();
            req.setQuantity(product.getQuantity());
            req.setCartItemId(cartItemId);
            update(req);
            return product.getQuantity();
        }
        return -1;
    }

    @Override
    public void updateQuantityByProductId(int productId, int quantity) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("select cartItemId from CartItem where product.productId=:s1");
        q.setParameter("s1",productId);

        List<Integer> cartItemIds = q.list();
        session.close();
        for (int id:cartItemIds){
            session = HibernateUtils.getSession();
            CartItem c = session.find(CartItem.class, id);
            c.setQuantity(quantity);
            session.close();
            HibernateUtils.merge(c);
        }
    }

    @Override
    public String addProductToCart(int productId, int quantity, int userId) {
        int cartId = CartService.getInstance().getCartIdByUserId(userId);
        String responseStatus;
        int count = -1;
        CartItemViewModel cartItem = CartService.getInstance().getCartItemContain(cartId, productId);
        ProductViewModel product = ProductService.getInstance().retrieveProductById(productId);
        if(product.getQuantity() > 0) {
            if (cartItem != null) {
                CartItemUpdateRequest updateReq = new CartItemUpdateRequest();
                updateReq.setCartItemId(cartItem.getCartItemId());
                updateReq.setQuantity(cartItem.getQuantity() + 1);
                updateReq.setStatus(cartItem.getStatus());
                count = CartService.getInstance().updateCartItem(updateReq) ? 1 : 0;
                if(count > 0)
                    responseStatus = "repeat";
                else
                    responseStatus = "error";
            } else {
                CartItemCreateRequest createReq = new CartItemCreateRequest();
                createReq.setCartId(cartId);
                createReq.setProductId(productId);
                createReq.setQuantity(quantity);
                createReq.setStatus(1);

                count = CartService.getInstance().insertCartItem(createReq);
                if (count > 0) {
                    responseStatus = "success";
                }
                else{
                    responseStatus = "error";
                }
            }
        }else{
            responseStatus = "expired";
        }

        return responseStatus;
    }

    @Override
    public BigDecimal getTotalCartItemPriceByUserId(int userId) {

        ArrayList<CartItemViewModel> cartItems = retrieveCartByUserId(userId);
        BigDecimal totalItemPrice = BigDecimal.valueOf(0);

        for(CartItemViewModel c:cartItems){
            totalItemPrice = totalItemPrice.add(c.getTotalPrice());
        }

        return totalItemPrice;
    }
}
