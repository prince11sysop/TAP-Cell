package com.nitjamshedpur.tapcell.Placements;

public class Student {

    private  String studName;
    private String companyName;
    private  String photoUrl;
    private int cnfStatus;

    public  Student(){

    }

    public Student(String studentName, String companyName, String photoUrl,int cnfStatus) {
        this.studName = studentName;
        this.companyName = companyName;
        this.photoUrl = photoUrl;
        this.cnfStatus=cnfStatus;
    }

    public int getCnfStatus() {
        return cnfStatus;
    }

    public void setCnfStatus(int cnfStatus) {
        this.cnfStatus = cnfStatus;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studentName) {
        this.studName = studentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
