package controllers.admin;

import models.repositories.order.OrderRepository;
import models.repositories.user.UserRepository;
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

        ArrayList<UserViewModel> customers = UserRepository.getInstance().getTopUserByTotalOrder(10);
        long totalUsers =UserRepository.getInstance().getTotalUser();

        ArrayList<OrderViewModel> orders = OrderRepository.getInstance().getTopOrderSoon(10);
        long totalOrders = OrderRepository.getInstance().getTotalOrder();

        BigDecimal totalRevenue = OrderRepository.getInstance().getRevenue();

        OrderOverviewViewModel statistics = OrderRepository.getInstance().getOrderOverviewStatistics();

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
