package controllers.admin.brand;

import models.services.brand.BrandService;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetBrands", value = "/admin/brands")
public class GetBrands extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BrandGetPagingRequest req = new BrandGetPagingRequest();

        req.setTypeSort("brandName");
        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAllBrand(req);

        request.setAttribute("brands", brands);
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        ServletUtils.forward(request, response, "/views/admin/brand/brand.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("edit","true");
        doGet(request, response);
    }
}
