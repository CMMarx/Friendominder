package com.example.pema_projekt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Group {

    private String name;
    private int photo;
    private Set<Contact> members = new HashSet<>();

    public Group(){

    }

    public Group(String name,int photo){
         this.name = name;
         this.photo = photo;
    }

    public Group(String name,int photo, Set members){
        this.name = name;
        this.photo = photo;
        this.members = members;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
