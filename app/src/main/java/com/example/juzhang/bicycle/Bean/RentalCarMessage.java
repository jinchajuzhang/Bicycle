package com.example.juzhang.bicycle.Bean;

import java.io.Serializable;

/**
 * 功能：车商品的模型
 * 作者：JuZhang
 * 时间：2017/5/13
 */

public class RentalCarMessage implements Serializable{

    private String imgUrl;
    private String carName;
    private String carType;
    private String carDescription;
    private String carPurpose;
    private float rentalPrice;

    //热点类型:0代表 不设置，1代表 new，2代表 hot
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCarName() {
        return carName;
    }
    public String getCarPurpose() {
        return carPurpose;
    }

    public void setCarPurpose(String carPurpose) {
        this.carPurpose = carPurpose;
    }
    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }
}
