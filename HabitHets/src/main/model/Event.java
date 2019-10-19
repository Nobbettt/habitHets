package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    private String getColor() {
        return color;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void setColor(String color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
