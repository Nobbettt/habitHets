package model;
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

    public List<Month> getMonths() {
        return months;
    }

    public Month getMonth(int month){
        for (Month tmpMonth : getMonths()){
            if(tmpMonth.getDays().get(0).getLdt().getMonthValue() == month){
                return tmpMonth;
            }
        }
        return null;
    }

    public Day getDayWithWeek(int week){
        for (Month month : getMonths()){
            if (month.getFirstWeek() < week && month.getLastWeek() >= week){
                return month.getFirstDayInWeek(week);
            }
        }
        System.out.println("NO SUCH WEEK");
        return null;
    }

    public Month getMonthFromLDT(LocalDateTime ldt){
        for (Month month : getMonths()){
            if (month.getDays().get(0).getLdt().getMonthValue() == ldt.getMonthValue()){
                return month;
            }
        }
        System.out.println("THERE IS NO SUCH MONTH");
        return null;
    }
}
