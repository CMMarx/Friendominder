package com.example.pema_projekt.Contacts;

public class Contact{

    private String Name;
    private String Phone;

    public Contact() {
    }

    public Contact(String name, String phone) {
        Name = name;
        Phone = phone;

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

}
