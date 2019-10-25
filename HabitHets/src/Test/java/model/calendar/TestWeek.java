package model.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Test is week is working as expected
 */
public class TestWeek {
    @Test
    public void testGetDayFromLtd(){
        Calender calender = new Calender();
        LocalDateTime testLTD = LocalDateTime.of(2019, 12, 12, 0, 0);
        Day testDay = calender.getDayFromLDT(testLTD);
        Assert.assertEquals("12/12", testDay.getString());
    }

    /**
     * Tests is the correct week is returned given an LocalDateTime
     */
    @Test
    public void testGetWeekFromLdt(){
        LocalDateTime testTime = LocalDateTime.now();
        Calender calender = new Calender();
        List<Day> dayList = calender.getWeekFromLDT(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("27/10", dayList.get(dayList.size()-1).getString());
    }

    /**
     * Tests is the correct week is returned before new year
     */
    @Test
    public void testGetWeekFromLdtBeforeNewYear(){
        LocalDateTime testTime = LocalDateTime.of(2019,12,30,00,00);
        Calender calender = new Calender();
        List<Day> dayList = calender.getWeekFromLDT(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("30/12", dayList.get(0).getString());
        Assert.assertEquals("5/1", dayList.get(dayList.size()-1).getString());
    }

    /**
     * Tests is the correct week is returned after new year
     */
    @Test
    public void testGetWeekFromLdtAfterNewYear() {
        LocalDateTime testTime = LocalDateTime.of(2021, 1, 2, 00, 00);
        Calender calender = new Calender();
        List<Day> dayList = calender.getWeekFromLDT(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("28/12", dayList.get(0).getString());
        Assert.assertEquals("3/1", dayList.get(dayList.size() - 1).getString());
    }

    /**
     * Tests is the correct week is returned given an LocalDateTime
     */
    @Test
    public void testSameDay(){
        Calender calender = new Calender();
        LocalDateTime testTime = LocalDateTime.of(2019, 1, 1, 00, 00);
        Day day = calender.getDayFromLDT(testTime);
        Assert.assertTrue(day == calender.getYear(2019).getMonth(1).getDays().get(0));
    }

    /**
     * Test if a calendar is accidentally created twice is the same calendar
     */
    @Test
    public void testSameCalendar(){
        Calender calender1 = new Calender();
        Calender calender2 = new Calender();
        Assert.assertTrue(calender2.getYear(2019).getMonth(1).getDays().get(0) == calender1.getYear(2019).getMonth(1).getDays().get(0));
    }
}
