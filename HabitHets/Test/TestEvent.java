import main.model.EventOrganizer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TestEvent {
    @Test
    public void testEventId(){
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(4, eventOrganizer.getEventList().size());
        Assert.assertEquals(0, eventOrganizer.getEventList().get(0).getId());
        Assert.assertEquals(1, eventOrganizer.getEventList().get(1).getId());
        Assert.assertEquals(2, eventOrganizer.getEventList().get(2).getId());
        Assert.assertEquals(3, eventOrganizer.getEventList().get(3).getId());

    }

    @Test
    public void testEventModifiers(){
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(0, eventOrganizer.getEventList().get(0).getId());
        eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("TestEvent", eventOrganizer.getEventList().get(0).getTitle());
        eventOrganizer.getEventList().get(0).setTitle("NewName");
        Assert.assertEquals("NewName", eventOrganizer.getEventList().get(0).getTitle());
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
    }

    @Test
    public void testAddRemove(){
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(2, eventOrganizer.getEventList().size());
        eventOrganizer.remove(0);
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
        Assert.assertEquals(1, eventOrganizer.getEventList().get(0).getId());
        eventOrganizer.remove(5);
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
    }

    @Test
    public void testTime(){
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertTrue((eventOrganizer.getEventList().get(0).getStartTime().isAfter(LocalDateTime.now())));
        Assert.assertTrue((eventOrganizer.getEventList().get(0).getEndTime().isBefore(LocalDateTime.now().plusHours(1))));
    }
}
