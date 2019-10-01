import main.model.EventHandler;
import main.model.HabitHandler;
import org.junit.Assert;
import org.junit.Test;

public class TestEvent {
    @Test
    public void testEvent(){
        EventHandler eventHandler = EventHandler.getInstant();
        eventHandler.add();
        eventHandler.add();
        eventHandler.add();
        eventHandler.add();
        Assert.assertEquals(4,eventHandler.getEventList().size());
        Assert.assertEquals(0, eventHandler.getEventList().get(0).getId());
        Assert.assertEquals(1,eventHandler.getEventList().get(1).getId());
        Assert.assertEquals(2,eventHandler.getEventList().get(2).getId());
        Assert.assertEquals(3,eventHandler.getEventList().get(3).getId());

    }


}
