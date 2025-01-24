package com.product.borg.model.remote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsgardUser {

    private int id;

    private byte verified;

    private byte deleted;

    private int updatedBy;

    private String userName;

    private BigInteger contactNumber;

    private String email;

    private String password;

    private String roles;

    private String appType;

    private String Imei;

    private String gCMRegistrationId;

    private AsgardUserPropertyMap asgardUserPropertyMap;

    public AsgardUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }


    public BigInteger getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(BigInteger mobileNumber) {
        this.contactNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getVerified() {
        return verified;
    }

    public void setVerified(byte verified) {
        this.verified = verified;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public String getgCMRegistrationId() {
        return gCMRegistrationId;
    }

    public void setgCMRegistrationId(String gCMRegistrationId) {
        this.gCMRegistrationId = gCMRegistrationId;
    }

    public AsgardUserPropertyMap getAsgardUserPropertyMap() {
        return asgardUserPropertyMap;
    }

    public void setAsgardUserPropertyMap(AsgardUserPropertyMap asgardUserPropertyMap) {
        this.asgardUserPropertyMap = asgardUserPropertyMap;
    }
}