package controllers.admin.review;

import models.services.review.ReviewService;
import utils.ServletUtils;
import models.view_models.review_items.ReviewItemGetPagingRequest;
import models.view_models.review_items.ReviewItemViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetReviews", value = "/admin/reviews")
public class GetReviews extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewItemGetPagingRequest req = new ReviewItemGetPagingRequest();

        ArrayList<ReviewItemViewModel> reviews = ReviewService.getInstance().retrieveAllReviewItem(req);

        request.setAttribute("reviews",reviews);
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        ServletUtils.forward(request, response, "/views/admin/review/review.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
