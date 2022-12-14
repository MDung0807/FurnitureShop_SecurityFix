package controllers.client.user;

import models.services.order.OrderService;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.orders.OrderViewModel;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "GetOrderDetails", value = "/my-account/order/detail")
public class GetOrderDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = StringUtils.toInt(request.getParameter("orderId"));
        OrderViewModel order = OrderService.getInstance().retrieveOrderById(orderId);

        request.setAttribute("order", order);
        ArrayList<OrderItemViewModel> orderItems = OrderService.getInstance().getItemByOrderId(orderId);
        request.setAttribute("orderItems", orderItems);

        ServletUtils.forward(request, response, "/views/client/my-account-order-details.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
