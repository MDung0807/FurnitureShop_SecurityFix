package models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wish_items",uniqueConstraints =
@UniqueConstraint(columnNames = {"productId","wishId"}))
public class WishItem {
    @Id
    @Column(name = "wishItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishItemId;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private LocalDateTime dateAdded;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "wishId")
    private WishList wishList;

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public int getWishItemId() {
        return wishItemId;
    }

    public void setWishItemId(int wishItemId) {
        this.wishItemId = wishItemId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }
}
