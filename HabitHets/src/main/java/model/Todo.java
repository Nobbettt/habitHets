package model;

/**
 * This class
 */

class Todo {
    private String title;
    private int id;

    Todo(String title, int id) {
        this.title = title;
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    int getId() {
        return id;
    }
}
