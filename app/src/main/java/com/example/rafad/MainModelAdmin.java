package com.example.rafad;

public class MainModelAdmin {

    Integer langLogo;
    String langName;


    public MainModelAdmin(Integer langLogo, String langName){
        this.langLogo=langLogo;
        this.langName=langName;
    }


    public Integer getLangLogo(){
        return langLogo;
    }

    public String getLangName(){
        return langName;
    }
}
