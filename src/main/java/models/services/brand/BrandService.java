package models.services.brand;

import models.repositories.brand.BrandRepository;
import models.view_models.brands.BrandCreateRequest;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandUpdateRequest;
import models.view_models.brands.BrandViewModel;

import java.util.ArrayList;

public class BrandService implements IBrandService{
    private static BrandService instance = null;

    public static BrandService getInstance(){
        if (instance == null)
            instance = new BrandService();
        return instance;
    }
    @Override
    public int insertBrand(BrandCreateRequest request) {
        return BrandRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateBrand(BrandUpdateRequest request) {
        return BrandRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteBrand(Integer brandId) {
        return BrandRepository.getInstance().delete(brandId);
    }

    @Override
    public BrandViewModel retrieveBrandById(Integer brandId) {
        return BrandRepository.getInstance().retrieveById(brandId);
    }

    @Override
    public ArrayList<BrandViewModel> retrieveAllBrand(BrandGetPagingRequest request) {
        return BrandRepository.getInstance().retrieveAll(request);
    }
}
