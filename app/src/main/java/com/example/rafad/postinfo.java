package com.example.rafad;

import android.net.Uri;

public class postinfo {
    String itemID;
    String imageID;
    String UID;
    String des;
    String cat;
    String tit;
    String isRe;
    String BID;
    String BN;
    String BS;
    String date;
    String Demail;
    String Bemail;

    public postinfo(String itemID1, String UID1, String imageUri1 , String des1 , String cat1, String tit1,String isRe ,String date1,String dummy,String Demail){
        itemID = itemID1;
        UID=UID1;
        imageID = imageUri1;
        des = des1;
        cat=cat1;
        tit=tit1;
        this.isRe=isRe;
        date =date1;
        this.Demail=Demail;
    }
    public postinfo(String itemID1, String UID1, String imageUri1,String tit){
        itemID = itemID1;
        UID=UID1;
        imageID = imageUri1;
        this.tit=tit;

    }
    public postinfo(String itemID1, String UID1, String imageUri1,String tit,String BN,String BS,String isRe,String BID){
        itemID = itemID1;
        UID=UID1;
        imageID = imageUri1;
        this.tit=tit;
        this.BN=BN;
        this.BS=BS;
        this.isRe=isRe;
        this.BID=BID;



    }
    public String getUID() {
        return UID;
    }
    public String getDemail() {
        return Demail;
    }

    public void setBS(String BS) {
        this.BS = BS;
    }
    public void setBem(String Bemail) {
        this.Bemail = Bemail;
    }

    public void setBN(String BN) {
        this.BN = BN;
    }
    public void setBID(String BID) {
        this.BID = BID;
    }
}
