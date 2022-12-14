package controllers.client.checkout;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import models.services.order.OrderService;
import models.services.paypal.PayPalService;
import models.view_models.orders.OrderCreateRequest;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HandleThirdPartyPayment", value = "/checkout/third-party-payment")
public class HandleThirdPartyPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");
        int userId = SessionUtils.getUserIdLogin(request);
        if(userId == 0) {
            ServletUtils.redirect(response, request.getContextPath() + "/cart/items?error=true");
            return;
        }

        Payment payment = null;
        try {
            payment = PayPalService.getInstance().handlePayment(paymentId, payerId);
        } catch (PayPalRESTException e) {
            ServletUtils.redirect(response, request.getContextPath() + "/cart/items?error=true");
            return;
        }
        HttpSession session = request.getSession();
        OrderCreateRequest orderCreateRequest = (OrderCreateRequest) session.getAttribute("createOrderReq");
        boolean res = OrderService.getInstance().createOrder(request, orderCreateRequest, userId);
        if(!res){
            ServletUtils.redirect(response, request.getContextPath() + "/cart/items?error=true");
            return;
        }
        ServletUtils.redirect(response, request.getContextPath() + "/cart/items?success=true");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
