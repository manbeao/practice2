package com.ohgiraffers.model.dto;

public class StaffDTO {
    private int staffId;
    private String staffName;
    private String staffPhone;
    private String hireDate;
    private String workStatus;
    private String managerName;

    public StaffDTO(){}

    public StaffDTO(int staffId, String staffName, String staffPhone, String hireDate, String workStatus, String managerName) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffPhone = staffPhone;
        this.hireDate = hireDate;
        this.workStatus = workStatus;
        this.managerName = managerName;
    }
    public StaffDTO(String staffName, String staffPhone, String hireDate, String workStatus, String managerName) {

        this.staffName = staffName;
        this.staffPhone = staffPhone;
        this.hireDate = hireDate;
        this.workStatus = workStatus;
        this.managerName = managerName;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return "직원 {" +
                "직원 ID : " + staffId +
                ", 직원 이름 : '" + staffName + '\'' +
                ", 직원 전화번호 : '" + staffPhone + '\'' +
                ", 입사일 : '" + hireDate + '\'' +
                ", 출근 여부 : '" + workStatus + '\'' +
                ", 담당 관리자 : '" + managerName + '\'' +
                '}';
    }
}
