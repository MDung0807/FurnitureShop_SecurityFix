package models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "review_items", uniqueConstraints =
@UniqueConstraint(columnNames = {"productId","reviewId"}))
@Entity
public class ReviewItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewItemId;

    @Column(nullable = false)
    private int reviewId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private int rating;


    public int getReviewItemId() {
        return reviewItemId;
    }

    public void setReviewItemId(int reviewItemId) {
        this.reviewItemId = reviewItemId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
