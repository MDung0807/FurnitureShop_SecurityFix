package models.repositories.brand;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.brands.BrandCreateRequest;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandUpdateRequest;
import models.view_models.brands.BrandViewModel;

public interface IBrandRepository extends IModifyEntity<BrandCreateRequest, BrandUpdateRequest, Integer>,
        IRetrieveEntity<BrandViewModel, BrandGetPagingRequest, Integer> {
}
