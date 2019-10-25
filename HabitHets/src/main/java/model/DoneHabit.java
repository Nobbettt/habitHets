package model;

import java.time.LocalDate;

/**
 * This is a done habit object which only contains a localDateTime to keep track of what days a habit was checked
 */
class DoneHabit {
    private LocalDate date;

    /**
     * Constructor for setting a DoneHabit with today's date
     */
    DoneHabit() {
        this.date = LocalDate.now();
    }

    /**
     * Constructor for setting a DoneHabit with given date
     */
    DoneHabit(LocalDate date) {
        this.date = date;
    }

    LocalDate getDate() {
        return date;
    }

    void setDate(LocalDate date) {
        this.date = date;
    }
}
