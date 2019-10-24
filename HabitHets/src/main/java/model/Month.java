package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Month{
    List<Day> days;
    private int monthNb;


    public Month(int year, int monthNb) {
        this.monthNb = monthNb;
        this.days = getDays(year, monthNb);
    }


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

    public List<Day> getDays() {
        return days;
    }

    public Day getDayFromLDT(LocalDateTime ldt){
        for (Day day : getDays()){
            if (day.getLdt().getDayOfMonth() == ldt.getDayOfMonth() && day.getMonthNbr() == ldt.getMonthValue() && day.getLdt().getYear() == ldt.getYear()){
                return day;
            }
        }
        return null;
    }

    public String getString() {
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
