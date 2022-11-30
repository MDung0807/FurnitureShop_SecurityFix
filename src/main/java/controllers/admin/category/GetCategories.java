package controllers.admin.category;

import models.services.category.CategoryService;
import utils.ServletUtils;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "GetCategories", value = "/admin/categories")
public class GetCategories extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryGetPagingRequest req = new CategoryGetPagingRequest();
        String sub = request.getParameter("sub-categories");

        req.setTypeSort("brandName");
        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAllCategory(req);
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        if(sub == null || sub.equals("")) {
            categories.removeIf(s -> s.getParentCategoryId() > 0);
            request.setAttribute("categories", categories);
            ServletUtils.forward(request, response, "/views/admin/category/main-category.jsp");
        }
        else {
            categories.removeIf(s -> s.getParentCategoryId() == 0);
            request.setAttribute("categories",categories);
            HashMap<Integer, String> parentCategories = CategoryService.getInstance().getParentCategory();
            request.setAttribute("parentCategories",parentCategories);
            ServletUtils.forward(request, response, "/views/admin/category/sub-category.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
