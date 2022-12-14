package controllers.client.cart;

import models.services.cart.CartService;
import models.view_models.cart_items.CartItemUpdateRequest;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UpdateItem", value = "/cart/update")
public class UpdateItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int cartItemId = StringUtils.toInt(request.getParameter("cartItemId"));
        int quantity = StringUtils.toInt(request.getParameter("quantity"));

        CartItemUpdateRequest updateReq = new CartItemUpdateRequest();
        updateReq.setCartItemId(cartItemId);
        updateReq.setQuantity(quantity);

        boolean success = CartService.getInstance().updateCartItem(updateReq);
        if(success){
            out.println("success");
        }else{
            int q = CartService.getInstance().canUpdateQuantity(cartItemId, quantity);
            if(q != -1)
                out.println(q + "-over");
            else
                out.println("error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
