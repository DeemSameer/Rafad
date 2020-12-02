package com.example.rafad;

public class MAinModel {

    Integer langLogo;
    String langName;


    public MAinModel(Integer langLogo, String langName){
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
