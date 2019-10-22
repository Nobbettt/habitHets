package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Stack;

public class Factory {
    private static int todoIdCount = 0;
    private static int habitIdCount = 0;
    private static int eventIdCount = 0;
    private static int noteIdCount = 0;

    public static void setTodoIdCount(int todoIdCount) {
        Factory.todoIdCount = todoIdCount;
    }

    public static void setHabitIdCount(int habitIdCount) {
        Factory.habitIdCount = habitIdCount;
    }

    public static void setEventIdCount(int eventIdCount) {
        Factory.eventIdCount = eventIdCount;
    }

    public static void setNoteIdCount(int noteIdCount) {
        Factory.noteIdCount = noteIdCount;
    }

    public static Event createAdvEvent(LocalDateTime start, LocalDateTime end, String title, String location, String desc, String color){
        eventIdCount++;
        Event createdAdvEvent = new Event(eventIdCount,start, end, title,location,desc,color);
        return createdAdvEvent;
    }
    public static Note createNote(String description, LocalDate day){
        noteIdCount++;
        Note createdNote = new Note(noteIdCount, description, day);
        return createdNote;
    }

    public static Todo createTodo(String title){
        todoIdCount++;
        Todo createTodo = new Todo(title,todoIdCount);
        return createTodo;
    }

    public static Habit createHabit( String title, Stack doneHabits, int bestStreak, String color,LocalDate dateRecord){
        habitIdCount++;
        Habit createHabit = new Habit(habitIdCount,title,doneHabits,bestStreak,color, dateRecord);
        return createHabit;
    }
}
