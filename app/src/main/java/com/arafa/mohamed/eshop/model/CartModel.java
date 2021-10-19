package com.arafa.mohamed.eshop.model;

public class CartModel {
    private String productName,imgUrl,productPrice,productId,totalPrice,totalQuantity;

    public CartModel() {
    }

    public CartModel(String productName, String imgUrl, String productPrice, String productId, String totalPrice, String totalQuantity) {
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.productPrice = productPrice;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
