package main.model;

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

    public List<Year> getYears(LocalDateTime start, LocalDateTime end) {
        List<Year> tmpYears = new ArrayList<>();
        while (start.getYear() <= end.getYear()){
            Year y = new Year(start.getYear());
            tmpYears.add(y);
            start.plusYears(1);
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

    public List<Day> getWeekFromWeek(int year, int week){
        List<Day> weekList = new ArrayList<>();

        Year toYear = getYear(year);
        Day day = toYear.getDayWithWeek(week);
        weekList.add(day);
        for (int i = 1; i < 7; i++){
            LocalDateTime tmpLtd = day.getLdt().plusDays(i);
            Day tmpDay = getDayFromLDT(tmpLtd);
            weekList.add(tmpDay);
        }
        return weekList;
    }

    public List<Day> getWeekFromLDT(LocalDateTime ldt){
        List<Day> weekList = new ArrayList<>();
        for (int i = 0; i<7; i++){
            LocalDateTime tmpLtd = ldt.plusDays(i);
            Day tmpDay = getDayFromLDT(tmpLtd);
            weekList.add(tmpDay);
        }
        return weekList;
    }

    private Day getDayFromLDT(LocalDateTime ldt){
        Year toYear = getYear(ldt.getYear());
        Month toMonth = toYear.getMonthFromLDT(ldt);
        return toMonth.getDayFromLDT(ldt);
    }


}
