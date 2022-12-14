package controllers.client.checkout;

import com.paypal.base.rest.PayPalRESTException;
import models.repositories.cart.CartRepository;
import models.services.cart.CartService;
import models.services.order.OrderService;
import models.services.paypal.PayPalService;
import models.services.user.UserService;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.StringUtils;
import utils.constants.ORDER_PAYMENT;
import utils.constants.ORDER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "CreateOrder", value = "/order/create")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int userId = SessionUtils.getUserIdLogin(request);
        if(userId == -1)
            return;
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        String totalItemPrice = request.getParameter("totalItemPrice");
        String shipping = request.getParameter("shipping");
        String totalPrice = request.getParameter("totalPrice");

        String payment = request.getParameter("payment");
        int discountId = StringUtils.toInt(request.getParameter("discountId"));
        OrderCreateRequest createOrderReq = new OrderCreateRequest();
        createOrderReq.setAddress(address);
        createOrderReq.setDiscountId(discountId);
        createOrderReq.setEmail(email);
        createOrderReq.setName(name);
        createOrderReq.setPayment(StringUtils.toInt(payment));
        createOrderReq.setPhone(phone);
        createOrderReq.setStatus(ORDER_STATUS.PENDING);
        createOrderReq.setShipping(StringUtils.toBigDecimal(shipping));
        createOrderReq.setTotalPrice(StringUtils.toBigDecimal(totalPrice));
        createOrderReq.setTotalItemPrice(StringUtils.toBigDecimal(totalItemPrice));
        createOrderReq.setUserId(userId);
        if(createOrderReq.getPayment() == ORDER_PAYMENT.PAYPAL){
            String approvedUrl = "";
            String url = request.getRequestURL().toString();
            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
            HttpSession session = request.getSession();
            session.setAttribute("createOrderReq", createOrderReq);
            try {
                approvedUrl = PayPalService.getInstance().authorizePayment(CartService.getInstance().retrieveCartByUserId(userId), createOrderReq, baseURL);
            } catch (PayPalRESTException e) {
                ServletUtils.redirect(response, request.getContextPath() +  "/cart/items?error=true");
                return;
            }
            String u = approvedUrl;
            if(Objects.equals(approvedUrl, "")) {
                u = request.getContextPath() +  "/cart/items?error=true";
            }
            ServletUtils.redirect(response, u);
            return;
        }
        boolean res = OrderService.getInstance().createOrder(request, createOrderReq, userId);
        String status = "";
        if(!res)
            status = "?error=true";
        else{
            UserViewModel user = UserService.getInstance().retrieveUserById(userId);
            request.getSession().setAttribute("user", user);
            status = "?success=true";
        }

        ServletUtils.redirect(response, request.getContextPath() + "/cart/items" + status);
    }
}
