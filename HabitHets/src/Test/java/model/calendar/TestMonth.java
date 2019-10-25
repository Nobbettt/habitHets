package model.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * A test class for the Month class to ensure that its methods works as expected
 */
public class TestMonth {

    /**
     * Tests if given a LocalDateTime the getDayFromLDT method returns the Day represented by that date
     */
    @Test
    public void testGetDayFromLdt(){
        Month m = new Month(2019, 2);
        Assert.assertEquals(null, m.getDayFromLDT(LocalDateTime.now()));
        Assert.assertEquals(null,m.getDayFromLDT(LocalDateTime.of(2020, 2, 16,01,01)));
        Assert.assertEquals("Saturday 16",m.getDayFromLDT(LocalDateTime.of(2019, 2, 16,01,01)).getWeekDayString());
    }

    /**
     * Tests if the method getString in Month gives the corrects month string given a a month number
     */
    @Test
    public void testMonthString(){
        Month m = new Month(2020, 12);
        Assert.assertEquals("December", m.getString());
    }
}
