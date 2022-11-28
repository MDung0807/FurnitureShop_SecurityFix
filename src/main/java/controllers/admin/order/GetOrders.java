package controllers.admin.order;

import models.services.order.OrderService;
import models.view_models.orders.OrderGetPagingRequest;
import models.view_models.orders.OrderViewModel;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetOrders", value = "/admin/orders")
public class GetOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderGetPagingRequest req = new OrderGetPagingRequest();

        String newOrder = request.getParameter("new");
        String delivered = request.getParameter("delivered");
        ArrayList<OrderViewModel> orders;
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        if(newOrder != null){
            orders = OrderService.getInstance().retrieveNewOrder(req);
        }else if(delivered != null){
            orders = OrderService.getInstance().retrieveDeliveredOrder(req);
        }else{
            orders = OrderService.getInstance().retrieveAllOrder(req);
        }

        request.setAttribute("orders", orders);

        ServletUtils.forward(request, response, "/views/admin/order/order.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
