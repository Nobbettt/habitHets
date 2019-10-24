package main.model;

import java.time.LocalDateTime;

public class Event {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String location;
    private String description;
    private String color;

    public Event(int id, LocalDateTime startTime, LocalDateTime endTime, String title, String location, String description, String color) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.location = location;
        this.description = description;
        this.color = color;
    }

    public Event(int id, LocalDateTime startTime, LocalDateTime endTime, String title) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }


    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getTitle() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String timeString() {
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
