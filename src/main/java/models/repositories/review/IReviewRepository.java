package models.repositories.review;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.review_items.ReviewItemCreateRequest;
import models.view_models.review_items.ReviewItemGetPagingRequest;
import models.view_models.review_items.ReviewItemUpdateRequest;
import models.view_models.review_items.ReviewItemViewModel;

import java.util.ArrayList;

public interface IReviewRepository extends IModifyEntity<ReviewItemCreateRequest, ReviewItemUpdateRequest, Integer>,
        IRetrieveEntity<ReviewItemViewModel, ReviewItemGetPagingRequest, Integer> {
    boolean ChangeStatus(int reviewItemId);

    ArrayList<ReviewItemViewModel> retrieveByProductId(Integer productId);
    ArrayList<ReviewItemViewModel> retrieveByUserId(Integer userId);

    ArrayList<ReviewItemViewModel> retrieveUserReviewByProductId(Integer userId, Integer productId);
    int getReviewIdByUserId(int userId);
}
