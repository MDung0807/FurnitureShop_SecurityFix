package models.view_models.categories;

import java.util.List;

public class CategoryViewModel {
    private int categoryId;
    private String name;
    private String description;
    private int parentCategoryId;
    private String parentCategoryName;
    private List<CategoryViewModel> subCategories;
    private String image;
    private long totalProduct;
    private long totalSell;
    private int status;
    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public List<CategoryViewModel> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryViewModel> subCategories) {
        this.subCategories = subCategories;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public long getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(long totalProduct) {
        this.totalProduct = totalProduct;
    }

    public long getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(long totalSell) {
        this.totalSell = totalSell;
    }
}
