package com.example.pema_projekt;

/**
 * Alarm class to work with firebase using alarm objects
 */
public class Alarm {

    private String timer;
    private String interval;

    /**
     * Empty constructor for alarm. Needed for firebase to work.
     */
    public Alarm(){
    }

    public Alarm(String time, String interval){
        this.timer = time;
        this.interval = interval;
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

}
