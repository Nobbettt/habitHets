package main.model;

import java.time.LocalDate;

public class Todo {
    private String title;
    private int id;
    private LocalDate date;


    public Todo(String title, int id) {
        this.title = title;
        this.id = id;

    }


    public Todo(String title) {
        this.title = title;

    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }


}
