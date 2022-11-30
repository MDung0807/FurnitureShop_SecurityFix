package controllers.client.review;

import models.services.product.ProductService;
import models.services.review.ReviewService;
import models.view_models.review_items.ReviewItemViewModel;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetReviewsByProduct", value = "/my-account/order/reviews")
public class GetReviewsByProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = StringUtils.toInt(request.getParameter("productId"));
        int userId = SessionUtils.getUserIdLogin(request);
        ArrayList<ReviewItemViewModel> reviewItems = ReviewService.getInstance().retrieveUserReviewByProductId(userId, productId);
        request.setAttribute("reviewItems",reviewItems);
        request.setAttribute("product", ProductService.getInstance().retrieveProductById(productId));
        ServletUtils.forward(request,response,"/views/client/review.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
