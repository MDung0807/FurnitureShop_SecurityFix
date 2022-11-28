package models.repositories.discount;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.discounts.DiscountCreateRequest;
import models.view_models.discounts.DiscountGetPagingRequest;
import models.view_models.discounts.DiscountUpdateRequest;
import models.view_models.discounts.DiscountViewModel;

public interface IDiscountRepository extends IModifyEntity<DiscountCreateRequest, DiscountUpdateRequest, Integer>,
        IRetrieveEntity<DiscountViewModel, DiscountGetPagingRequest, Integer> {
    DiscountViewModel getByDiscountCode(String discountCode);
    boolean updateQuantity(int discountId);
}
