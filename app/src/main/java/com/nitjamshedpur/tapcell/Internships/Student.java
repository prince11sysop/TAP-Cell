package com.nitjamshedpur.tapcell.Internships;

public class Student {

    private  String studName;
    private String companyName;
    private  String photoUrl;

    public  Student(){

    }

    public Student(String studentName, String companyName, String photoUrl) {
        this.studName = studentName;
        this.companyName = companyName;
        this.photoUrl = photoUrl;
    }


    public String getStudName() {
        return studName;
    }

    public void setStudentName(String studentName) {
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
