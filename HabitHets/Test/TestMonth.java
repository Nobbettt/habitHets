import main.model.Month;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TestMonth {

    @Test
    public void testGetDayFromLdt(){
        Month m = new Month(2019, 2);
        Assert.assertEquals(null, m.getDayFromLDT(LocalDateTime.now()));
        Assert.assertEquals(null,m.getDayFromLDT(LocalDateTime.of(2020, 2, 16,01,01)));
        Assert.assertEquals("Saturday 16",m.getDayFromLDT(LocalDateTime.of(2019, 2, 16,01,01)).getWeekDayString());
    }

    @Test
    public void testMonthString(){
        Month m = new Month(2020, 12);
        Assert.assertEquals("December", m.getString());
    }
}
