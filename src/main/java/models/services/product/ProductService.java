package models.services.product;

import models.repositories.product.ProductRepository;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;

import java.util.*;

public class ProductService implements IProductService {
    private static ProductService instance = null;
    public static ProductService getInstance() {
        if(instance == null)
            instance = new ProductService();
        return instance;
    }
    @Override
    public int insertProduct(ProductCreateRequest request) {
        return ProductRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateProduct(ProductUpdateRequest request) {
        return ProductRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        return ProductRepository.getInstance().delete(productId);
    }

    @Override
    public ProductViewModel retrieveProductById(Integer productId) {
        return ProductRepository.getInstance().retrieveById(productId);
    }

    @Override
    public ArrayList<ProductViewModel> retrieveAllProduct(ProductGetPagingRequest request) {
        return ProductRepository.getInstance().retrieveAll(request);
    }

    @Override
    public boolean updateQuantity(int productId, int quantity) {
        return ProductRepository.getInstance().updateQuantity(productId, quantity);
    }

    @Override
    public int getQuantity(int productId) {
        return ProductRepository.getInstance().getQuantity(productId);
    }

    @Override
    public int insertImage(ProductImageCreateRequest request) {
        return ProductRepository.getInstance().insertImage(request);
    }

    @Override
    public boolean updateImage(ProductImageUpdateRequest request) {
        return ProductRepository.getInstance().updateImage(request);
    }

    @Override
    public boolean deleteImage(Integer entityId) {
        return ProductRepository.getInstance().deleteImage(entityId);
    }

    @Override
    public ProductImageViewModel retrieveImageById(Integer entityId) {
        return ProductRepository.getInstance().retrieveImageById(entityId);
    }

    @Override
    public ArrayList<ProductImageViewModel> retrieveAllImage(ProductImageGetPagingRequest request) {
        return ProductRepository.getInstance().retrieveAllImage(request);
    }
}
