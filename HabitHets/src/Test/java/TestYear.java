import model.Year;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TestYear {

    @Test
    public void testYearToString(){
        Year y = new Year(2020);
        Assert.assertEquals("2020", y.getString());
    }

    @Test
    public void testYearCreation(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonths().size() == 12);
    }

    @Test
    public void testGetMonth(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonths().get(1).getString().equals("February"));
    }

    @Test
    public void testGetMonthFromDate(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonthFromLDT(LocalDateTime.now()).getDays().get(0).getMonthNbr() == 10);
    }

    @Test
    public void testGetMonthFromInt(){
        Year y = new Year(2019);
        Assert.assertTrue(y.getMonth(11).getString().equals("November"));

    }
}
