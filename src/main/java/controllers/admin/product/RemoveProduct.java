package controllers.admin.product;

import models.services.cart.CartService;
import models.services.product.ProductService;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveProduct", value = "/admin/product/delete")
public class RemoveProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = StringUtils.toInt(request.getParameter("productId"));
        boolean isSuccess = ProductService.getInstance().deleteProduct(productId);
        String error = "";
        if(!isSuccess){
            error = "?error = true";
        }else{
            CartService.getInstance().updateQuantityByProductId(productId, 0);
        }
        ServletUtils.redirect(response,request.getContextPath() + "/admin/products" + error);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
