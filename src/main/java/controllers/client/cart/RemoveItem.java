package controllers.client.cart;

import models.services.cart.CartService;
import models.view_models.users.UserViewModel;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RemoveItem", value = "/cart/remove")
public class RemoveItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int cartItemId = StringUtils.toInt(request.getParameter("cartItemId"));
        boolean success = CartService.getInstance().deleteCartItem(cartItemId);
        if(!success){
            out.println("error");
        }else{
            HttpSession session = request.getSession();
            UserViewModel user = (UserViewModel) session.getAttribute("user");
            if(user == null) {
                out.println("error");
                return;
            }
            user.setTotalCartItem(user.getTotalCartItem() - 1);
            session.setAttribute("user", user);
            //ServletUtils.redirect(response, request.getContextPath() + "/wish-list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
