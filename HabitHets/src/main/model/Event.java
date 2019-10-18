package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event implements IPlanable{
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

    @Override
    public List<String> getInfo() {
        List<String> info = new ArrayList<>();
        info.add(title); //0
        info.add(Integer.toString(id)); //1
        info.add(location); //2
        info.add(description); //3
        info.add(Integer.toString(startTime.getYear()));//4
        info.add(Integer.toString(startTime.getDayOfYear()));//5
        info.add(Integer.toString(startTime.getHour()));//6
        info.add(Integer.toString(startTime.getMinute()));//7
        info.add(Integer.toString(endTime.getHour())); //8
        info.add(Integer.toString(endTime.getMinute()));//9

        return info;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
