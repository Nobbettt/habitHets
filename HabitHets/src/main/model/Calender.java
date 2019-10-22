package main.model;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Locale;

public class Calender {
    private static Calender instant;
    private static List<Year> years;

    private Calender() {
        int yearsBack = 2;
        int yearsForward  = 4;
        years = getYears(yearsBack, yearsForward);
    }

    public static Calender getInstant() {
        if (instant == null) {

            instant = new Calender();
            return instant;

        } else {
            return instant;
        }
    }

    private List<Year> getYears(int yearsBack, int yearsForward) {
        List<Year> tmpYears = new ArrayList<>();
        for (int i = -yearsBack; i <yearsForward; i++){
            Year y = new Year(LocalDateTime.now().getYear()+i);
            tmpYears.add(y);
        }
        return tmpYears;
    }

    private List<Year> getYears() {
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

    List<Month> getYearFromLDT(LocalDateTime ldt) {
        Year y = getYear(ldt.getYear());
        List<Month> months = y.getMonths();
        return months;
    }

    public Month getMonth(LocalDateTime localDateTime){
        List<Month> months = getYearFromLDT(localDateTime);
        return months.get(localDateTime.getMonthValue()-1);
    }

    public int getWeekFromLdt(LocalDateTime ldt){
        LocalDate date = ldt.toLocalDate();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return date.get(woy);
    }

    public List<LocalDateTime> getLdtWeekFromLdt(LocalDateTime ldt){
        List<LocalDateTime> list = new ArrayList<>();
        for (Day day : getWeekFromLDT(ldt)){
            list.add(day.getLdt());
        }
        return list;
    }

    public List<LocalDateTime> getLdtMonthFromDate(LocalDateTime localDateTime) {
        List<LocalDateTime> list = new ArrayList<>();
        for (Day day : getMonth(localDateTime).getDays()){
            list.add(day.getLdt());
        }
        return list;
    }

    public List<LocalDateTime> getLdtYearFromDate(LocalDateTime localDateTime){
        List<LocalDateTime> list = new ArrayList<>();
        for (Month month : getYearFromLDT(localDateTime)){
            list.add(month.getDays().get(0).getLdt());
        }
        return list;
    }

    public String getMonthString(LocalDateTime ltd){
        return getMonth(ltd).getString();

    }

    public List<Day> getDayFromDate(LocalDateTime localDateTime) {
        List<Day> singleDayList = new ArrayList();
        singleDayList.add(getDayFromLDT(localDateTime));
        return singleDayList;
    }

}
