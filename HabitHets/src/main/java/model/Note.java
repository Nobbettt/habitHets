package model;

import java.time.LocalDate;

/**
 * This class defines what a note is and all the variables a note needs.
 */
class Note {
    private int id;
    private String description;
    private LocalDate day;

    /**
     * This is the only constructor of the Note class.
     * It contains all of the instance variables. This makes
     * sure that there is no need of adding exceptions to the class
     * methods. (Exceptions that handles if a variable is not set).
     * @param id
     * @param description
     * @param day
     */
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

    int getId(){
        return id;
    }

    LocalDate getDay() {
        return day;
    }

    void setId(int id) {
        this.id = id;
    }

    void setDay(LocalDate day) {
        this.day = day;
    }

}



