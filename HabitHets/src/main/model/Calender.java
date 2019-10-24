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

    /**
     * Adds years to a list of years that the calendar will hold.
     * @param yearsBack Is one year backwards.
     * @param yearsForward Is one yaer forward.
     * @return
     */

    private List<Year> getYears(int yearsBack, int yearsForward) {
        List<Year> tmpYears = new ArrayList<>();
        for (int i = -yearsBack; i <yearsForward; i++){
            Year y = new Year(LocalDateTime.now().getYear()+i);
            tmpYears.add(y);
        }
        return tmpYears;
    }


    /**
     *
     * @return The list of years.
     */
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

    /**
     * Method that gives a back a week (+6 days) of dates from a given date.
     * @param ldt is a date.
     * @return the week that a given date is in.
     */

    public List<Day> getWeekFromLDT(LocalDateTime ldt){
        List<Day> weekList = new ArrayList<>();

        /**
         * The two lines below make it so that the list returns a week from MON-SUN, if we want SUN - SAT remove
        the -1 from the line exactly below. Remove these two lines completely if you just want 7 consecutive Day objects.
         */

        int dayInWeek = ldt.getDayOfWeek().getValue()-1;
        LocalDateTime firstDayInWeek = ldt.minusDays(dayInWeek);

        for (int i = 0; i<7; i++){
            LocalDateTime tmpLtd = firstDayInWeek.plusDays(i);
            Day tmpDay = getDayFromLDT(tmpLtd);
            weekList.add(tmpDay);
        }
        return weekList;
    }

    /**
     * A method that takes in a date and return which day that date is.
     * @param ldt a date
     * @return which day the given date is.
     */

    public Day getDayFromLDT(LocalDateTime ldt){
        Year toYear = getYear(ldt.getYear());
        Month toMonth = toYear.getMonthFromLDT(ldt);
        return toMonth.getDayFromLDT(ldt);
    }

    /**
     * A method that gives back the year a given date is in.
     * @param ldt a date
     * @return the year the given date is in.
     */

    List<Month> getYearFromLDT(LocalDateTime ldt) {
        Year y = getYear(ldt.getYear());
        List<Month> months = y.getMonths();
        return months;
    }

    /**
     * @param localDateTime a date
     * @return which month a given date is in
     */
    Month getMonth(LocalDateTime localDateTime){
        List<Month> months = getYearFromLDT(localDateTime);
        return months.get(localDateTime.getMonthValue()-1);
    }

    /**
     *
     * @param ldt a date
     * @return what week the given date is in
     */
    public int getWeekFromLdt(LocalDateTime ldt){
        LocalDate date = ldt.toLocalDate();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return date.get(woy);
    }

    /**
     * A method that takes in a date and adds it to a week.
     * @param ldt a date
     * @return a list of dates in a week.
     */
    public List<LocalDateTime> getLdtWeekFromLdt(LocalDateTime ldt){
        List<LocalDateTime> list = new ArrayList<>();
        for (Day day : getWeekFromLDT(ldt)){
            list.add(day.getLdt());
        }
        return list;
    }

    /**
     * A method that puts the days of a month in the month.
     * @param localDateTime a date
     * @return a list of a month containing given the dates in that month.
     */
    public List<LocalDateTime> getLdtMonthFromDate(LocalDateTime localDateTime) {
        List<LocalDateTime> list = new ArrayList<>();
        for (Day day : getMonth(localDateTime).getDays()){
            list.add(day.getLdt());
        }
        return list;
    }

    /**
     * A method that given all the days of a year adds these to a year.
     * @param localDateTime a date
     * @return A list of a year containing all the months.
     */
    public List<LocalDateTime> getLdtYearFromDate(LocalDateTime localDateTime){
        List<LocalDateTime> list = new ArrayList<>();
        for (Month month : getYearFromLDT(localDateTime)){
            list.add(month.getDays().get(0).getLdt());
        }
        return list;
    }

    /**
     *
     * @param ltd a date
     * @return The month value as a string
     */
    public String getMonthString(LocalDateTime ltd){
        return getMonth(ltd).getString();

    }

    /**
     *
     * @param localDateTime a date
     * @return a week value as string
     */
    public String getWeekdayString(LocalDateTime localDateTime){
        return getDayFromLDT(localDateTime).getWeekDayString();
    }

}
