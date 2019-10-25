package model.calendar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Month{
    List<Day> days;
    private int monthNb;

    /**
     * The constructor for the month class. Given a year and a month number the month will create a a list of all
     * the day and set the attribute monthNb to this month's month value
     * @param year the year this month is in
     * @param monthNb what month of the year the month is
     */
    Month(int year, int monthNb) {
        this.monthNb = monthNb;
        this.days = getDays(year, monthNb);
    }

    /**
     * A method that is called when the month is created that creates all the days of that month
     * @param year the year that this month is in
     * @param monthNb the month number of this month
     * @return A list of the days that the month contains
     */
    private List<Day> getDays(int year, int monthNb){
        LocalDateTime ldt = LocalDateTime.of(year, monthNb,1,0,0);
        List<Day> tmpDays = new ArrayList<>();
        while (ldt.getMonthValue() == monthNb){
            Day d = new Day(ldt);
            tmpDays.add(d);
            ldt = ldt.plusDays(1);
        }
        return tmpDays;
    }

    /**
     * A getter for the list of
     * @return the list of days in the month
     */

    List<Day> getDays() {
        return days;
    }

    /**
     * A method that returns the day object of a date given a LocalDatTime of that date
     * @param ldt the LocalDateTime that you want the corresponding Day of
     * @return the day that is represented by the given LocalDateTime
     */

    Day getDayFromLDT(LocalDateTime ldt){
        for (Day day : getDays()){
            if (day.getLdt().getDayOfMonth() == ldt.getDayOfMonth() && day.getMonthNbr() == ldt.getMonthValue() && day.getLdt().getYear() == ldt.getYear()){
                return day;
            }
        }
        return null;
    }

    /**
     * A method that checks the value of the month and return the corresponding String
     * @return the String of what month this is
     */

    String getString() {
        switch (monthNb) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Default month";
        }
    }
}
