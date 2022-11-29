package controllers.admin.review;

import models.services.review.ReviewService;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeStatus", value = "/admin/review/editStatus")
public class ChangeStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewItemId = request.getParameter("reviewItemId");

        boolean isSuccess = ReviewService.getInstance().ChangeReviewItemStatus(StringUtils.toInt(reviewItemId));
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/reviews" + error);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
