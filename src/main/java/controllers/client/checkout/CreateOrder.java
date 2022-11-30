package controllers.client.checkout;

import models.services.order.OrderService;
import models.services.user.UserService;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.StringUtils;
import utils.constants.ORDER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

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

        boolean res = OrderService.getInstance().createOrder(request, createOrderReq, userId);
        String error = "";
        if(!res)
            error = "?error=true";
        else{
            UserViewModel user = UserService.getInstance().retrieveUserById(userId);
            request.getSession().setAttribute("user", user);
        }

        ServletUtils.redirect(response, request.getContextPath() + "/cart/items" + error);
    }
}
