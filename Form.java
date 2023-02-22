package com.card.my;

import java.util.Date;

public class Form {
    private String serialNo;
    private String name;
    private String fatherName;
    private String address;
    private String whatsapp;
    private String cnic;
    private Date date;

    public Form() {
        // Required default constructor for Firebase object mapping
    }

    public Form(String serialNo, String name, String fatherName, String address, String whatsapp, String cnic, Date date) {
        this.serialNo = serialNo;
        this.name = name;
        this.fatherName = fatherName;
        this.address = address;
        this.whatsapp = whatsapp;
        this.cnic = cnic;
        this.date = date;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
