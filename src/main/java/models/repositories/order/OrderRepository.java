package models.repositories.order;

import com.paypal.base.rest.PayPalRESTException;
import models.entities.*;
import models.repositories.cart.CartRepository;
import models.repositories.discount.DiscountRepository;
import models.repositories.product.ProductRepository;
import models.services.cart.CartService;
import models.services.discount.DiscountService;
import models.services.mail.MailJetService;
import models.services.order.OrderService;
import models.services.paypal.PayPalService;
import models.services.product.ProductService;
import models.view_models.cart_items.CartItemUpdateRequest;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.discounts.DiscountViewModel;
import models.view_models.order_items.OrderItemCreateRequest;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.orders.*;
import models.view_models.products.ProductViewModel;
import models.view_models.users.UserViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.HtmlClassUtils;
import utils.constants.ORDER_PAYMENT;
import utils.constants.ORDER_STATUS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRepository implements IOrderRepository{
    private static OrderRepository instance = null;
    public static OrderRepository getInstance(){
        if(instance == null)
            instance = new OrderRepository();
        return instance;
    }
    @Override
    public int insert(OrderCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Order order = new Order();
        order.setAddress(request.getAddress());
        order.setDateCreated(DateUtils.dateTimeNow());
        order.setPayment(request.getPayment());
        order.setStatus(request.getStatus());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setName(request.getName());
        if(request.getDiscountId() > 0) {
            Discount discount = session.find(Discount.class, request.getDiscountId());
            order.setDiscount(discount);
        }
        User user = session.find(User.class, request.getUserId());
        order.setUser(user);
        if(request.getPayment() == ORDER_PAYMENT.PAYPAL)
            order.setDateDone(DateUtils.dateTimeNow());
        order.setShipping(request.getShipping());
        order.setTotalItemPrice(request.getTotalItemPrice());

        order.setTotalPrice(request.getTotalPrice());

        int orderId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(order);
            orderId = order.getOrderId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return orderId;
    }

    @Override
    public boolean update(OrderUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Order order = session.find(Order.class, request.getOrderId());
        if(order.getStatus() == ORDER_STATUS.DELIVERED && request.getStatus() != ORDER_STATUS.RETURN)
            return false;
        order.setStatus(request.getStatus());
        if(request.getStatus() == ORDER_STATUS.DELIVERED && order.getPayment() == ORDER_PAYMENT.COD){
            order.setPayment(ORDER_PAYMENT.PAYPAL);
            order.setDateDone(DateUtils.dateTimeNow());
        }
        session.close();
        return HibernateUtils.merge(order);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Order order = session.find(Order.class, entityId);
        session.close();
        return HibernateUtils.remove(order);
    }

    private String getStatus(int i){
        String status = "";
        switch (i){
            case ORDER_STATUS.PENDING:
                status = "Đang đợi";
                break;
            case ORDER_STATUS.READY_TO_SHIP:
                status = "Sẵn sàng chuyển đi";
                break;
            case ORDER_STATUS.ON_THE_WAY:
                status = "Đang giao";
                break;
            case ORDER_STATUS.DELIVERED:
                status = "Đã giao thành công";
                break;
            case ORDER_STATUS.CANCEL:
                status = "Đã huỷ";
                break;
            case ORDER_STATUS.RETURN:
                status = "Hoàn trả";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private String getPayment(int i){
        String payment = "";
        switch (i){
            case ORDER_PAYMENT.PAYPAL:
                payment = "PAYPAL";
                break;
            case ORDER_PAYMENT.COD:
                payment = "COD";
                break;
            default:
                payment = "Undefined";
                break;
        }
        return payment;
    }
    private OrderViewModel getOrderViewModel(Order order, Session session){
        OrderViewModel orderViewModel = new OrderViewModel();
        DiscountViewModel discount = null;
        if(order.getDiscount() != null)
            discount = DiscountService.getInstance().retrieveDiscountById(order.getDiscount().getDiscountId());
        Query q = session.createQuery("from User where id =:s1");
        q.setParameter("s1",order.getUser().getUserId());
        User user = (User)q.getSingleResult();

        orderViewModel.setUserName(user.getUsername());
        orderViewModel.setUserAddress(user.getAddress());
        orderViewModel.setUserPhone(user.getPhone());

        orderViewModel.setOrderId(order.getOrderId());
        orderViewModel.setAddress(order.getAddress());
        if(order.getDateCreated() != null)
            orderViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(order.getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        orderViewModel.setStatus(order.getStatus());
        orderViewModel.setStatusCode(getStatus(order.getStatus()));
        orderViewModel.setEmail(order.getEmail());
        orderViewModel.setPhone(order.getPhone());
        orderViewModel.setName(order.getName());
        if(order.getDiscount() != null) {
            orderViewModel.setDiscountId(order.getDiscount().getDiscountId());
            orderViewModel.setDiscountCode(discount.getDiscountCode());
            orderViewModel.setDiscountValue(discount.getDiscountValue());
        }
        orderViewModel.setUserId(order.getUser().getUserId());
        if(order.getDateDone() != null)
            orderViewModel.setDateDone(DateUtils.dateTimeToStringWithFormat(order.getDateDone(),"yyyy-MM-dd HH:mm:ss"));
        orderViewModel.setShipping(order.getShipping());
        orderViewModel.setTotalItemPrice(order.getTotalItemPrice());
        orderViewModel.setTotalPrice(order.getTotalPrice());
        orderViewModel.setPayment(order.getPayment());
        orderViewModel.setPaymentMethod(getPayment(order.getPayment()));
        orderViewModel.setTotalItem(HibernateUtils.count("OrderItem","orderId = " + order.getOrderId()));
        orderViewModel.setStatusClass(HtmlClassUtils.generateOrderStatusClass(order.getStatus()));
        return orderViewModel;
    }
    @Override
    public OrderViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Order order = session.find(Order.class, entityId);

        OrderViewModel orderViewModel = getOrderViewModel(order, session);
        session.close();

        return orderViewModel;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveAll(OrderGetPagingRequest request) {
        ArrayList<OrderViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Order", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Order> orders = q.list();

        for(Order order:orders){
            OrderViewModel v = getOrderViewModel(order, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveDeliveredOrder(OrderGetPagingRequest request) {
        ArrayList<OrderViewModel> all = retrieveAll(request);

        all.removeIf(x -> x.getStatus() != ORDER_STATUS.DELIVERED);

        return all;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveNewOrder(OrderGetPagingRequest request) {
        ArrayList<OrderViewModel> all = retrieveAll(request);

        all.removeIf(x -> x.getStatus() != ORDER_STATUS.PENDING);

        return all;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveOrderByUserId(int userId) {
        Session session = HibernateUtils.getSession();
        Query q1 = session.createQuery("from Order where user.userId=:s1");
        q1.setParameter("s1",userId);
        ArrayList<OrderViewModel> list = new ArrayList<>();
        List<Order> orders = q1.list();

        for(Order order:orders){
            OrderViewModel v = getOrderViewModel(order, session);
            list.add(v);
        }
        return list;
    }

    @Override
    public BigDecimal getRevenue() {
        ArrayList<OrderViewModel> orders = OrderService.getInstance().retrieveAllOrder(new OrderGetPagingRequest());

        BigDecimal totalRevenue = BigDecimal.valueOf(0);
        for(OrderViewModel o: orders){
            totalRevenue = totalRevenue.add(o.getTotalPrice());
        }

        return totalRevenue;
    }

    @Override
    public long getTotalOrder() {
        return HibernateUtils.count("Order", "");
    }

    @Override
    public OrderOverviewViewModel getOrderOverviewStatistics() {
        ArrayList<OrderViewModel> orders = retrieveAll(new OrderGetPagingRequest());
        OrderOverviewViewModel res = new OrderOverviewViewModel();
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("select count(*) from Order where status=:s1");

        q.setParameter("s1", ORDER_STATUS.PENDING);
        res.setTotalPending((long)q.getSingleResult());

        q.setParameter("s1", ORDER_STATUS.READY_TO_SHIP);
        res.setTotalReady((long)q.getSingleResult());

        q.setParameter("s1", ORDER_STATUS.ON_THE_WAY);
        res.setTotalDelivering((long)q.getSingleResult());

        q.setParameter("s1", ORDER_STATUS.DELIVERED);
        res.setTotalCompleted((long)q.getSingleResult());

        q.setParameter("s1", ORDER_STATUS.CANCEL);
        res.setTotalCanceled((long)q.getSingleResult());

        q.setParameter("s1", ORDER_STATUS.RETURN);
        res.setTotalReturned((long)q.getSingleResult());

        return res;

    }

    @Override
    public ArrayList<OrderViewModel> getTopOrderSoon(int top) {
        OrderGetPagingRequest req = new OrderGetPagingRequest();
        req.setPageSize(top);
        req.setSortBy("dateCreated");
        req.setTypeSort("DESC");

        return retrieveAll(req);
    }

    @Override
    public boolean createOrder(HttpServletRequest request, OrderCreateRequest orderReq, int userId) {

        ArrayList<CartItemViewModel> cartItems = CartRepository.getInstance().retrieveCartByUserId(userId);
        if(cartItems.size() == 0)
            return false;
        int orderId = OrderRepository.getInstance().insert(orderReq);
        if(orderId < 1)
            return false;
        for(CartItemViewModel c: cartItems){
            if(c.getQuantity() == 0)
                continue;
            OrderItemCreateRequest createOrderItemReq = new OrderItemCreateRequest();
            createOrderItemReq.setOrderId(orderId);
            createOrderItemReq.setQuantity(c.getQuantity());
            createOrderItemReq.setUnitPrice(c.getUnitPrice());
            createOrderItemReq.setProductId(c.getProductId());
            int orderItemId = insertOrderItem(createOrderItemReq);
            if(orderItemId == -1) {
                return false;
            }
            // Số lượng product trong kho không đủ
            else if(orderItemId == 0){
                // update lại số lượng trong giỏ hàng = với số lượng trong kho nếu vượt quá
                for(CartItemViewModel ci: cartItems) {
                    int currQuantity = ProductRepository.getInstance().getQuantity(ci.getProductId());
                    if (currQuantity == 0) {
                        CartRepository.getInstance().delete(ci.getCartItemId());
                    }
                    else if(currQuantity < ci.getQuantity()) {
                        CartItemUpdateRequest r = new CartItemUpdateRequest();
                        r.setCartItemId(ci.getCartItemId());
                        r.setQuantity(currQuantity);
                        r.setStatus(1);
                        CartRepository.getInstance().update(r);
                    }
                }
                return false;
            }
        }
        boolean success = CartService.getInstance().deleteCartByUserId(userId);
        if(!success){
            clearOrder(orderId);
            return false;
        }
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        user.setTotalCartItem(user.getTotalCartItem() - cartItems.size());
        session.setAttribute("user", user);
        if(orderReq.getDiscountId() != 0)
            DiscountRepository.getInstance().updateQuantity(orderReq.getDiscountId());
        MailJetService.getInstance().sendMail(user.getFirstName() + " " + user.getLastName(), user.getEmail(),
                "<h2>Chào " + user.getFirstName() + " " + user.getLastName() + " </h2>, <h3>FurSshop cảm ơn vì đã tin tưởng mua sản phẩm, đơn hàng sẽ nhanh chóng đến tay của bạn.<br />Bạn có thể xem chi tiết đơn hàng trong mục Đơn hàng của tôi. </h3><h4>Xin chân thành cảm ơn bạn !!! Rất vui được phục vụ.</h4>",
                "Đơn xác nhận đặt hàng");
        return true;
    }

    @Override
    public boolean clearOrder(int orderId) {
        Session s = HibernateUtils.getSession();
        Query q = s.createQuery("select orderItemId from OrderItem where order.orderId=:s1");
        q.setParameter("s1",orderId);
        List<Integer> oIds = q.list();
        s.close();
        for(int id:oIds) {
            boolean res = deleteOrderItem(id);
            if(!res)
                return false;
        }
        return delete(orderId);
    }
    private OrderItemViewModel getOrderItemViewModel(OrderItem orderItem){
        OrderItemViewModel orderItemViewModel = new OrderItemViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveProductById(orderItem.getProduct().getProductId());

        orderItemViewModel.setProductId(orderItem.getProduct().getProductId());
        orderItemViewModel.setOrderId(orderItem.getOrder().getOrderId());
        orderItemViewModel.setProductImage(product.getImage());
        orderItemViewModel.setProductName(product.getName());
        orderItemViewModel.setOrderItemId(orderItem.getOrderItemId());
        orderItemViewModel.setUnitPrice(orderItem.getUnitPrice());
        orderItemViewModel.setQuantity(orderItem.getQuantity());
        orderItemViewModel.setTotalPrice(orderItem.getTotalPrice());

        return orderItemViewModel;
    }
    @Override
    public ArrayList<OrderItemViewModel> getItemByOrderId(int orderId) {
        Session session = HibernateUtils.getSession();
        ArrayList<OrderItemViewModel> orders = new ArrayList<>();
        Query q = session.createQuery("from OrderItem where order.orderId=:s1");
        q.setParameter("s1", orderId);

        List<OrderItem> l = q.list();
        l.forEach(oi -> {
            orders.add(getOrderItemViewModel(oi));
        });
        session.close();
        return orders;
    }

    @Override
    public int insertOrderItem(OrderItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        OrderItem orderItem = new OrderItem();
        Product product = session.find(Product.class, request.getProductId());
        Order order = session.find(Order.class, request.getOrderId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setUnitPrice(request.getUnitPrice());
        orderItem.setTotalPrice(request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        int orderItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(orderItem);
            orderItemId = orderItem.getOrderItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        if(orderItemId != -1) {
            boolean res = ProductService.getInstance().updateQuantity(orderItem.getProduct().getProductId(), orderItem.getQuantity());
            if (!res) {
                OrderService.getInstance().clearOrder(orderItem.getOrder().getOrderId());
                return 0;
            }
        }
        return orderItemId;
    }

    @Override
    public boolean deleteOrderItem(Integer entityId) {
        Session session = HibernateUtils.getSession();
        OrderItem orderItem = session.find(OrderItem.class, entityId);
        session.close();
        return HibernateUtils.remove(orderItem);
    }
}
