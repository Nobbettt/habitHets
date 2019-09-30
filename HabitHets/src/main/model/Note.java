package main.model;

import java.time.LocalDateTime;

public class Note {

    private String title;
    private String description;
    private int id;
    private LocalDateTime day;

    public Note(String title) {
        this.title = title;
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
}



