package com.example.rafad.ChatJava;

public class Chat {

    private String sender;
    private String receiver;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    private String message;
    int type;

    public Chat(String sender, String receiver, String message){

        this.sender=sender;
        this.receiver=receiver;
        this.message=message;
    }
    public Chat(String message,String time,String date,int type){
        this.message=message;
        this.time=time;
        this.date=date;
        this.type=type;
    }

    public Chat(){}


    public String getSender(){
        return sender;
    }

    public void setSender(String sender){
        this.sender=sender;
    }

    public String getReceiver(){
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
