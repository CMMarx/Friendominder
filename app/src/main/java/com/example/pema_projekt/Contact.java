package com.example.pema_projekt;

public class Contact{

    private String Name;
    private String Phone;
    //private int Photo;

    public Contact() {
    }

    public Contact(String name, String phone) {
        Name = name;
        Phone = phone;
        //Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    /*
    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

     */

    @Override
    public boolean equals(Object object){

        if (object == null){
            return false;
        }
        return Name != null && this.Phone.equals(((Contact) object).getPhone());
    }

    @Override
    public int hashCode(){
        return 0;
    }
}
