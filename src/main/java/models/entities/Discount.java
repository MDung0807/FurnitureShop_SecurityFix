package models.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @Column(name = "discountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discountId;

    @Column(nullable = false, unique = true)
    private String discountCode;
    @Column(nullable = false)
    private double discountValue;
    @Column(nullable = false)
    private LocalDateTime dateStart;
    @Column(nullable = false)
    private LocalDateTime dateEnd;
    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int quantity;

    @OneToMany
    @JoinColumn(name = "discountId")
    private List<Order> orders;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
