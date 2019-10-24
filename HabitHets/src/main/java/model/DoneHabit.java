package model;

import java.time.LocalDate;

class DoneHabit {
    private LocalDate date;

    /**
     * checka i idag
     */
    DoneHabit() {
        this.date = LocalDate.now();
    }

    /**
     * checka i en annan dag
     * @param date
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
