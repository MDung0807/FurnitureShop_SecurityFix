package models.view_models.product_images;

import javax.servlet.http.Part;
import java.util.List;

public class ProductImageCreateRequest {
    private int productId;

    private List<Part> images;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<Part> getImages() {
        return images;
    }

    public void setImages(List<Part> images) {
        this.images = images;
    }
}
