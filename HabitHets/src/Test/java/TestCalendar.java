import model.Calender;
import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class TestCalendar {

    @Test
    public void testInstant(){
        Assert.assertTrue(Calender.getInstant() != null);
    }

    @Test
    public void testGetYears(){
        Calender c = Calender.getInstant();
        Assert.assertEquals(5, c.getYears(2,2).size());
        Assert.assertEquals("2024",c.getYears(1, 5).get(6).getString());
    }

    @Test
    public void getYear(){
        Calender c = Calender.getInstant();
        Assert.assertEquals("2021",c.getYear(2021).getString());
    }

    @Test
    public void getWeek(){
        Calender c = Calender.getInstant();
        Assert.assertEquals("Monday 11",c.getWeekFromLDT(LocalDateTime.of(2019,11,14,01,01)).get(0).getWeekDayString());
    }

    @Test
    public void getMonth(){
        Calender c = Calender.getInstant();
        Assert.assertEquals("November",c.getMonth(LocalDateTime.of(2019,11,14,01,01)).getString());
    }

    @Test
    public void getWeekNbr(){
        Calender c = Calender.getInstant();
        Assert.assertEquals(43, c.getWeekFromLdt(LocalDateTime.of(2019,10,24,01,01)));
    }

    @Test
    public void getAWeekOfDates(){
        Calender c = Calender.getInstant();
        Assert.assertTrue(c.getLdtWeekFromLdt(LocalDateTime.of(2019,12,12,01,01)).size() == 7);
        Assert.assertEquals(23, c.getLdtWeekFromLdt(LocalDateTime.of(2019,12,24,01,01)).get(0).getDayOfMonth());
    }

    @Test
    public void getMonthString(){
        Calender c = Calender.getInstant();
        Assert.assertEquals("April", c.getMonthString(LocalDateTime.of(2018, 4,10,01,01)));
    }

    @Test
    public void getWeekday(){
        Calender c = Calender.getInstant();
        Assert.assertEquals("Thursday 24", c.getWeekdayString(LocalDateTime.of(2019, 10,24,01,01)));
    }

    @Test
    public void getAMonthOfDates(){
        Calender c = Calender.getInstant();
        Assert.assertEquals(31, c.getLdtMonthFromDate(LocalDateTime.of(2021, 10, 3,01,01)).size());
        Assert.assertEquals(4, c.getLdtMonthFromDate(LocalDateTime.of(2020, 12, 15,01,01)).get(3).getDayOfMonth());
        Assert.assertEquals(DayOfWeek.WEDNESDAY, c.getLdtMonthFromDate(LocalDateTime.of(2019, 12, 15,01,01)).get(3).getDayOfWeek());
    }

    @Test
    public void getAYearOfDates(){
        Calender c = Calender.getInstant();
        Assert.assertEquals(12,c.getLdtYearFromDate(LocalDateTime.of(2019,8,8,8,8)).size());
        Assert.assertEquals(12, c.getLdtYearFromDate(LocalDateTime.of(2020,01,01,01,01)).get(11).getMonthValue());
    }

}
