package model.calendar;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Tests if Year class is working properly
 */
public class TestYear {

    /**
     * Tests if years is returned correctly as a string
     */
    @Test
    public void testYearToString(){
        Year y = new Year(2020);
        Assert.assertEquals("2020", y.getString());
    }

    /**
     * Tests if years are created as expected
     */
    @Test
    public void testYearCreation(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonths().size() == 12);
    }

    /**
     * Test if months are created correctly
     */
    @Test
    public void testGetMonth(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonths().get(1).getString().equals("February"));
    }

    /**
     * Test is right month is returned given a LocalDateTime
     */
    @Test
    public void testGetMonthFromDate(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonthFromLDT(LocalDateTime.now()).getDays().get(0).getMonthNbr() == 10);
    }

    /**
     * Tests is correct month is returned given a int
     */
    @Test
    public void testGetMonthFromInt(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonth(11).getString().equals("November"));

    }
}
