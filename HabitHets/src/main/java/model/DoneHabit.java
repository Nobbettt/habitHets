package model;

import java.time.LocalDate;

class DoneHabit {
    private LocalDate date;

    DoneHabit() {
        this.date = LocalDate.now();
    }

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
