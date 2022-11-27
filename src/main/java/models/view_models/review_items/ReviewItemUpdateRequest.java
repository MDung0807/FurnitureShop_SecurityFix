package models.view_models.review_items;

public class ReviewItemUpdateRequest {
    public int reviewItemId;

    public String content;

    public int rating;

    private int status;

    public int getReviewItemId() {
        return reviewItemId;
    }

    public void setReviewItemId(int reviewItemId) {
        this.reviewItemId = reviewItemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
