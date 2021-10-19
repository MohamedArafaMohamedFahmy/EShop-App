package com.arafa.mohamed.eshop.model;

public class CategoryModel {
    private String imgURl,idImage,categoryName,category;

    public CategoryModel() {
    }

    public CategoryModel(String imgURl, String idImage, String categoryName) {
        this.imgURl = imgURl;
        this.idImage = idImage;
        this.categoryName = categoryName;

    }

    public String getImgURl() {
        return imgURl;
    }

    public void setImgURl(String imgURl) {
        this.imgURl = imgURl;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
