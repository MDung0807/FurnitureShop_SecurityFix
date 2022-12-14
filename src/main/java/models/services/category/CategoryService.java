package models.services.category;

import models.entities.Category;
import models.repositories.category.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import models.view_models.categories.CategoryCreateRequest;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryUpdateRequest;
import models.view_models.categories.CategoryViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryService implements ICategoryService{
    private static CategoryService instance = null;
    public static CategoryService getInstance(){
        if(instance == null)
            instance = new CategoryService();
        return instance;
    }
    @Override
    public int insertCategory(CategoryCreateRequest request) {
        return CategoryRepository.getInstance().insert(request);
    }
    @Override
    public boolean updateCategory(CategoryUpdateRequest request) {
        return CategoryRepository.getInstance().update(request);
    }
    @Override
    public boolean deleteCategory(Integer categoryId) {
        return CategoryRepository.getInstance().delete(categoryId);
    }
    @Override
    public CategoryViewModel retrieveCategoryById(Integer categoryId) {
        return CategoryRepository.getInstance().retrieveById(categoryId);
    }
    @Override
    public ArrayList<CategoryViewModel> retrieveAllCategory(CategoryGetPagingRequest request) {
        return CategoryRepository.getInstance().retrieveAll(request);
    }


    public HashMap<Integer, String> getParentCategory() {
        return CategoryRepository.getInstance().getParentCategory();
    }
}
