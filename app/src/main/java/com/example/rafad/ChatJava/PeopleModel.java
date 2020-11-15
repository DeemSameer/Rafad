package com.example.rafad.ChatJava;

public class PeopleModel {
    private String name;
    private String lastMsg;
    private String time;
    private String date;
    private String UID;
    String unread;
    String mail;

    public PeopleModel(String name, String lastMsg, String time, String date, String UID, String unread,String mail) {
        this.name = name;
        this.lastMsg = lastMsg;
        this.time = time;
        this.date = date;
        this.UID = UID;
        this.unread=unread;
        this.mail=mail;
    }

    public PeopleModel() {

    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
