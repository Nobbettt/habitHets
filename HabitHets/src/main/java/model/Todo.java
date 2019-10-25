package model;

public class Todo {
    private String title;
    private int id;

    /**
     * Sets a  id and title to the todo object on creation
     * @param title
     * @param id
     */
    Todo(String title, int id) {
        this.title = title;
        this.id = id;
    }

    /**
     * Returns a todos title
     * @return
     */
    String getTitle() {
        return title;
    }

    /**
     * Sets a todos title
     * @param title
     */
    void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns a todo's id
     * @return
     */
    int getId() {
        return id;
    }
}
