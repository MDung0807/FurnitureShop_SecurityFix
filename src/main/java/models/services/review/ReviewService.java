package models.services.review;

import models.repositories.review.ReviewRepository;
import models.view_models.review_items.ReviewItemCreateRequest;
import models.view_models.review_items.ReviewItemGetPagingRequest;
import models.view_models.review_items.ReviewItemUpdateRequest;
import models.view_models.review_items.ReviewItemViewModel;

import java.util.ArrayList;

public class ReviewService implements IReviewService {
    private static ReviewService instance = null;
    public static ReviewService getInstance(){
        if(instance == null)
            instance = new ReviewService();
        return instance;
    }
    @Override
    public int insertReviewItem(ReviewItemCreateRequest request) {
        return ReviewRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateReviewItem(ReviewItemUpdateRequest request) {
        return ReviewRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteReviewItem(Integer reviewItemId) {
        return ReviewRepository.getInstance().delete(reviewItemId);
    }
    @Override
    public ReviewItemViewModel retrieveReviewItemById(Integer reviewItemId) {
        return ReviewRepository.getInstance().retrieveById(reviewItemId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveAllReviewItem(ReviewItemGetPagingRequest request) {
        return ReviewRepository.getInstance().retrieveAll(request);
    }

    @Override
    public boolean ChangeReviewItemStatus(int reviewItemId) {
        return ReviewRepository.getInstance().ChangeStatus(reviewItemId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveReviewItemByProductId(Integer productId) {
        return ReviewRepository.getInstance().retrieveByProductId(productId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveReviewItemByUserId(Integer userId) {
        return ReviewRepository.getInstance().retrieveByUserId(userId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveUserReviewByProductId(Integer userId, Integer productId) {
        return ReviewRepository.getInstance().retrieveUserReviewByProductId(userId,productId);
    }

    @Override
    public int getReviewIdByUserId(int userId) {
        return ReviewRepository.getInstance().getReviewIdByUserId(userId);
    }


}
