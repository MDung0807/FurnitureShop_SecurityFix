package models.repositories.category;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.categories.CategoryCreateRequest;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryUpdateRequest;
import models.view_models.categories.CategoryViewModel;

import java.util.HashMap;

public interface ICategoryRepository extends IModifyEntity<CategoryCreateRequest, CategoryUpdateRequest, Integer>,
        IRetrieveEntity<CategoryViewModel, CategoryGetPagingRequest, Integer> {
    HashMap<Integer, String> getParentCategory();
}
