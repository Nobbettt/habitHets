package main.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class Note{
    private int id;
    private String description;
    private LocalDateTime day;

    private Note(int id, String Description, LocalDateTime day) {
        this.description = description;
        this.day = day;
        this.id = id;
    }

    public void setDescription(String description) {
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



