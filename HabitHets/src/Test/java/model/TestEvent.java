package model;

import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.List;

public class TestEvent {
    EventOrganizer eventOrganizer = new EventOrganizer();
    List<Event> list = EventOrganizer.getEventList();

    @Test
    public void testEventId(){
        clearEventlist();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(4, eventOrganizer.getEventList().size());
        Assert.assertEquals(1, eventOrganizer.getEventList().get(0).getId());
        Assert.assertEquals(2, eventOrganizer.getEventList().get(1).getId());
        Assert.assertEquals(3, eventOrganizer.getEventList().get(2).getId());
        Assert.assertEquals(4, eventOrganizer.getEventList().get(3).getId());

    }

    @Test
    public void testEventModifiers(){
        clearEventlist();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals("Hej", eventOrganizer.getEventList().get(0).getTitle());
        eventOrganizer.getEventList().get(0).setTitle("NewName");
        Assert.assertEquals("NewName", eventOrganizer.getEventList().get(0).getTitle());
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
    }

    @Test
    public void testAddRemove(){
        clearEventlist();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals(2, eventOrganizer.getEventList().size());
        eventOrganizer.remove(eventOrganizer.getEventList().get(1).getId());
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
        eventOrganizer.remove(5);
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
    }

    @Test
    public void testTime(){
        clearEventlist();
        eventOrganizer.addEvent(LocalDateTime.of(2019,10,24,12,00), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertTrue((eventOrganizer.getEventList().get(0).getStartTime().isAfter(LocalDateTime.of(2019,10,9,12,00))));
        Assert.assertTrue((eventOrganizer.getEventList().get(0).getEndTime().isBefore(LocalDateTime.of(2019,10,24,12,00).plusHours(3))));
}

    @Test
    public void testTimestring(){
        clearEventlist();
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "model.TestEvent", "M1212D", "String");
        Assert.assertTrue(eventOrganizer.getEventList().get(0).timeString().equals("10:00"));
    }

    private void clearEventlist(){
        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.getEventList().clear();
    }
}
