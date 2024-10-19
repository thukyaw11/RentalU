package com.minthukyaw.rental.model;

public class PropertyModel {
    private int refNo;
    private String propertyType, noOfRooms, date, rentalPrice, typeOfFurniture, remark, reporterName;

    public PropertyModel(int refNo, String propertyType, String noOfRooms, String date, String rentalPrice, String typeOfFurniture, String remark, String reporterName) {
        this.refNo = refNo;
        this.propertyType = propertyType;
        this.noOfRooms = noOfRooms;
        this.date = date;
        this.rentalPrice = rentalPrice;
        this.typeOfFurniture = typeOfFurniture;
        this.remark = remark;
        this.reporterName = reporterName;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getTypeOfFurniture() {
        return typeOfFurniture;
    }

    public void setTypeOfFurniture(String typeOfFurniture) {
        this.typeOfFurniture = typeOfFurniture;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}
