package models.view_models.categories;

import javax.servlet.http.Part;

public class CategoryUpdateRequest {
    private int categoryId;
    private String name;
    private int parentCategoryId;
    private String description;
    private Part image;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
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

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String content) {
        this.description = content;
    }
}
