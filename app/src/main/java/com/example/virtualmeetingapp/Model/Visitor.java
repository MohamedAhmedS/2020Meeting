package com.example.virtualmeetingapp.Model;

public class Visitor extends User {
    private String image1Url;
    //    private String image2Url;
    private String passwordVisitor;
    private String descriptionVisitor;
    private boolean isApprovedByAdmin;
    private boolean isOffListApvByOfficer;
    private boolean isPriListApvByOfficer;
    private boolean isChatApvByOfficer;

    public Visitor() {
        image1Url = "";
        passwordVisitor = "";
        descriptionVisitor = "";
        isApprovedByAdmin = false;
        isOffListApvByOfficer = false;
        isPriListApvByOfficer = false;
        isChatApvByOfficer = false;
    }

    public Visitor(String userType, String userName, String phoneNo, String userEmail, String token, String descriptionVisitor,
                   String passwordVisitor, Boolean isApprovedByAdmin, Boolean isOffListApvByOfficer, Boolean isPriListApvByOfficer,
                   Boolean isChatApvByOfficer, String image1Url) {
        super(userType, userName, phoneNo, userEmail, token);
        this.descriptionVisitor = descriptionVisitor;
        this.passwordVisitor = passwordVisitor;
        this.isApprovedByAdmin = isApprovedByAdmin;
        this.isOffListApvByOfficer = isOffListApvByOfficer;
        this.isPriListApvByOfficer = isPriListApvByOfficer;
        this.isChatApvByOfficer = isChatApvByOfficer;
        this.image1Url = image1Url;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public boolean isApprovedByAdmin() {
        return isApprovedByAdmin;
    }

    public void setApprovedByAdmin(boolean approvedByAdmin) {
        isApprovedByAdmin = approvedByAdmin;
    }

    public boolean isOffListApvByOfficer() {
        return isOffListApvByOfficer;
    }

    public void setOffListApvByOfficer(boolean offListApvByOfficer) {
        isOffListApvByOfficer = offListApvByOfficer;
    }

    public boolean isPriListApvByOfficer() {
        return isPriListApvByOfficer;
    }

    public void setPriListApvByOfficer(boolean priListApvByOfficer) {
        isPriListApvByOfficer = priListApvByOfficer;
    }

    public String getPasswordVisitor() {
        return passwordVisitor;
    }

    public void setPasswordVisitor(String passwordVisitor) {
        this.passwordVisitor = passwordVisitor;
    }

    public boolean isChatApvByOfficer() {
        return isChatApvByOfficer;
    }

    public void setChatApvByOfficer(boolean chatApvByOfficer) {
        isChatApvByOfficer = chatApvByOfficer;
    }

    public String getDescriptionVisitor() {
        return descriptionVisitor;
    }

    public void setDescriptionVisitor(String descriptionVisitor) {
        this.descriptionVisitor = descriptionVisitor;
    }
}