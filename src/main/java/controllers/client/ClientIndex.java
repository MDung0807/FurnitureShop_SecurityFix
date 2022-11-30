package controllers.client;

import models.services.category.CategoryService;
import models.services.product.ProductService;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryViewModel;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductViewModel;
import utils.ServletUtils;
import utils.constants.CATEGORY_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet(name = "ClientIndex", value = "/home")
public class ClientIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProductGetPagingRequest req1 = new ProductGetPagingRequest();
        ArrayList<ProductViewModel> products = ProductService.getInstance().retrieveAllProduct(req1);
        products.sort((o1, o2) -> (int) (o2.getAvgRating() - o1.getAvgRating()));
        ArrayList<ProductViewModel> popularProducts = new ArrayList<>();
        for(int i=0;i<10;i++){
            try {
                popularProducts.add(products.get(i));
            }
            catch(Exception e){
                break;
            }
        }
        CategoryGetPagingRequest req2 = new CategoryGetPagingRequest();
        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAllCategory(req2);
        categories.removeIf(x -> x.getParentCategoryId() != 0 || x.getStatus() == CATEGORY_STATUS.IN_ACTIVE);
        request.setAttribute("products", popularProducts);
        request.setAttribute("categories", categories);
        ServletUtils.forward(request,response,"/views/client/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
