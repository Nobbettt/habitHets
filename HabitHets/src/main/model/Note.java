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

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
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



