package main.model;

import java.time.LocalDate;

public class DoneHabit {
    private LocalDate date;

    /**
     * checka i idag
     */
    public DoneHabit() {
        this.date = LocalDate.now();
    }

    /**
     * checka i en annan dag
     * @param date
     */
    public DoneHabit(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
