package controllers.admin.brand;

import models.services.brand.BrandService;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.brands.BrandUpdateRequest;
import models.view_models.brands.BrandViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditBrand", value = "/admin/brand/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EditBrand extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brandId = request.getParameter("brandId");

        BrandViewModel brand = BrandService.getInstance().retrieveBrandById(Integer.parseInt(brandId));
        request.setAttribute("brand", brand);
        ServletUtils.forward(request, response, "/admin/brands");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("brand-logo");
        BrandUpdateRequest brandReq = new BrandUpdateRequest();
        String brandId = request.getParameter("brandId");

        brandReq.setBrandId(StringUtils.toInt(brandId));
        brandReq.setBrandName(request.getParameter("brandName"));
        brandReq.setOrigin(request.getParameter("brandOrigin"));
        brandReq.setImage(filePart);
        brandReq.setStatus(StringUtils.toInt(request.getParameter("status")));

        boolean isSuccess = BrandService.getInstance().updateBrand(brandReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }

        ServletUtils.redirect(response, request.getContextPath() + "/admin/brands" + error);
    }
}
