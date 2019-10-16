package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Month implements CalendarAble {
    List<Day> days;
    int monthNb;


    public Month(int year, int monthNb) {
        this.monthNb = monthNb;
        this.days = getDays(year, monthNb);
    }


    public List<Day> getDays(int year, int monthNb){
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

    public int getFirstWeek(){
        Calendar javaCal = new GregorianCalendar();
        LocalDateTime firstDay = getDays().get(0).getLdt();
        javaCal.set(firstDay.getYear(), firstDay.getMonthValue(), 1);
        return javaCal.get(javaCal.WEEK_OF_YEAR);
    }

    public int getLastWeek(){
        Calendar javaCal = new GregorianCalendar();
        int nbrDays = getDays().size() -1;
        LocalDateTime lastDay = getDays().get(nbrDays).getLdt();
        javaCal.set(lastDay.getYear(), lastDay.getMonthValue(), lastDay.getDayOfMonth());
        return javaCal.get(javaCal.WEEK_OF_YEAR);
    }

    public Day getFirstDayInWeek(int week){
        for (Day day : getDays()){
            if (day.getWeekNr() == week){
                return day;
            }
        }
        System.out.println("THAT WEEK DOESN'T EXIST");
        return null;
    }

    public Day getDayFromLDT(LocalDateTime ldt){
        for (Day day : getDays()){
            if (day.getLdt().getDayOfMonth() == ldt.getDayOfMonth()){
                return day;
            }
        }
        System.out.println("THERE IS NO SUCH DAY");
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
                return "Bitch month";
        }
    }
}
