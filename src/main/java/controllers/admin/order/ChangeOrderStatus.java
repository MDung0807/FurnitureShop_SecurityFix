package controllers.admin.order;

import models.services.order.OrderService;
import models.view_models.orders.OrderUpdateRequest;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeOrderStatus", value = "/admin/order/editStatus")
public class ChangeOrderStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String orderId = request.getParameter("orderId");
//
//        OrderViewModel order = OrderService.getInstance().retrieveOrderById(Integer.parseInt(orderId));
//        request.setAttribute("currOrder", order);
//        ServletUtils.forward(request, response, "/admin/orders");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = StringUtils.toInt(request.getParameter("editOrderId"));
        int status = StringUtils.toInt(request.getParameter("orderStatus"));
        OrderUpdateRequest req = new OrderUpdateRequest();
        req.setOrderId(orderId);
        req.setStatus(status);

        boolean isSuccess = OrderService.getInstance().updateOrder(req);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/orders" + error);
    }
}
