package com.arafa.mohamed.eshop.model;

public class ImagesData {
    private String imgURl,idImage,categoryName;

    public ImagesData(){

    }

    public ImagesData(String imgURL, String idImage){
        this.imgURl=imgURL;
        this.idImage=idImage;

    }

    public ImagesData(String imgURL,String categoryName, String idImage){
        this.imgURl=imgURL;
        this.idImage=idImage;
        this.categoryName=categoryName;

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
}
