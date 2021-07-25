package com.example.pema_projekt.Groups;

public class Group {

    private String name;


    public Group(){

    }

    public Group(int id, String name){
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
