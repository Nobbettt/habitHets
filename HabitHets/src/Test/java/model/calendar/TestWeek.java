package model.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class TestWeek {
    @Test
    public void testGetDayFromLtd(){
        Calender calender = Calender.getInstance();
        LocalDateTime testLTD = LocalDateTime.of(2019, 12, 12, 0, 0);
        Day testDay = calender.getDayFromLDT(testLTD);
        Assert.assertEquals("12/12", testDay.getString());
    }

    @Test
    public void testGetWeekFromLdt(){
        LocalDateTime testTime = LocalDateTime.now();
        List<Day> dayList = Calender.getInstance().getWeekFromLDT(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("27/10", dayList.get(dayList.size()-1).getString());
    }


    @Test
    public void testGetWeekFromLdtBeforeNewYear(){
        LocalDateTime testTime = LocalDateTime.of(2019,12,30,00,00);
        List<Day> dayList = Calender.getInstance().getWeekFromLDT(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("30/12", dayList.get(0).getString());
        Assert.assertEquals("5/1", dayList.get(dayList.size()-1).getString());
    }

    @Test
    public void testGetWeekFromLdtAfterNewYear() {
        LocalDateTime testTime = LocalDateTime.of(2021, 1, 2, 00, 00);
        List<Day> dayList = Calender.getInstance().getWeekFromLDT(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("28/12", dayList.get(0).getString());
        Assert.assertEquals("3/1", dayList.get(dayList.size() - 1).getString());
    }

    @Test
    public void testSameDay(){
        Calender calender = Calender.getInstance();
        LocalDateTime testTime = LocalDateTime.of(2019, 1, 1, 00, 00);
        Day day = calender.getDayFromLDT(testTime);
        Assert.assertTrue(day == calender.getYear(2019).getMonth(1).getDays().get(0));
    }

    @Test
    public void testSameCalendar(){
        Calender calender1 = Calender.getInstance();
        Calender calender2 = Calender.getInstance();
        Assert.assertTrue(calender2.getYear(2019).getMonth(1).getDays().get(0) == calender1.getYear(2019).getMonth(1).getDays().get(0));
    }
}
