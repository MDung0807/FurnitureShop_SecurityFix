package controllers.admin;

import models.services.order.OrderService;
import models.services.user.UserService;
import models.view_models.orders.OrderGetPagingRequest;
import models.view_models.orders.OrderOverviewViewModel;
import models.view_models.orders.OrderViewModel;
import models.view_models.users.UserGetPagingRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet(name = "AdminIndex", value = "/admin/home")
public class AdminIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<UserViewModel> customers = UserService.getInstance().getTopUserByTotalOrder(10);
        long totalUsers = UserService.getInstance().getTotalUser();

        ArrayList<OrderViewModel> orders = OrderService.getInstance().getTopOrderSoon(10);
        long totalOrders = OrderService.getInstance().getTotalOrder();

        BigDecimal totalRevenue = OrderService.getInstance().getRevenue();

        OrderOverviewViewModel statistics = OrderService.getInstance().getOrderOverviewStatistics();

        request.setAttribute("totalUsers",totalUsers);
        request.setAttribute("totalOrders",totalOrders);
        request.setAttribute("totalRevenue",totalRevenue);
        request.setAttribute("customers", customers);
        request.setAttribute("orders", orders);
        request.setAttribute("statistics",statistics);

        ServletUtils.forward(request,response,"/views/admin/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
