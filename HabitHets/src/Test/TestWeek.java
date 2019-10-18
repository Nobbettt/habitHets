package Test;

import model.Facade;
import model.Calender;
import model.Day;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class TestWeek {
    @Test
    public void testGetDayFromLtd(){
        Calender calender = Calender.getInstant();
        LocalDateTime testLTD = LocalDateTime.of(2019, 12, 12, 0, 0);
        Day testDay = calender.getDayFromLDT(testLTD);
        Assert.assertEquals("Day{ldt=2019-12-12}", testDay.getString());
    }

    @Test
    public void testGetWeekFromLdt(){
        Facade facade = new Facade();
        LocalDateTime testTime = LocalDateTime.now();
        List<Day> dayList = facade.getWeekFromDate(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("Day{ldt=2019-10-6}", dayList.get(dayList.size()-1).getString());
    }


    @Test
    public void testGetWeekFromLdtBeforeNewYear(){
        Facade facade = new Facade();
        LocalDateTime testTime = LocalDateTime.of(2019,12,30,00,00);
        List<Day> dayList = facade.getWeekFromDate(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("Day{ldt=2019-12-30}", dayList.get(0).getString());
        Assert.assertEquals("Day{ldt=2020-1-5}", dayList.get(dayList.size()-1).getString());
    }

    @Test
    public void testGetWeekFromLdtAfterNewYear() {
        Facade facade = new Facade();
        LocalDateTime testTime = LocalDateTime.of(2021, 1, 2, 00, 00);
        List<Day> dayList = facade.getWeekFromDate(testTime);
        Assert.assertEquals(7, dayList.size());
        Assert.assertEquals("MONDAY", dayList.get(0).getLdt().getDayOfWeek().toString());
        Assert.assertEquals("Day{ldt=2020-12-28}", dayList.get(0).getString());
        Assert.assertEquals("Day{ldt=2021-1-3}", dayList.get(dayList.size() - 1).getString());
    }

    @Test
    public void testSameDay(){
        Calender calender = Calender.getInstant();
        LocalDateTime testTime = LocalDateTime.of(2019, 1, 1, 00, 00);
        Day day = calender.getDayFromLDT(testTime);
        Assert.assertTrue(day == calender.getYear(2019).getMonth(1).getDays().get(0));
    }

    @Test
    public void testSameCalendar(){
        Calender calender1 = Calender.getInstant();
        Calender calender2 = Calender.getInstant();
        Assert.assertTrue(calender2.getYear(2019).getMonth(1).getDays().get(0) == calender1.getYear(2019).getMonth(1).getDays().get(0));

    }
}
