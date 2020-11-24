package com.example.rafad.Block;

public class BenModelBlock {
    String UID;
    String Count;
    String Name;
    String PhoneNumber;

    public BenModelBlock(String UID, String count, String name, String phoneNumber) {
        this.UID = UID;
        Count = count;
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
