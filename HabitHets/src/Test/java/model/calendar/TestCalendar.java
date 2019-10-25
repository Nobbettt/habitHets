package model.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class TestCalendar {

    @Test
    public void testInstant(){
        Calender calender = new Calender();
        Assert.assertTrue(calender != null);
    }

    @Test
    public void testGetYears(){
        Calender calender = new Calender();
        Assert.assertEquals(5, calender.getYears(2,2).size());
        Assert.assertEquals("2024",calender.getYears(1, 5).get(6).getString());
    }

    @Test
    public void getYear(){
        Calender calender = new Calender();
        Assert.assertEquals("2021",calender.getYear(2021).getString());
    }

    @Test
    public void getWeek(){
        Calender calender = new Calender();
        Assert.assertEquals("Monday 11",calender.getWeekFromLDT(LocalDateTime.of(2019,11,14,01,01)).get(0).getWeekDayString());
    }

    @Test
    public void getMonth(){
        Calender calender = new Calender();
        Assert.assertEquals("November",calender.getMonth(LocalDateTime.of(2019,11,14,01,01)).getString());
    }

    @Test
    public void getWeekNbr(){
        Calender calender = new Calender();
        Assert.assertEquals(43, calender.getWeekFromLdt(LocalDateTime.of(2019,10,24,01,01)));
    }

    @Test
    public void getAWeekOfDates(){
        Calender calender = new Calender();
        Assert.assertTrue(calender.getLdtWeekFromLdt(LocalDateTime.of(2019,12,12,01,01)).size() == 7);
        Assert.assertEquals(23, calender.getLdtWeekFromLdt(LocalDateTime.of(2019,12,24,01,01)).get(0).getDayOfMonth());
    }

    @Test
    public void getMonthString(){
        Calender calender = new Calender();
        Assert.assertEquals("April", calender.getMonthString(LocalDateTime.of(2018, 4,10,01,01)));
    }

    @Test
    public void getWeekday(){
        Calender calender = new Calender();
        Assert.assertEquals("Thursday 24", calender.getWeekdayString(LocalDateTime.of(2019, 10,24,01,01)));
    }

    @Test
    public void getAMonthOfDates(){
        Calender calender = new Calender();
        Assert.assertEquals(31, calender.getLdtMonthFromDate(LocalDateTime.of(2021, 10, 3,01,01)).size());
        Assert.assertEquals(4, calender.getLdtMonthFromDate(LocalDateTime.of(2020, 12, 15,01,01)).get(3).getDayOfMonth());
        Assert.assertEquals(DayOfWeek.WEDNESDAY, calender.getLdtMonthFromDate(LocalDateTime.of(2019, 12, 15,01,01)).get(3).getDayOfWeek());
    }

    @Test
    public void getAYearOfDates(){
        Calender calender = new Calender();
        Assert.assertEquals(12,calender.getLdtYearFromDate(LocalDateTime.of(2019,8,8,8,8)).size());
        Assert.assertEquals(12, calender.getLdtYearFromDate(LocalDateTime.of(2020,01,01,01,01)).get(11).getMonthValue());
    }

}
