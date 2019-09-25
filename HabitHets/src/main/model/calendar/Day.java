package main.calender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Day {
    private LocalDateTime ldt;
    public Day(LocalDateTime ldt) {
        this.ldt = ldt;
        System.out.println(ldt.getDayOfWeek());
    }
}
