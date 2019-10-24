package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Stack;

public class Factory {
    private static int todoIdCount = 0;
    private static int habitIdCount = 0;
    private static int eventIdCount = 0;
    private static int noteIdCount = 0;

    /**
     * Setter to set todoIdCount
     * (Used by getDbTodo, getDbDoneTodo in TxtDbCommunicator)
     * @param todoIdCount
     */
    public static void setTodoIdCount(int todoIdCount) {
        Factory.todoIdCount = todoIdCount;
    }

    /**
     * Setter to set habitIdCount
     * (Used by getDbHabit in TxtDbCommunicator)
     * @param habitIdCount
     */
    public static void setHabitIdCount(int habitIdCount) {
        Factory.habitIdCount = habitIdCount;
    }

    /**
     * Setter to set eventIdCount
     * (Used by getDbEvent in TxtDbCommunicator)
     * @param eventIdCount
     */
    public static void setEventIdCount(int eventIdCount) {
        Factory.eventIdCount = eventIdCount;
    }

    /**
     * Setter to set noteIdCount
     * (Used by getDbNote in TxtDbCommunicator)
     * @param noteIdCount
     */
    public static void setNoteIdCount(int noteIdCount) {
        Factory.noteIdCount = noteIdCount;
    }

    /**
     * Creates a event object given the following parameters, (sets id automatically using local eventIdCount variable)
     * Increases eventIdCount variable
     * @param start
     * @param end
     * @param title
     * @param location
     * @param desc
     * @param color
     * @return
     */
    public static Event createAdvEvent(LocalDateTime start, LocalDateTime end, String title, String location, String desc, String color){
        eventIdCount++;
        Event createdAdvEvent = new Event(eventIdCount,start, end, title,location,desc,color);
        return createdAdvEvent;
    }

    /**
     * Creates a Note object given the following parameters, (sets id automatically using local noteIdCount variable)
     * Increases noteIdCount variable
     * @param description
     * @param day
     * @return
     */
    public static Note createNote(String description, LocalDate day){
        noteIdCount++;
        Note createdNote = new Note(noteIdCount, description, day);
        return createdNote;
    }

    /**
     * Creates a Todo object given the following parameters, (sets id automatically using local todoIdCount variable)
     * Increases todoIdCount variable
     * @param title
     * @return
     */
    public static Todo createTodo(String title){
        todoIdCount++;
        Todo createTodo = new Todo(title,todoIdCount);
        return createTodo;
    }

    /**
     * Creates a Habit object given the following parameters, (sets id automatically using local habitIdCount variable)
     * Increases habitIdCount variable
     * @param title
     * @param doneHabits
     * @param bestStreak
     * @param color
     * @param dateRecord
     * @return
     */
    public static Habit createHabit( String title, Stack doneHabits, int bestStreak, String color,LocalDate dateRecord){
        habitIdCount++;
        Habit createHabit = new Habit(habitIdCount,title,doneHabits,bestStreak,color, dateRecord);
        return createHabit;
    }
}
