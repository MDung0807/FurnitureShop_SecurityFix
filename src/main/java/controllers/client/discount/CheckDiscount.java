package controllers.client.discount;

import com.google.gson.Gson;
import models.services.discount.DiscountService;
import models.view_models.discounts.DiscountViewModel;
import org.apache.commons.text.StringEscapeUtils;
import utils.DateUtils;
import utils.constants.DISCOUNT_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CheckDiscount", value = "/discount/apply")
public class CheckDiscount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        PrintWriter out = response.getWriter();
        String discountCode = request.getParameter("discountCode");
        discountCode = StringEscapeUtils.escapeXml11(discountCode);
        DiscountViewModel discount = DiscountService.getInstance().getByDiscountCode(discountCode);
        if(discount == null)
            out.println("error");
        else if(discount.getStatus() == DISCOUNT_STATUS.SUSPENDED){
            out.println("suspended");
        }
        else if (DateUtils.stringToLocalDateTime(discount.getStartDate().replace(" ", "T")).isAfter(DateUtils.dateTimeNow()) ||
                DateUtils.stringToLocalDateTime(discount.getEndDate().replace(" ", "T")).isBefore(DateUtils.dateTimeNow())){
            out.println("expired");
        }
        else if(discount.getQuantity() == 0){
            out.println("out");
        }
        else {
            out.println(new Gson().toJson(StringEscapeUtils.escapeHtml4(discount.getDiscountCode())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
