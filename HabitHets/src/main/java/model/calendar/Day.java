package model.calendar;

import java.time.LocalDateTime;

public class Day {
    private LocalDateTime ldt;

    /**
     * The constructor for the Day class. Sets the LocalDateTime that is used to show which day that is created
     * @param ldt the LocalDateTime that represents what Day this day is
     */
    public Day(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    /**
     * A method that returns the LocalDateTime that represents the day
     * @return the LocalDateTime that represents the day
     */

    LocalDateTime getLdt() {
        return ldt;
    }

    /**
     * A method that gives checks and returns what year this Day is in
     * @return the yearNumber of the day
     */

    int getYearNbr(){
        return ldt.getYear();
    }

    /**
     *  A method that gives checks and returns what month this Day is in
     *  @return the monthNumber of the day
     */

    int getMonthNbr() {return ldt.getMonthValue();}

    /**
     * A method that creates a String that represents the date of Day
     * @return a String that represents the date of the day
     */
    public String getString() {
        return "" + ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "";
    }

    /**
     * A switch case that checks what day of the week this day is and returns a string of the day of week and what day
     * of the month the day is
     * @return the String that represents what day of the week and what day of the month this day is
     */

    String getWeekDayString() {
        String string = "";
        switch (ldt.getDayOfWeek().getValue()) {
            case 1:
                string = "Monday";
                break;
            case 2:
                string = "Tuesday";
                break;
            case 3:
                string = "Wednesday";
                break;
            case 4:
                string = "Thursday";
                break;
            case 5:
                string = "Friday";
                break;
            case 6:
                string = "Saturday";
                break;
            case 7:
                string = "Sunday";
                break;
            default:
                string = "Default Day";
                break;
        }
        return string + " " + ldt.getDayOfMonth();
    }

}
