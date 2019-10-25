package model.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * A test class for the Calendar class that communicates between the model and controller so that everything works as
 * expected
 */
public class TestCalendar {

    /**
     * A test that checks if the getYears method works as expected
     */
    @Test
    public void testGetYears(){
        Calender calender = new Calender();
        Assert.assertEquals(5, calender.getYears(2,2).size());
        Assert.assertEquals("2024",calender.getYears(1, 5).get(6).getString());
    }

    /**
     * A test for the method getYear to see if it gets the year as expected
     */
    @Test
    public void getYear(){
        Calender calender = new Calender();
        Assert.assertEquals("2021",calender.getYear(2021).getString());
    }

    /**
     * A test for the method getWeek that tests if it gets the week as expected
     */
    @Test
    public void getWeek(){
        Calender calender = new Calender();
        Assert.assertEquals("Monday 11",calender.getWeekFromLDT(LocalDateTime.of(2019,11,14,01,01)).get(0).getWeekDayString());
    }
    /**
     * A test for the method getMonth that tests if it gets the month as expected
     */
    @Test
    public void getMonth(){
        Calender calender = new Calender();
        Assert.assertEquals("November",calender.getMonth(LocalDateTime.of(2019,11,14,01,01)).getString());
    }

    /**
     * A test for the method getWeekNbr that tests if it gets the week number as expected
     */
    @Test
    public void getWeekNbr(){
        Calender calender = new Calender();
        Assert.assertEquals(43, calender.getWeekFromLdt(LocalDateTime.of(2019,10,24,01,01)));
    }

    /**
     * A test for the method getAWeekOfDates that tests if given a LocalDateTime returns a list of correct of localDateTime as expected
     */
    @Test
    public void getAWeekOfDates(){
        Calender calender = new Calender();
        Assert.assertTrue(calender.getLdtWeekFromLdt(LocalDateTime.of(2019,12,12,01,01)).size() == 7);
        Assert.assertEquals(23, calender.getLdtWeekFromLdt(LocalDateTime.of(2019,12,24,01,01)).get(0).getDayOfMonth());
    }

    /**
     * A test for the method getMonthString that tests if given a LocalDateTime gets the Month as String as it is expected too
     */
    @Test
    public void getMonthString(){
        Calender calender = new Calender();
        Assert.assertEquals("April", calender.getMonthString(LocalDateTime.of(2018, 4,10,01,01)));
    }

    /**
     * A test for the method getWeekday that tests if given a LocalDateTime gets the weekday string as expected
     */
    @Test
    public void getWeekday(){
        Calender calender = new Calender();
        Assert.assertEquals("Thursday 24", calender.getWeekdayString(LocalDateTime.of(2019, 10,24,01,01)));
    }
    /**
     * A test for the method getAMonthOfDates that tests given a LocalDateTime time returns the corresponding month
     */
    @Test
    public void getAMonthOfDates(){
        Calender calender = new Calender();
        Assert.assertEquals(31, calender.getLdtMonthFromDate(LocalDateTime.of(2021, 10, 3,01,01)).size());
        Assert.assertEquals(4, calender.getLdtMonthFromDate(LocalDateTime.of(2020, 12, 15,01,01)).get(3).getDayOfMonth());
        Assert.assertEquals(DayOfWeek.WEDNESDAY, calender.getLdtMonthFromDate(LocalDateTime.of(2019, 12, 15,01,01)).get(3).getDayOfWeek());
    }

    /**
     * A test for the method getAYearOfDates that test if given a LocalDate returns the corresponding list of dates
     */
    @Test
    public void getAYearOfDates(){
        Calender calender = new Calender();
        Assert.assertEquals(12,calender.getLdtYearFromDate(LocalDateTime.of(2019,8,8,8,8)).size());
        Assert.assertEquals(12, calender.getLdtYearFromDate(LocalDateTime.of(2020,01,01,01,01)).get(11).getMonthValue());
    }

}
