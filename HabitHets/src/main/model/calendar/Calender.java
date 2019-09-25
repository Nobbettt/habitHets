package main.model.calendar;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Calender {
    protected List<Year> years;

    public Calender() {
        LocalDateTime start = LocalDateTime.now().minusYears(2);
        LocalDateTime end = LocalDateTime.now().plusYears(5);
        this.years = getYears(start, end);
    }

    private List<Year> getYears(LocalDateTime start, LocalDateTime end) {
        List<Year> tmpYears = new ArrayList<>();
        while (start.getYear() <= end.getYear()){
            Year y = new Year(start.getYear());
            tmpYears.add(y);
            start.plusYears(1);
        }
        return tmpYears;
    }
}
