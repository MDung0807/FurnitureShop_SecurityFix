package controllers.client.checkout;

import models.services.cart.CartService;
import models.view_models.cart_items.CartItemViewModel;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@WebServlet(name = "GetCheckout", value = "/checkout")
public class GetCheckout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = SessionUtils.getUserIdLogin(request);
        if(userId == -1)
            return;
        ArrayList<CartItemViewModel> cartItems = CartService.getInstance().retrieveCartByUserId(userId);
        cartItems.removeIf(x -> x.getQuantity() == 0);
        request.setAttribute("cartItems",cartItems);
        BigDecimal totalItemPrice = CartService.getInstance().getTotalCartItemPriceByUserId(userId);
        BigDecimal shipping = BigDecimal.valueOf(0);
        BigDecimal discount = BigDecimal.valueOf(0);
        BigDecimal totalPrice;
        totalPrice = totalItemPrice.add(shipping);
        totalPrice = totalPrice.subtract(totalPrice.multiply(discount));

        request.setAttribute("totalItemPrice",totalItemPrice);
        request.setAttribute("shipping",shipping);
        request.setAttribute("discount",discount);
        request.setAttribute("totalPrice",totalPrice);
        ServletUtils.forward(request,response, "/views/client/checkout.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
