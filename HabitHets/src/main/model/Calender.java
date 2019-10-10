package main.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Calender {
    private static Calender instant;
    protected static List<Year> years;

    private Calender() {
        int yearsBack = 2;
        int yearsForward  = 3;
        this.years = getYears(yearsBack, yearsForward);
    }

    public static Calender getInstant() {
        if (instant == null) {

            instant = new Calender();
            return instant;

        } else {
            return instant;
        }
    }

    public List<Year> getYears(int yearsBack, int yearsForward) {
        List<Year> tmpYears = new ArrayList<>();
        for (int i = -yearsBack; i <yearsForward; i++){
            Year y = new Year(LocalDateTime.now().getYear()+i);
            tmpYears.add(y);
        }
        return tmpYears;
    }

    public List<Year> getYears() {
        return years;
    }

    public Year getYear(int year){
        for (Year tmpYear: getYears()){
            if (tmpYear.getMonths().get(0).getDays().get(0).getYearNbr() == year){
                return tmpYear;
            }
        }
        return null;
    }

    public List<Day> getWeekFromLDT(LocalDateTime ldt){
        List<Day> weekList = new ArrayList<>();

        /*The two lines below make it so that the list returns a week from MON-SUN, if we want SUN - SAT remove
        the -1 from the line exactly below. Remove these to lines completely if you just want 7 consecutive Day objects. */

        int dayInWeek = ldt.getDayOfWeek().getValue()-1;
        LocalDateTime firstDayInWeek = ldt.minusDays(dayInWeek);

        for (int i = 0; i<7; i++){
            LocalDateTime tmpLtd = firstDayInWeek.plusDays(i);
            Day tmpDay = getDayFromLDT(tmpLtd);
            weekList.add(tmpDay);
        }
        return weekList;
    }

    public Day getDayFromLDT(LocalDateTime ldt){
        Year toYear = getYear(ldt.getYear());
        Month toMonth = toYear.getMonthFromLDT(ldt);
        return toMonth.getDayFromLDT(ldt);
    }

    public List<Month> getYearFromLDT(LocalDateTime ldt) {
        Year y = getYear(ldt.getYear());
        return new ArrayList<>(y.getMonths());
    }

}
