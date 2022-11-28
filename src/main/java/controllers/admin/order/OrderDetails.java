package controllers.admin.order;

import models.services.order.OrderService;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.orders.OrderViewModel;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OrderDetails", value = "/admin/order/detail")
public class OrderDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = StringUtils.toInt(request.getParameter("orderId"));
        OrderViewModel order = OrderService.getInstance().retrieveOrderById(orderId);

        request.setAttribute("order", order);
        ArrayList<OrderItemViewModel> orderItems = OrderService.getInstance().getItemByOrderId(orderId);
        request.setAttribute("orderItems", orderItems);

        ServletUtils.forward(request, response, "/views/admin/order/order-details.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
