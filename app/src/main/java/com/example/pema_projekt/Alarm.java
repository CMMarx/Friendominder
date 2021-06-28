package com.example.pema_projekt;

public class Alarm {

    private String timer;
    private String intervall;

    public Alarm(){
    }

    public Alarm(String time, String intervall){
        this.timer = time;
        this.intervall = intervall;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String time) {
        this.timer = time;
    }

    public String getIntervall() {
        return intervall;
    }

    public void setIntervall(String intervall) {
        this.intervall = intervall;
    }
}
