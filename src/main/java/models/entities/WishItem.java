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
    private int wishId;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private LocalDateTime dateAdded;

    public int getWishItemId() {
        return wishItemId;
    }

    public void setWishItemId(int wishItemId) {
        this.wishItemId = wishItemId;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
