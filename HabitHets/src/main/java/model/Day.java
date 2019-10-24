package model;

import java.time.LocalDateTime;

public class Day {
    private LocalDateTime ldt;

    public Day(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    int getYearNbr(){
        return ldt.getYear();
    }

    public int getMonthNbr() {return ldt.getMonthValue();}

    public String getString() {
        return "" + ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "";
    }

    public String getWeekDayString() {
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
