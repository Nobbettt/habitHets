package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Facade {
    private EventOrganizer eventOrganizer = EventOrganizer.getInstance();
    private NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
    private TodoOrganizer todoOrganizer = TodoOrganizer.getInstant();
    private HabitOrganizer habitOrganizer = HabitOrganizer.getInstant();

    public double countOverlaps(LocalDateTime dateTime, int id) {
        return OverlapsCounter.countOverlaps(dateTime, id);
    }

    public String getNoteTextFromLdt(LocalDateTime ldt){
        return noteOrganizer.getNoteDate(ldt.toLocalDate()).getDescription();
    }

    public boolean noteOnDay(LocalDateTime ldt){
        return noteOrganizer.isNoteOnDay(ldt);
    }

    public void updateNote (String s, LocalDateTime ldt){
        if (noteOnDay(ldt)){
            noteOrganizer.getNoteDate(ldt.toLocalDate()).setDescription(s);
        } else {
            noteOrganizer.addNote(s, ldt.toLocalDate());
        }
    }

    public long getLength(int id){
        return (ChronoUnit.MINUTES.between(eventOrganizer.getEventOfId(id).getStartTime(), eventOrganizer.getEventOfId(id).getEndTime()));
    }

    public void createEvent(LocalDateTime ldt, int fH, int fM, int tH, int tM, String title, String location, String description){
        eventOrganizer.addEvent(ldt, fH, fM, tH, tM, title, location, description);
    }

    public void deleteEvent(int id){
        eventOrganizer.remove(id);
    }

    public void editEvent(int id, String title, String location, String desc, LocalDateTime from, LocalDateTime to){
        eventOrganizer.editEvent(id, title, location, desc, from, to);
    }

    public String getEventTitle(int id){
        return eventOrganizer.getEventOfId(id).getTitle();
    }

    public String getEventStarttimeString(int id){
        return eventOrganizer.getEventOfId(id).timeString();
    }

    public String getEventLocation(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getLocation();
    }

    public String getEventDesc(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getDescription();
    }

    public LocalDateTime getEventStarttime(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getStartTime();
    }

    public LocalDateTime getEventEndtime(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getEndTime();
    }

    public List<Integer> getAllIdsOfDay(LocalDateTime ltd){
        return eventOrganizer.getAllIDsOfDay(ltd);
    }

    public void createNewTodo(String title){
        todoOrganizer.addTodo(title);
    }

    public String getDoneTodoTitle(int id){
        return todoOrganizer.getDoneTodoOfId(id).getTitle();
    }

    public String getTodoTitle(int id){
        return todoOrganizer.getTodoOfId(id).getTitle();
    }

    public void moveBackTodo(int id){
        todoOrganizer.moveBackDoneTodo(id);
    }

    public void removeTodo(int id){
        todoOrganizer.remove(id);
    }

    public void removeDoneTodo(int id){
        todoOrganizer.doneTodoRemove(id);
    }

    public List<Integer> getTodoIds(){
        return todoOrganizer.getTodoIds();
    }

    public List<Integer> getDoneTodoIds(){
        return todoOrganizer.getDoneTodoIds();
    }

    public void createHabit(String title, String color){
        habitOrganizer.addHabit(title, color);
    }

    public void habitClicked(int id){
        habitOrganizer.getHabitFromId(id).onClickHabit();
    }

    public void removeHabit(int id){
        habitOrganizer.remove(id);
    }

    public boolean habitExist(String id){
        if (habitOrganizer.getHabitById(id) != null){
            return true;
        } else{
            return false;
        }
    }

    public String getHabitTitle(int id){
        return habitOrganizer.getHabitFromId(id).getTitle();
    }

    public int getBestStreak(int id){
        return habitOrganizer.getHabitFromId(id).getBestStreak();
    }

    public int getStreak(int id){
        return habitOrganizer.getHabitFromId(id).getStreak();
    }

    public boolean habitIsCheckedToday(int id){
        return habitOrganizer.getHabitFromId(id).isCheckedToday();
    }

    public String getHabitColor(int id){
        return habitOrganizer.getHabitFromId(id).getColor();
    }

    public void updateHabitTitle(int id, String title){
        habitOrganizer.getHabitFromId(id).setTitle(title);
    }

    public void updateHabitColor(int id, String color){
        habitOrganizer.getHabitFromId(id).setColor(color);
    }

    public List<Integer> getAllHabitIds(){ return habitOrganizer.getAllHabitIDs(); }

    public void addTodoListener(Listener l){
        todoOrganizer.addListener(l);
    }

    public void addHabitListener(Listener l){
        habitOrganizer.addListener(l);
    }
}
