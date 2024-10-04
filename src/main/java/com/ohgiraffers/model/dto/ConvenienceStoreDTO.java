package com.ohgiraffers.model.dto;

public class ConvenienceStoreDTO {
    private int code;
    private String name;
    private int price;
    private String category;
    private String productStatus;

    public ConvenienceStoreDTO(){}

    public ConvenienceStoreDTO(int code) {
        this.code = code;

    }

    public ConvenienceStoreDTO(String name) {
        this.name = name;
    }

    public ConvenienceStoreDTO(int code, String name, int price, String category, String productStatus) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
        this.productStatus = productStatus;
    }
    public ConvenienceStoreDTO(String name, int price, String category, String productStatus) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.productStatus = productStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "편의점 {" +
                "상품관리 코드 : " + code +
                ", 상품 이름 : '" + name + '\'' +
                ", 상품 가격 : " + price +
                ", 상품 카테고리 : '" + category + '\'' +
                ", 재고 여부 : '" + productStatus + '\'' +
                '}';
    }
}
