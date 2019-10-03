package main.java.model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Day {
    private LocalDateTime ldt;

    public Day(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public int getYearNbr(){
        return ldt.getYear();
    }

    public int getWeekNr(){
        Calendar javaCal = new GregorianCalendar();
        javaCal.set(getLdt().getYear(), getLdt().getMonthValue(), getLdt().getDayOfMonth());
        return javaCal.get(javaCal.WEEK_OF_YEAR);
    }

    public String getDateString() {
        return "" + ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "";
    }
}
