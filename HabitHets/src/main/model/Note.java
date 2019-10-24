package main.model;

import java.time.LocalDate;

public class Note {
    private int id;
    private String description;
    private LocalDate day;

    Note(int id, String description, LocalDate day) {
        this.description = description;
        this.day = day;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

}



