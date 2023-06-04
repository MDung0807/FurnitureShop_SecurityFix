package controllers.client.review;

import models.services.review.ReviewService;
import models.view_models.review_items.ReviewItemUpdateRequest;
import models.view_models.review_items.ReviewItemViewModel;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "EditReview", value = "/my-account/order/review/edit")
public class EditReview extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        int reviewItemId = StringUtils.toInt(request.getParameter("reviewItemId"));
        ReviewItemViewModel productReview = ReviewService.getInstance().retrieveReviewItemById(reviewItemId);

        request.setAttribute("productReview",productReview);

        ServletUtils.forward(request, response, "/my-account/order/reviews?productId=" + productReview.getProductId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        request.setCharacterEncoding("UTF-8");
        int productId = StringUtils.toInt(request.getParameter("productId"));
        int reviewItemId = StringUtils.toInt(request.getParameter("reviewItemId"));

        int userId = SessionUtils.getUserIdLogin(request);
        ReviewItemUpdateRequest updateReq = new ReviewItemUpdateRequest();
        ArrayList<ReviewItemViewModel> reviewItems = ReviewService.getInstance().retrieveReviewItemByUserId(userId);
        String error = null;
        for (ReviewItemViewModel i: reviewItems){
            if (i.getProductId() == productId && i.getReviewItemId() == reviewItemId) {
                error = "";
                break;
            }
        }

        if (Objects.equals(error, "")) {
            updateReq.setReviewItemId(reviewItemId);
            updateReq.setRating(StringUtils.toInt(request.getParameter("rating")));
            updateReq.setContent(request.getParameter("content"));

            boolean success = ReviewService.getInstance().updateReviewItem(updateReq);
        }
        else
            error = "&error=true";
        ServletUtils.redirect(response, request.getContextPath() + "/my-account/order/reviews?productId=" + -1 +error);
    }
}
