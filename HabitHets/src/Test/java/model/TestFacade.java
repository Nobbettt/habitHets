package model;

import org.junit.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestFacade {
    static EventOrganizer eventOrganizer = new EventOrganizer();
    static NoteOrganizer noteOrganizer = new NoteOrganizer();
    static HabitOrganizer habitOrganizer = new HabitOrganizer();
    static TodoOrganizer todoOrganizer = new TodoOrganizer();
    static List<Event> events;
    static List<Todo> todos;
    static List<Todo> doneTodos;
    static List<Habit> habits;
    static List<Note> notes;

    @BeforeClass
    public static void setUp() {
        TxtDbCommunicator.importDb();
        events = copyEventList();
        todos = copyTodoList();
        doneTodos = copyDoneTodoList();
        habits = copyHabitList();
        notes = copyNoteList();

        eventOrganizer.setEventList(new ArrayList<>());
        noteOrganizer.setNotesList(new ArrayList<>());
        habitOrganizer.setHabitList(new ArrayList<>());
        todoOrganizer.setTodoList(new ArrayList<>());
        todoOrganizer.setDoneTodoList(new ArrayList<>());
    }

    @Before
    public void clear(){
        eventOrganizer.getEventList().clear();
        noteOrganizer.getNotes().clear();
        habitOrganizer.getHabitList().clear();
        todoOrganizer.getTodoList().clear();
        todoOrganizer.getDoneTodoList().clear();
    }

    @AfterClass
    public static void resetClass(){
        EventOrganizer.setEventList(events);
        NoteOrganizer.setNotesList(notes);
        HabitOrganizer.setHabitList(habits);
        TodoOrganizer.setTodoList(todos);
        TodoOrganizer.setDoneTodoList(doneTodos);
    }

    private static List<Event> copyEventList(){
        List<Event> tmpList = new ArrayList<>();
        for (Event event : eventOrganizer.getEventList()){
            tmpList.add(event);
        }
        return tmpList;
    }

    private static List<Note> copyNoteList(){
        List<Note> tmpList = new ArrayList<>();
        for (Note note : noteOrganizer.getNotes()){
            tmpList.add(note);
        }
        return tmpList;
    }

    private static List<Habit> copyHabitList(){
        List<Habit> tmpList = new ArrayList<>();
        for (Habit habit : habitOrganizer.getHabitList()){
            tmpList.add(habit);
        }
        return tmpList;
    }

    private static List<Todo> copyTodoList(){
        List<Todo> tmpList = new ArrayList<>();
        for (Todo todo : todoOrganizer.getTodoList()){
            tmpList.add(todo);
        }
        return tmpList;
    }

    private static List<Todo> copyDoneTodoList(){
        List<Todo> tmpList = new ArrayList<>();
        for (Todo todo : todoOrganizer.getDoneTodoList()){
            tmpList.add(todo);
        }
        return tmpList;
    }

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
        Facade f = new Facade();
        Assert.assertEquals(0, EventOrganizer.getEventList().size());
        f.createEvent(LocalDateTime.now(),12,00,14,00,"Event1", "Place", "Desc");
        f.createEvent(LocalDateTime.now(),12,30,16,00,"Event2", "Place", "Desc");
        f.createEvent(LocalDateTime.now(),15,30,17,00,"Event3", "Place", "Desc");
        int id = EventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals(3, EventOrganizer.getEventList().size());
        Assert.assertEquals("3.0", String.valueOf(f.countOverlaps(LocalDateTime.now(), id)));
    }

    @Test
    public void lenghtOfEvent(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title", "loc", "desc");
        int id = EventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals(122,f.getLength(id));
    }

    @Test
    public void createAnEvent(){
        Facade f = new Facade();
        Assert.assertTrue(eventOrganizer.getEventList().size() == 0);
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title", "loc", "desc");
        Assert.assertTrue(eventOrganizer.getEventList().size() == 1);
    }

    @Test
    public void removeAnEvent(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title", "loc", "desc");
        Assert.assertEquals(1, eventOrganizer.getEventList().size());
        int id = eventOrganizer.getEventList().get(0).getId();
        f.deleteEvent(id);
        Assert.assertTrue(eventOrganizer.getEventList().size() == 0);
    }

    @Test
    public void editAnEvent(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("title1", f.getEventTitle(id));
        f.editEvent(id,"title2","", "", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Assert.assertEquals("title2", f.getEventTitle(id));
    }

    @Test
    public void getEventStarttime(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("12:12", f.getEventStarttimeString(id));
    }

    @Test
    public void getEventLocation(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("loc", f.getEventLocation(id));
    }

    @Test
    public void getEventDescription(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.now(),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertEquals("desc", f.getEventDesc(id));
    }

    @Test
    public void getEventStartTime(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.of(2019,12,31,00,00),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertTrue(LocalDateTime.of(2019,12,31,12,12).equals(f.getEventStarttime(id)));
    }

    @Test
    public void getEventEndTime(){
        Facade f = new Facade();
        f.createEvent(LocalDateTime.of(2019,12,31,00,00),12,12,14,14,"title1", "loc", "desc");
        int id = eventOrganizer.getEventList().get(0).getId();
        Assert.assertTrue(LocalDateTime.of(2019,12,31,14,14).equals(f.getEventEndtime(id)));
    }

    @Test
    public void creatingATodo(){
        Facade f = new Facade();
        Assert.assertTrue(TodoOrganizer.getTodoList().size()==0);
        f.createNewTodo("testTodo");
        Assert.assertTrue(TodoOrganizer.getTodoList().size() == 1);
    }

    @Test
    public void getATodoTitle(){
        Facade f = new Facade();
        f.createNewTodo("testTodo");
        int id = TodoOrganizer.getTodoList().get(0).getId();
        Assert.assertEquals("testTodo", f.getTodoTitle(id));
    }

    @Test
    public void getADoneTodoTitle(){
        Facade f = new Facade();
        TodoOrganizer.getDoneTodoList().add(new Todo("StringX", 100));
        Assert.assertEquals("StringX", f.getDoneTodoTitle(100));
    }

    @Test
    public void removingATodo(){
        Facade f = new Facade();
        Assert.assertEquals(0, TodoOrganizer.getTodoList().size());
        f.createNewTodo("TodoTitle");
        int id = TodoOrganizer.getTodoList().get(0).getId();
        Assert.assertEquals(1, TodoOrganizer.getTodoList().size());
        f.removeTodo(id);
        Assert.assertEquals(0, TodoOrganizer.getTodoList().size());

    }

    @Test
    public void removingADoneTodo() {
        Facade f = new Facade();
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
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        Assert.assertEquals(1, HabitOrganizer.getHabitList().size());
    }

    @Test
    public void removingAHabit(){
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
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        Assert.assertTrue(!f.habitExist("0"));
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertTrue(f.habitExist(""+id));
    }

    @Test
    public void getAHabtiTitle(){
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals("HabitTitle",f.getHabitTitle(id));
    }

    @Test
    public void getAHabtiColor(){
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(Color.BLACK.toString(),f.getHabitColor(id));
    }

    @Test
    public void getHabitStreak(){
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
        Facade f = new Facade();
        Assert.assertEquals(0, HabitOrganizer.getHabitList().size());
        f.createHabit("HabitTitle", Color.BLACK.toString());
        int id = HabitOrganizer.getHabitList().get(0).getId();
        Assert.assertEquals(Color.BLACK.toString(), f.getHabitColor(id));
        f.updateHabitColor(id, Color.CYAN.toString());
        Assert.assertEquals(Color.CYAN.toString(), f.getHabitColor(id));
    }


}
