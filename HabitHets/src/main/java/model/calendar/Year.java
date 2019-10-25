package model.calendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Year{
    private List<Month> months;
    private Integer year;

    /**
     * The constructor of the year class that creates a list of months of that year and sets the months attribute to
     * that list.
     * @param year the actual year that a given object represents
     */
    public Year(int year) {
        this.months = getMonths(year);
        this.year = year;
    }

    /**
     * A method that creates a list of all the months a year
     * @param year the year that the months is in
     * @return a list of all the months of a year
     */
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

    /**
     * A getter of the list of month that this year contains
     * @return the list of months in the year
     */
    List<Month> getMonths() {
        return months;
    }

    /**
     * A method that given a int returns the month that matches that ints value, returns null if no such month exist
     * @param month the month value of the month that should be returned
     * @return the month in this year that has that month value, returns null if there is no such month
     */
    Month getMonth(int month){
        for (Month tmpMonth : getMonths()){
            if(tmpMonth.getDays().get(0).getLdt().getMonthValue() == month){
                return tmpMonth;
            }
        }
        return null;
    }

    /**
     * A method that given a LocalDateTime finds the Month object that the LocalDateTime is in, if there is no such
     * month in this year the method returns null
     * @param ldt the LocalDateTime that the corresponding Month of which should be returned
     * @return a month that the LocalDateTime is represented by or null if there is no such month
     */

    Month getMonthFromLDT(LocalDateTime ldt){
        for (Month month : getMonths()){
            if (month.getDays().get(0).getLdt().getMonthValue() == ldt.getMonthValue()){
                return month;
            }
        }
        return null;
    }

    /**
     * @return what year this year represent as a String object
     */
    public String getString() {
        return year.toString();
    }

}
