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

    /**
     * Constructor for the Alarm object
     * @param time the time of the alarm
     * @param interval the interval in days
     * @param name the name for the alarm
     */
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
     * @return the amount of days in the interval
     */
    public String getInterval() {
        return interval;
    }

    /**
     * Getter method for the name variable
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the name variable
     * @param name the name you want to set it to
     */
    public void setName(String name) {
        this.name = name;
    }

}
