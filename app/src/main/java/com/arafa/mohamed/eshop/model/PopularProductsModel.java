package com.arafa.mohamed.eshop.model;

import java.io.Serializable;

public class PopularProductsModel implements Serializable {

    private String description,nameProduct,imgUrl,price,rating,idImage, category;

    public PopularProductsModel() {

    }

    public PopularProductsModel(String description, String nameProduct, String imgUrl, String price, String rating, String idImage, String category) {
        this.description = description;
        this.nameProduct = nameProduct;
        this.imgUrl = imgUrl;
        this.price = price;
        this.rating = rating;
        this.idImage=idImage;
        this.category=category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
