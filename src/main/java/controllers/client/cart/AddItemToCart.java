package controllers.client.cart;

import models.services.cart.CartService;
import models.view_models.users.UserViewModel;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddItemToCart", value = "/cart/add")
public class AddItemToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        int productId = StringUtils.toInt(request.getParameter("productId"));

        int quantity = StringUtils.toInt(request.getParameter("quantity"));
        if(quantity == 0)
            quantity = 1;

        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        if(user == null)
            return;
        int userId = user.getId();

        String responseStatus = CartService.getInstance().addProductToCart(productId, quantity, userId);

        if(responseStatus.contains("success")){
            user.setTotalCartItem(user.getTotalCartItem() + 1);
            session.setAttribute("user", user);
            responseStatus = user.getTotalCartItem() + responseStatus;
        }
        out.println(responseStatus);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
