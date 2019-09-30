package main.model;



public class Todo {
    private String title;
    private int id;

    public Todo(String title, int id) {
        this.title = title;
        this.id = id;
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
