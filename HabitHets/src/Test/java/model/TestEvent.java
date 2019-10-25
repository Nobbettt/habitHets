package model;

import org.junit.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEvent {

    static EventOrganizer eventOrganizer = new EventOrganizer();
    static List<Event> events;

    @BeforeClass
    public static void setUp() {
        TxtDbCommunicator.importDb();
        events = copyList();
        eventOrganizer.setEventList(new ArrayList<>());
    }

    @Before
    public void clear(){
        eventOrganizer.getEventList().clear();
    }

    @AfterClass
    public static void resetClass(){
        EventOrganizer.setEventList(events);
    }

    private static List<Event> copyList(){
        List<Event> tmpList = new ArrayList<>();
        for (Event event : eventOrganizer.getEventList()){
            tmpList.add(event);
        }
        return tmpList;
    }

    @Test
    public void testEventModifiers(){
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertEquals("Hej", eventOrganizer.getEventList().get(0).getTitle());
        eventOrganizer.getEventList().get(0).setTitle("NewName");
        Assert.assertEquals("NewName", eventOrganizer.getEventList().get(0).getTitle());
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
    }

    @Test
    public void testAddRemove(){

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
        eventOrganizer.addEvent(LocalDateTime.of(2019,10,24,12,00), 10, 0, 12, 0, "Hej", "Blä", "En string");
        Assert.assertTrue((eventOrganizer.getEventList().get(0).getStartTime().isAfter(LocalDateTime.of(2019,10,9,12,00))));
        Assert.assertTrue((eventOrganizer.getEventList().get(0).getEndTime().isBefore(LocalDateTime.of(2019,10,24,12,00).plusHours(3))));
}

    @Test
    public void testTimestring(){
        eventOrganizer.addEvent(LocalDateTime.now(), 10, 0, 12, 0, "TestEvent", "KUUKBAJS", "String");
        Assert.assertTrue(eventOrganizer.getEventList().get(0).timeString().equals("10:00"));
    }
}
