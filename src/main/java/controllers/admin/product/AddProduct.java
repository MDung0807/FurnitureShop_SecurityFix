package controllers.admin.product;

import models.services.brand.BrandService;
import models.services.category.CategoryService;
import models.services.product.ProductService;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryViewModel;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.products.ProductCreateRequest;
import utils.constants.BRAND_STATUS;
import utils.constants.CATEGORY_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "AddProduct", value = "/admin/product/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryGetPagingRequest req1 = new CategoryGetPagingRequest();

        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAllCategory(req1);
        categories.removeIf(x -> x.getStatus() == CATEGORY_STATUS.IN_ACTIVE);
        BrandGetPagingRequest req2 = new BrandGetPagingRequest();

        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAllBrand(req2);
        brands.removeIf(x -> x.getStatus() == BRAND_STATUS.IN_ACTIVE);
        request.setAttribute("categories",categories);
        request.setAttribute("brands",brands);
        ServletUtils.forward(request, response, "/views/admin/product/add-product.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part file = request.getPart("main-image");

        List<Part> subImages = new ArrayList<Part>();
        int numberSubImage = StringUtils.toInt(request.getParameter("number-sub-image"));
        for(int i=1; i<= numberSubImage; i++){
            Part f = request.getPart("sub-image-" + i);
            if(f != null && !Objects.equals(f.getSubmittedFileName(), ""))
                subImages.add(f);
        }

        ProductCreateRequest req = new ProductCreateRequest();
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        int quantity = StringUtils.toInt(request.getParameter("quantity"));

        int categoryId = StringUtils.toInt(request.getParameter("categories"));
        String origin = request.getParameter("origin");
        int brandId = StringUtils.toInt(request.getParameter("brands"));
        int status = StringUtils.toInt(request.getParameter("status"));

        req.setDescription(description);
        req.setOrigin(origin);
        req.setPrice(StringUtils.toBigDecimal(price));
        req.setProductName(productName);

        req.setQuantity(quantity);
        req.setImage(file);
        req.setCategoryId(categoryId);
        req.setBrandId(brandId);
        req.setStatus(status);
        int productId = ProductService.getInstance().insertProduct(req);
        if(productId < 1){
            request.setAttribute("error", "true");
            doGet(request, response);
            return;
        }
        ProductImageCreateRequest productImageCreateRequest = new ProductImageCreateRequest();
        productImageCreateRequest.setProductId(productId);

        productImageCreateRequest.setImages(subImages);

        int id = ProductService.getInstance().insertImage(productImageCreateRequest);
        if(id < 1){
            request.setAttribute("error", "true");
            doGet(request, response);
            return;
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/products");

    }
}
