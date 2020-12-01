package com.example.rafad;




public class MainModelBen {

    Integer langLogo;
    String langName;


    public MainModelBen(Integer langLogo, String langName){
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
