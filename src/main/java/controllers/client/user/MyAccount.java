package controllers.client.user;

import models.entities.User;
import models.services.order.OrderService;
import models.services.user.UserService;
import models.view_models.orders.OrderViewModel;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MyAccount", value = "/my-account")
public class MyAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        String info = request.getParameter("info");
        String url = "";
        if (info != null){
            request.setAttribute("user",user);
            url = "/views/client/my-account-info.jsp";
        }
        else{
            ArrayList<OrderViewModel> orders = OrderService.getInstance().retrieveOrderByUserId(user.getId());

            request.setAttribute("orders", orders);
            url = "/views/client/my-account-order.jsp";
        }
        ServletUtils.forward(request, response, url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
