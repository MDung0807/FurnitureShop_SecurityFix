package controllers.client.cart;

import models.repositories.brand.BrandRepository;
import models.repositories.cart.CartRepository;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.constants.BRAND_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@WebServlet(name = "GetCart", value = "/cart/items")
public class GetCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        if(user == null)
            return;
        int userId = user.getId();
        ArrayList<CartItemViewModel> cartItems = CartRepository.getInstance().retrieveCartByUserId(userId);
        request.setAttribute("cartItems", cartItems);

        ArrayList<BrandViewModel> brands = BrandRepository.getInstance().retrieveAll(new BrandGetPagingRequest());
        brands.removeIf(x -> x.getStatus() == BRAND_STATUS.IN_ACTIVE);
        request.setAttribute("brands", brands);
        BigDecimal total = CartRepository.getInstance().getTotalCartItemPriceByUserId(userId);
        request.setAttribute("total", total);
        ServletUtils.forward(request,response,"/views/client/cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
