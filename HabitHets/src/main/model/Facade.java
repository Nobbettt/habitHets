package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Facade {
    private Calender calender = Calender.getInstant();
    private EventOrganizer eventOrganizer = EventOrganizer.getInstant();
    private NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
    private TodoOrganizer todoOrganizer = TodoOrganizer.getInstant();
    private HabitOrganizer habitOrganizer = HabitOrganizer.getInstant();

    public List<Day> getWeekFromDate(LocalDateTime localDateTime) {
        return calender.getWeekFromLDT(localDateTime);
    }

    public List<Day> getDayFromDate(LocalDateTime localDateTime) {
        List<Day> singleDayList = new ArrayList();
        singleDayList.add(calender.getDayFromLDT(localDateTime));
        return singleDayList;
    }

    public Month getMonth (LocalDateTime ldt){
        return calender.getMonth(ldt);
    }

    private Event getEventFromID(int id){
        return eventOrganizer.getEventOfId(id);
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

    public long getLength(int id){
        return (ChronoUnit.MINUTES.between(eventOrganizer.getEventOfId(id).getStartTime(), eventOrganizer.getEventOfId(id).getEndTime()));
    }

    public double calculateWidth(LocalDateTime dateTime, int id) {
        double i = 0;
        List<Event> overlaps = new ArrayList<>();
        List<Event> overlaps2 = new ArrayList<>();
        List<Event> events = EventOrganizer.getInstant().getEventsOfDay(dateTime);
        Interval interval = new Interval(getEventStarttime(id), true, getEventEndtime(id), true);
        for (Event tmpEvent : events) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                i++;
                if (!overlaps.contains(tmpEvent) && !tmpEvent.equals(getEventFromID(id))) {
                    overlaps.add(tmpEvent);
                }
            }
        }
        for (Event event1 : overlaps) {
            Interval interval1 = new Interval(event1.getStartTime(), true, event1.getEndTime(), true);
            for (Event event2 : events) {
                Interval interval2 = new Interval(event2.getStartTime(), true, event2.getEndTime(), true);
                if (interval1.overlaps(interval2) && event2 != event1 && event2 != getEventFromID(id)) {
                    i++;
                    if (!overlaps2.contains(event2) && !event2.equals(event1)) {
                        overlaps2.add(event2);
                    }
                }
            }
        }
        events.add(getEventFromID(id));
        return i;
    }

    public int getWeekFromLdt(LocalDateTime ldt){
        LocalDate date = ldt.toLocalDate();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return date.get(woy);
    }

    public List<LocalDateTime> getLdtMonthFromDate(LocalDateTime localDateTime) {
        List<LocalDateTime> list = new ArrayList<>();
        for (Day day : calender.getMonth(localDateTime).getDays()){
            list.add(day.getLdt());
        }
        return list;
    }
    
    public List<LocalDateTime> getLdtWeekFromDate(LocalDateTime localDateTime){
        List<LocalDateTime> list = new ArrayList<>();
        for (Day day : calender.getWeekFromLDT(localDateTime)){
            list.add(day.getLdt());
        }
        return list;
    }

    public List<LocalDateTime> getLdtYearFromDate(LocalDateTime localDateTime){
        List<LocalDateTime> list = new ArrayList<>();
        for (Month month : calender.getYearFromLDT(localDateTime)){
            list.add(month.getLocalDateTime());
        }
        return list;
    }

    public String getMonthString(LocalDateTime ltd){
        Month m = calender.getMonth(ltd);
        return m.getString();
    }

    public String getNoteTextFromLdt(LocalDateTime ldt){
        return noteOrganizer.getNoteDate(ldt.toLocalDate()).getDescription();
    }

    public boolean noteOnDay(LocalDateTime ldt){
        return noteOrganizer.getNoteDate(ldt.toLocalDate()) != null;
    }

    public void updateNote (String s, LocalDateTime ldt){
        if (noteOnDay(ldt)){
            noteOrganizer.getNoteDate(ldt.toLocalDate()).setDescription(s);
        } else {
            noteOrganizer.addNote(s, ldt.toLocalDate());
        }
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

}