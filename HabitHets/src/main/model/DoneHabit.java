package main.model;

import java.time.LocalDate;

public class DoneHabit {
    private LocalDate date;

    public DoneHabit() {
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }
}
