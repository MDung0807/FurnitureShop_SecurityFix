package models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @Column(name = "brandId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;

    @Column(nullable = false)
    private String brandName;
    @Column(nullable = false)
    private String origin;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Column(nullable = false)
    private int status;

    @OneToMany
    @JoinColumn(name = "brandId")
    private List<Product> products;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
