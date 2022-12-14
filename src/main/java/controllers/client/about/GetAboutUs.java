package controllers.client.about;

import models.services.brand.BrandService;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;
import utils.ServletUtils;
import utils.constants.BRAND_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetAboutUs", value = "/about")
public class GetAboutUs extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BrandGetPagingRequest req = new BrandGetPagingRequest();
        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAllBrand(req);
        brands.removeIf(x -> x.getStatus() == BRAND_STATUS.IN_ACTIVE);
        request.setAttribute("brands", brands);
        ServletUtils.forward(request,response,"/views/client/about.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
