package models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items",uniqueConstraints =
@UniqueConstraint(columnNames = {"productId","cartId"}))
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int cartId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime dateAdded;

    @Column(nullable = false)
    private int status;

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
