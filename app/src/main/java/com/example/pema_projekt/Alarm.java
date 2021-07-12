package com.example.pema_projekt;

/**
 * Alarm class to work with firebase using alarm objects
 */
public class Alarm {


    private String timer;
    private String interval;
    private String name;

    /**
     * Empty constructor for alarm. Needed for firebase to work.
     */
    public Alarm(){
    }

    public Alarm(String time, String interval, String name){
        this.timer = time;
        this.interval = interval;
        this.name = name;
    }


    /**
     *Getter method for the timer variable
     * @return the time of the alarm
     */
    public String getTimer() {
        return timer;
    }

    /**
     *Getter method for the interval variable
     * @return the amount of days in the intervall
     */
    public String getInterval() {
        return interval;
    }

    public String getName() {
        return name;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setName(String name) {
        this.name = name;
    }

}
