package controllers.admin.discount;

import models.services.discount.DiscountService;
import utils.ServletUtils;
import models.view_models.discounts.DiscountGetPagingRequest;
import models.view_models.discounts.DiscountViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetDiscounts", value = "/admin/discounts")
public class GetDiscounts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiscountGetPagingRequest req = new DiscountGetPagingRequest();
        ArrayList<DiscountViewModel> discounts = DiscountService.getInstance().retrieveAllDiscount(req);

        request.setAttribute("discounts",discounts);
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        ServletUtils.forward(request,response,"/views/admin/discount/discount.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
