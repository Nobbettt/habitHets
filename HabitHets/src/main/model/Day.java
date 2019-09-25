package main.model;

import java.time.LocalDateTime;

public class Day {
    private LocalDateTime ldt;
    public Day(LocalDateTime ldt) {
        this.ldt = ldt;
        System.out.println(ldt.getDayOfWeek());
    }
}
