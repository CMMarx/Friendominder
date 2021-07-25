package com.example.pema_projekt.Groups;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Group {

    private int groupId;

    private String name;
    //private int photo;
    //private Set<Contact> members = new HashSet<>();

    public Group(){

    }

    public Group(int id, String name){
        this.groupId = id;
        this.name = name;
        //this.photo = R.drawable.account_image;
    }

    /**
    public Group(int id, String name,int photo){
        this.groupId = id;
         this.name = name;
         this.photo = photo;
    }

    public Group(int id, String name,int photo, Set members){
        this.groupId = id;
        this.name = name;
        this.photo = photo;
        this.members = members;
    }
     **/




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
    public int getPhoto() {
        return photo;
    }


    public void setPhoto(int photo) {
        this.photo = photo;
    }
     **/
}
