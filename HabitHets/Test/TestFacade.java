import main.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestFacade {

    @Test
    public void getNoteAndEdit(){
        Facade f = new Facade();
        NoteOrganizer.addNote("String1", LocalDate.of(2019,10,10));
        Assert.assertEquals("String1", f.getNoteTextFromLdt(LocalDateTime.of(2019, 10,10,10,01)));
        Assert.assertTrue(f.noteOnDay(LocalDateTime.of(2019,10,10,10,10)));
        Assert.assertTrue(!f.noteOnDay(LocalDateTime.of(2019, 10,11,10,01)));
        f.updateNote("NewString",LocalDateTime.of(2019, 10,10,10,01));
        Assert.assertEquals("NewString", f.getNoteTextFromLdt(LocalDateTime.of(2019, 10,10,10,01)));

    }

    @Test
    public void testOverlaps(){
        clearEventlist();
        Facade f = new Facade();
        Assert.assertEquals(0, EventOrganizer.getEventList().size());
        f.createEvent(LocalDateTime.now(),12,00,14,00,"Event1", "Place", "Desc");
        f.createEvent(LocalDateTime.now(),12,30,16,00,"Event2", "Place", "Desc");
        f.createEvent(LocalDateTime.now(),15,30,17,00,"Event3", "Place", "Desc");
        int id = EventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals(3, EventOrganizer.getEventList().size());
        Assert.assertEquals("3.0", String.valueOf(f.calculateOverlaps(LocalDateTime.now(), id)));
    }

    @Test
    public void lenghtOfEvent(){
        clearEventlist();
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title", "loc", "desc");
        int id = EventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals(122,f.getLength(id));
    }

    @Test
    public void createAnEvent(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        clearEventlist();
        Assert.assertTrue(eventOrganizer.getEventList().size() == 0);
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title", "loc", "desc");
        Assert.assertTrue(eventOrganizer.getEventList().size() == 1);
        clearEventlist();
    }

    @Test
    public void removeAnEvent(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title", "loc", "desc");
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
        int id = eventOrganizer.getEventList().get(0).getId();
        f.deleteEvent(id);
        Assert.assertTrue(eventOrganizer.getEventList().size() == 0);
    }

    @Test
    public void editAnEvent(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("title1", f.getEventTitle(id));
        f.editEvent(id,"title2","", "", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Assert.assertEquals("title2", f.getEventTitle(id));
    }

    @Test
    public void getEventStarttime(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("12:12", f.getEventStarttimeString(id));
    }

    @Test
    public void getEventLocation(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("loc", f.getEventLocation(id));
    }

    @Test
    public void getEventDescription(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("desc", f.getEventDesc(id));
    }

    @Test
    public void getEventStartTime(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.of(2019,12,31,00,00),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertTrue(LocalDateTime.of(2019,12,31,12,12).equals(f.getEventStarttime(id)));
    }

    @Test
    public void getEventEndTime(){
        clearEventlist();
        Facade f = new Facade();
        EventOrganizer eventOrganizer = EventOrganizer.getInstant();
        f.createEvent(LocalDateTime.of(2019,12,31,00,00),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertTrue(LocalDateTime.of(2019,12,31,14,14).equals(f.getEventEndtime(id)));
    }

    @Test
    public void creatingATodo(){
        clearTodoLists();
        Facade f = new Facade();
        Assert.assertTrue(TodoOrganizer.getTodoList().size()==0);
        f.createNewTodo("testTodo");
        Assert.assertTrue(TodoOrganizer.getTodoList().size() == 1);
    }

    @Test
    public void getATodoTitle(){
        clearTodoLists();
        Facade f = new Facade();
        f.createNewTodo("testTodo");
        int id = TodoOrganizer.getTodoList().get(0).getId();
        Assert.assertEquals("testTodo", f.getTodoTitle(id));
    }

    @Test
    public void getADoneTodoTitle(){
        clearTodoLists();
        Facade f = new Facade();
        TodoOrganizer.getDoneTodoList().add(new Todo("StringX", 100));
        Assert.assertEquals("StringX", f.getDoneTodoTitle(100));
    }

    @Test
    public void removingATodo(){
        clearTodoLists();
        Facade f = new Facade();
        Assert.assertEquals(0, TodoOrganizer.getTodoList().size());
        f.createNewTodo("TodoTitle");
        int id = TodoOrganizer.getTodoList().get(0).getId();
        Assert.assertEquals(1, TodoOrganizer.getTodoList().size());
        f.removeTodo(id);
        Assert.assertEquals(0, TodoOrganizer.getTodoList().size());
        clearTodoLists();

    }

    @Test
    public void removingADoneTodo() {
        clearTodoLists();
        Facade f = new Facade();
        TodoOrganizer todoOrganizer = TodoOrganizer.getInstant();
        Assert.assertEquals(0, todoOrganizer.getDoneTodoList().size());
        Assert.assertEquals(0, todoOrganizer.getTodoList().size());
        f.createNewTodo("TodoTitle");
        int id = todoOrganizer.getTodoList().get(0).getId();
        Assert.assertEquals(1, todoOrganizer.getTodoList().size());
        f.removeDoneTodo(id);
        Assert.assertEquals(0, todoOrganizer.getTodoList().size());
        Assert.assertEquals(1, todoOrganizer.getDoneTodoList().size());
    }

    @Test
    public void creatingAHabit(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        Assert.assertEquals(1, HabitOrganizer.getHabitList().size());
    }

    @Test
    public void removingAHabit(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(1, HabitOrganizer.getHabitList().size());
        f.removeHabit(id);
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
    }

    @Test
    public void aHabitExist(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        Assert.assertTrue(!f.habitExist("0"));
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertTrue(f.habitExist(""+id));
    }

    @Test
    public void getAHabtiTitle(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals("HabitTitle",f.getHabitTitle(id));
    }

    @Test
    public void getAHabtiColor(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(Color.BLACK.toString(),f.getHabitColor(id));
    }

    @Test
    public void getHabitStreak(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(0, f.getStreak(id));
        f.habitClicked(id);
        Assert.assertEquals(1, f.getStreak(id));
    }

    @Test
    public void getBestHabitStreak(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(0, f.getStreak(id));
        Assert.assertEquals(0, f.getBestStreak(id));
        f.habitClicked(id);
        Assert.assertEquals(1, f.getStreak(id));
        Assert.assertEquals(1, f.getBestStreak(id));
    }

    @Test
    public void habitIsCheckedToday(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        Assert.assertEquals(1, HabitOrganizer.getHabitList().size());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertTrue(!f.habitIsCheckedToday(id));
        f.habitClicked(id);
        Assert.assertTrue(f.habitIsCheckedToday(id));
    }

    @Test
    public void updatingAHabitTitle(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals("HabitTitle", f.getHabitTitle(id));
        f.updateHabitTitle(id, "NewHabitTitle");
        Assert.assertEquals("NewHabitTitle", f.getHabitTitle(id));
    }

    @Test
    public void updatingAHabitColor(){
        clearAllHabits();
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(Color.BLACK.toString(), f.getHabitColor(id));
        f.updateHabitColor(id, Color.CYAN.toString());
        Assert.assertEquals(Color.CYAN.toString(), f.getHabitColor(id));
    }

    private void clearEventlist(){
        EventOrganizer organizer = EventOrganizer.getInstant();
        organizer.getEventList().clear();
    }

    private void clearTodoLists(){
        TodoOrganizer organizer = TodoOrganizer.getInstant();
        organizer.getTodoList().clear();
        organizer.getDoneTodoList().clear();
    }

    private void clearAllHabits(){
        HabitOrganizer organizer = HabitOrganizer.getInstant();
        organizer.getHabitList().clear();

    }

}
