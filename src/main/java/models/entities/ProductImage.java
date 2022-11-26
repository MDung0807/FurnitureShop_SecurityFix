package models.entities;


import javax.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @Column(name = "productImageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productImageId;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Column(nullable = false)
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(int productImageId) {
        this.productImageId = productImageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }


}
