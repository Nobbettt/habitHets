package model;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private String title;
    private String description;
    private LocalDateTime day;

    public Note(int id, String title, String Description, LocalDateTime day) {
        this.title = title;
        this.description = description;
        this.day = day;
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }
}



