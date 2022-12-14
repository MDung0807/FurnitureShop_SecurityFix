package controllers.admin.category;

import models.services.category.CategoryService;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveCategory", value = "/admin/category/delete")
public class RemoveCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryId = request.getParameter("categoryId");
        String sub = request.getParameter("sub-categories");

        boolean isSuccess = CategoryService.getInstance().deleteCategory(StringUtils.toInt(categoryId));
        String error = "";
        if(!isSuccess){
            error = "error=true";
        }
        if(sub == null || sub.equals("")) {
            error = "?" +error;
            ServletUtils.redirect(response, request.getContextPath() + "/admin/categories" + error);
        }
        else {
            error = "&" +error;
            ServletUtils.redirect(response, request.getContextPath() + "/admin/categories?sub-categories=true" + error);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
