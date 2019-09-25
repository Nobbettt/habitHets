package main.model.calendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Year {
    List<Month> months;
    public Year(int year) {
        this.months = getMonths(year);
    }

    private List<Month> getMonths(int year) {
        LocalDateTime ldt = LocalDateTime.of(year, 1,1,0,0);
        List<Month> tmpMonths = new ArrayList<>();
        while (ldt.getYear() == year){
            Month m = new Month(ldt.getYear(), ldt.getMonthValue());
            tmpMonths.add(m);
            ldt = ldt.plusMonths(1);
        }
        return tmpMonths;
    }
}
