package com.example.juzhang.bicycle.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 功能：车商品的模型
 * 作者：JuZhang
 * 时间：2017/5/13
 */

public class RentalCarMessage implements Serializable{

    private String bicycleMessageId;
    private String bicycleMessageCode;
    private String bicycleMessageStoreId;
    private String bicycleMessageStoreName;
    private String bicycleMessageName;
    private String bicycleMessageTypeId;
    private String bicycleMessageTypeName;
    private String bicycleMessageMainPictureId;
    private String bicycleMessageMainPictureSrc;
    private String bicycleMessageExtraPictureId;
    private Float bicycleMessageMarketPrice;
    private Float bicycleMessageStorePrice;
    private Integer bicycleMessageStock;
    private String bicycleMessagePromotion;
    private String bicycleMessageBrandId;
    private String bicycleMessageBrandName;
    private String bicycleMessageDiscription;
    private Integer bicycleMessageCommentNumber;
    private Boolean bicycleMessageStoreRecommend;
    private Boolean bicycleMessageStoreIsSell;
    private String bicycleMessageCreateTime;
    //热点类型:0代表 不设置，1代表 new，2代表 hot
    private Integer hotType;

    public String getBicycleMessageId() {
        return bicycleMessageId;
    }

    public void setBicycleMessageId(String bicycleMessageId) {
        this.bicycleMessageId = bicycleMessageId;
    }

    public String getBicycleMessageCode() {
        return bicycleMessageCode;
    }

    public void setBicycleMessageCode(String bicycleMessageCode) {
        this.bicycleMessageCode = bicycleMessageCode;
    }

    public String getBicycleMessageStoreId() {
        return bicycleMessageStoreId;
    }

    public void setBicycleMessageStoreId(String bicycleMessageStoreId) {
        this.bicycleMessageStoreId = bicycleMessageStoreId;
    }

    public String getBicycleMessageStoreName() {
        return bicycleMessageStoreName;
    }

    public void setBicycleMessageStoreName(String bicycleMessageStoreName) {
        this.bicycleMessageStoreName = bicycleMessageStoreName;
    }

    public String getBicycleMessageName() {
        return bicycleMessageName;
    }

    public void setBicycleMessageName(String bicycleMessageName) {
        this.bicycleMessageName = bicycleMessageName;
    }

    public String getBicycleMessageMainPictureSrc() {
        return bicycleMessageMainPictureSrc;
    }

    public void setBicycleMessageMainPictureSrc(String bicycleMessageMainPictureSrc) {
        this.bicycleMessageMainPictureSrc = bicycleMessageMainPictureSrc;
    }

    public String getBicycleMessageTypeId() {
        return bicycleMessageTypeId;
    }

    public void setBicycleMessageTypeId(String bicycleMessageTypeId) {
        this.bicycleMessageTypeId = bicycleMessageTypeId;
    }

    public String getBicycleMessageTypeName() {
        return bicycleMessageTypeName;
    }

    public void setBicycleMessageTypeName(String bicycleMessageTypeName) {
        this.bicycleMessageTypeName = bicycleMessageTypeName;
    }

    public String getBicycleMessageMainPictureId() {
        return bicycleMessageMainPictureId;
    }

    public void setBicycleMessageMainPictureId(String bicycleMessageMainPictureId) {
        this.bicycleMessageMainPictureId = bicycleMessageMainPictureId;
    }

    public String getBicycleMessageExtraPictureId() {
        return bicycleMessageExtraPictureId;
    }

    public void setBicycleMessageExtraPictureId(String bicycleMessageExtraPictureId) {
        this.bicycleMessageExtraPictureId = bicycleMessageExtraPictureId;
    }

    public Float getBicycleMessageMarketPrice() {
        return bicycleMessageMarketPrice;
    }

    public void setBicycleMessageMarketPrice(Float bicycleMessageMarketPrice) {
        this.bicycleMessageMarketPrice = bicycleMessageMarketPrice;
    }

    public Float getBicycleMessageStorePrice() {
        return bicycleMessageStorePrice;
    }

    public void setBicycleMessageStorePrice(Float bicycleMessageStorePrice) {
        this.bicycleMessageStorePrice = bicycleMessageStorePrice;
    }

    public Integer getBicycleMessageStock() {
        return bicycleMessageStock;
    }

    public void setBicycleMessageStock(Integer bicycleMessageStock) {
        this.bicycleMessageStock = bicycleMessageStock;
    }

    public String getBicycleMessagePromotion() {
        return bicycleMessagePromotion;
    }

    public void setBicycleMessagePromotion(String bicycleMessagePromotion) {
        this.bicycleMessagePromotion = bicycleMessagePromotion;
    }

    public String getBicycleMessageBrandId() {
        return bicycleMessageBrandId;
    }

    public void setBicycleMessageBrandId(String bicycleMessageBrandId) {
        this.bicycleMessageBrandId = bicycleMessageBrandId;
    }

    public String getBicycleMessageBrandName() {
        return bicycleMessageBrandName;
    }

    public void setBicycleMessageBrandName(String bicycleMessageBrandName) {
        this.bicycleMessageBrandName = bicycleMessageBrandName;
    }

    public String getBicycleMessageDiscription() {
        return bicycleMessageDiscription;
    }

    public void setBicycleMessageDiscription(String bicycleMessageDiscription) {
        this.bicycleMessageDiscription = bicycleMessageDiscription;
    }

    public Integer getBicycleMessageCommentNumber() {
        return bicycleMessageCommentNumber;
    }

    public void setBicycleMessageCommentNumber(Integer bicycleMessageCommentNumber) {
        this.bicycleMessageCommentNumber = bicycleMessageCommentNumber;
    }

    public Boolean getBicycleMessageStoreRecommend() {
        return bicycleMessageStoreRecommend;
    }

    public void setBicycleMessageStoreRecommend(Boolean bicycleMessageStoreRecommend) {
        this.bicycleMessageStoreRecommend = bicycleMessageStoreRecommend;
    }

    public Boolean getBicycleMessageStoreIsSell() {
        return bicycleMessageStoreIsSell;
    }

    public void setBicycleMessageStoreIsSell(Boolean bicycleMessageStoreIsSell) {
        this.bicycleMessageStoreIsSell = bicycleMessageStoreIsSell;
    }

    public String getBicycleMessageCreateTime() {
        return bicycleMessageCreateTime;
    }

    public void setBicycleMessageCreateTime(String bicycleMessageCreateTime) {
        this.bicycleMessageCreateTime = bicycleMessageCreateTime;
    }

    public Integer getHotType() {
        return hotType;
    }

    public void setHotType(Integer hotType) {
        this.hotType = hotType;
    }
}
