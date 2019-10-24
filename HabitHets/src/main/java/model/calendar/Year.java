package model.calendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Year{
    private List<Month> months;
    private Integer year;

    public Year(int year) {
        this.months = getMonths(year);
        this.year = year;
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

    List<Month> getMonths() {
        return months;
    }

    Month getMonth(int month){
        for (Month tmpMonth : getMonths()){
            if(tmpMonth.getDays().get(0).getLdt().getMonthValue() == month){
                return tmpMonth;
            }
        }
        return null;
    }

    Month getMonthFromLDT(LocalDateTime ldt){
        for (Month month : getMonths()){
            if (month.getDays().get(0).getLdt().getMonthValue() == ldt.getMonthValue()){
                return month;
            }
        }
        return null;
    }

    public String getString() {
        return year.toString();
    }

}
