package test.java.model;

import main.java.model.EventHandler;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class TestEvent {
    @Test
    public void testEventId(){
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

    @Test
    public void testEventModifiers(){
        EventHandler eventHandler = EventHandler.getInstant();
        eventHandler.add();
        Assert.assertEquals(0, eventHandler.getEventList().get(0).getId());
        eventHandler.getEventList().get(0).getId();
        Assert.assertEquals("test.java.model.TestEvent", eventHandler.getEventList().get(0).getTitle());
        eventHandler.getEventList().get(0).setTitle("NewName");
        Assert.assertEquals("NewName", eventHandler.getEventList().get(0).getTitle());
        Assert.assertEquals(1, eventHandler.getEventList().size());
    }

    @Test
    public void testAddRemove(){
        EventHandler eventHandler = EventHandler.getInstant();
        eventHandler.add();
        Assert.assertEquals(1,eventHandler.getEventList().size());
        eventHandler.add();
        Assert.assertEquals(2,eventHandler.getEventList().size());
        eventHandler.remove(0);
        Assert.assertEquals(1,eventHandler.getEventList().size());
        Assert.assertEquals(1,eventHandler.getEventList().get(0).getId());
        eventHandler.remove(5);
        Assert.assertEquals(1,eventHandler.getEventList().size());
    }

    @Test
    public void testTime(){
        EventHandler eventHandler = EventHandler.getInstant();
        eventHandler.add();
        Assert.assertTrue((eventHandler.getEventList().get(0).getStartTime().isAfter(LocalDateTime.now())));
        Assert.assertTrue((eventHandler.getEventList().get(0).getEndTime().isBefore(LocalDateTime.now().plusHours(1))));
    }
}
