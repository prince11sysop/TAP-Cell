package com.nitjamshedpur.tapcell.Placements;

public class Placement {

    String branchName;
    String branchImage;

    public Placement(String branchName, String branchImage) {
        this.branchName = branchName;
        this.branchImage = branchImage;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchImage() {
        return branchImage;
    }

    public void setBranchImage(String branchImage) {
        this.branchImage = branchImage;
    }

    public Placement(){

    }
}
