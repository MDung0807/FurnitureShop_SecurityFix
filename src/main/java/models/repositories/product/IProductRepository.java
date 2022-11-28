package models.repositories.product;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IProductRepository extends IModifyEntity<ProductCreateRequest, ProductUpdateRequest, Integer>,
        IRetrieveEntity<ProductViewModel, ProductGetPagingRequest, Integer> {
    boolean updateQuantity(int productId, int quantity);
    int getQuantity(int productId);
    int insertImage(ProductImageCreateRequest request);
    boolean updateImage(ProductImageUpdateRequest request);
    boolean deleteImage(Integer entityId);
    ProductImageViewModel retrieveImageById(Integer entityId);
    ArrayList<ProductImageViewModel> retrieveAllImage(ProductImageGetPagingRequest request);
}
