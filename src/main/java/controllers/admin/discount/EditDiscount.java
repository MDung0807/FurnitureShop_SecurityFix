package controllers.admin.discount;

import models.services.discount.DiscountService;
import utils.DateUtils;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.discounts.DiscountUpdateRequest;
import models.view_models.discounts.DiscountViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditDiscount", value = "/admin/discount/edit")
public class EditDiscount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int discountId = StringUtils.toInt(request.getParameter("discountId"));

        DiscountViewModel discount = DiscountService.getInstance().retrieveDiscountById(discountId);

        request.setAttribute("discount", discount);

        ServletUtils.forward(request,response,"/admin/discounts");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        DiscountUpdateRequest updateReq = new DiscountUpdateRequest();

        int discountId = StringUtils.toInt(request.getParameter("discountId"));

        updateReq.setDiscountId(discountId);
        updateReq.setStartDate(DateUtils.stringToLocalDateTime(request.getParameter("startDate")));
        updateReq.setEndDate(DateUtils.stringToLocalDateTime(request.getParameter("endDate")));
        updateReq.setStatus(StringUtils.toInt(request.getParameter("status")));
        updateReq.setDiscountCode(request.getParameter("discountCode"));
        updateReq.setDiscountValue(StringUtils.toDouble(request.getParameter("discountValue")));
        updateReq.setQuantity(StringUtils.toInt(request.getParameter("quantity")));

        boolean isSuccess = DiscountService.getInstance().updateDiscount(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/discounts" + error);
    }
}
