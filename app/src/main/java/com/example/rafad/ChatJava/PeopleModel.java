package com.example.rafad.ChatJava;

public class PeopleModel {
    private String name;
    private String lastMsg;
    private String time;
    private String date;
    private String UID;

    public PeopleModel(String name, String lastMsg, String time, String date, String UID, String pic) {
        this.name = name;
        this.lastMsg = lastMsg;
        this.time = time;
        this.date = date;
        this.UID = UID;
        this.pic = pic;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    String pic;

}
