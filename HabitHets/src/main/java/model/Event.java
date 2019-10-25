package model;

import java.time.LocalDateTime;

public class Event {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String location;
    private String description;
    private String color;

    /**
     * The constructor of the Event class that sets all is's values upon creation
     * @param id the id of the Event
     * @param startTime a LocalDateTime that represents the start time of the event
     * @param endTime a LocalDateTime that represents the end time of the event
     * @param title the title of the event
     * @param location the location of the event as a String
     * @param description a description of the event
     * @param color the color of the event
     */
    public Event(int id, LocalDateTime startTime, LocalDateTime endTime, String title, String location, String description, String color) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.location = location;
        this.description = description;
        this.color = color;
    }

    String getColor() {
        return color;
    }

    int getId() {
        return id;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    LocalDateTime getEndTime() {
        return endTime;
    }

    String getTitle() {
        return title;
    }

    String getLocation() {
        return location;
    }

    String getDescription() {
        return description;
    }

    void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    void setLocation(String location) {
        this.location = location;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks the start time of the event and reformats it as a String and returns that String
     * @return a String the start time of the event in the format HH:mm (Hour:Minute)
     */
    String timeString() {
        String hour;
        String minute;
        if (startTime.getHour() < 10) {
            hour = "0" + startTime.getHour();
        } else {
            hour = "" + startTime.getHour();
        }
        if (startTime.getMinute() < 10) {
            minute = "0" + startTime.getMinute();
        } else {
            minute = "" + startTime.getMinute();
        }
        return hour + ":" + minute;
    }
}
