package main.model;

import java.time.LocalDateTime;

public class Note{
    private int id;
    private String description;
    private LocalDateTime day;

    Note(int id, String Description, LocalDateTime day) {
        this.description = description;
        this.day = day;
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String setDescription(){
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

    public void onNoteClick(){

    }
}



