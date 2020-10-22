package com.example.rafad;
@SuppressWarnings("SpellCheckingInspection")
public class benDataModel {
    private String UID;
    private String securityNumber;
    private String phoneNumber;
    private String typeOfResidence;
    private String TotalIncome;
    private String userName;
    private String email;
    private String SSN;

    public benDataModel(String UID,String phoneNumber, String typeOfResidence, String totalIncome, String userName, String email, String SSN, String securityNumber) {
        this.UID = UID;
        this.phoneNumber = phoneNumber;
        this.typeOfResidence = typeOfResidence;
        TotalIncome = totalIncome;
        this.userName = userName;
        this.email = email;
        this.SSN = SSN;
        this.securityNumber = securityNumber;
    }
    public benDataModel(String UID,String userName) {
        this.UID = UID;
        this.userName = userName;

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeOfResidence() {
        return typeOfResidence;
    }

    public void setTypeOfResidence(String typeOfResidence) {
        this.typeOfResidence = typeOfResidence;
    }

    public String getTotalIncome() {
        return TotalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        TotalIncome = totalIncome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }


}
