package main.model;

import java.time.LocalDateTime;

public class Habit {

    private static int id;
    private String title;
    private boolean checkedToday;
    private boolean lastChecked;
    private String description;
    private String color;

    /**
     * A constructor that includes all of the attributes
     * @param id
     * @param title
     * @param checkedToday
     * @param lastChecked
     * @param description
     * @param color
     */
    public Habit(int id, String title, boolean checkedToday, boolean lastChecked, String description, String color) {
        this.id = id;
        this.title = title;
        this.checkedToday = checkedToday;
        this.lastChecked = lastChecked;
        this.description = description;
        this.color = color;
    }

    public int getId(){
        return id;
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheckedToday() {
        return checkedToday;
    }

    public boolean isLastChecked() {
        return lastChecked;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
