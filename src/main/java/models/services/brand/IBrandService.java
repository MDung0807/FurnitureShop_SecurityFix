package models.services.brand;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.brands.BrandCreateRequest;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandUpdateRequest;
import models.view_models.brands.BrandViewModel;

import java.util.ArrayList;

public interface IBrandService  {
    int insertBrand(BrandCreateRequest request);
    boolean updateBrand(BrandUpdateRequest request);
    boolean deleteBrand(Integer brandId);
    BrandViewModel retrieveBrandById(Integer brandId);
    ArrayList<BrandViewModel> retrieveAllBrand(BrandGetPagingRequest request);
}
