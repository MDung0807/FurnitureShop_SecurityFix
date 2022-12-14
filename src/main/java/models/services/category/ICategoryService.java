package models.services.category;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.categories.CategoryCreateRequest;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryUpdateRequest;
import models.view_models.categories.CategoryViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICategoryService{
    int insertCategory(CategoryCreateRequest request);
    boolean updateCategory(CategoryUpdateRequest request);
    boolean deleteCategory(Integer categoryId);
    CategoryViewModel retrieveCategoryById(Integer categoryId);
    ArrayList<CategoryViewModel> retrieveAllCategory(CategoryGetPagingRequest request);
    HashMap<Integer, String> getParentCategory();
}
