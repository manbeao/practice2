package com.ohgiraffers.model.dto;
//
public class ManagerDTO {
    private int managerId;
    private String managerName;
    private String managerPhone;
    private String productCategory;
    private int productCode;

    public ManagerDTO(){}



    public ManagerDTO(int managerId, String managerName, String managerPhone, String productCategory, int productCode) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.productCategory = productCategory;
        this.productCode = productCode;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "관리자 {" +
                "관리자 ID : " + managerId +
                ", 관리자 이름 : '" + managerName + '\'' +
                ", 관리자 전화 번호'" + managerPhone + '\'' +
                ", 상품 카테고리 : '" + productCategory + '\'' +
                ", 상품 코드" + productCode +
                '}';
    }
}
