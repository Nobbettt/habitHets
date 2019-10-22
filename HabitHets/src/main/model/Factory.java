package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Stack;

public class Factory {
    //private static int id = 0;

    public static Event createAdvEvent(LocalDateTime start, LocalDateTime end, String title, String location, String desc, String color){
        System.out.println("------"+start.toString()+"----------");
        int idDb = getId();
        Event createdAdvEvent = new Event(idDb,start, end, title,location,desc,color);
        return createdAdvEvent;
    }
    public static Note createNote(String description, LocalDate day){
        int idDb = getId();
        Note createdNote = new Note(idDb, description, day);
        return createdNote;
    }

    public static Todo createTodo(String title){
        int idDb = getId();
        Todo createTodo = new Todo(title,idDb);
        return createTodo;
    }

    public static Habit createHabit( String title, Stack doneHabits, int bestStreak, String color,LocalDate dateRecord){
        int idDb = getId();
        Habit createHabit = new Habit(idDb,title,doneHabits,bestStreak,color, dateRecord);
        return createHabit;
    }

    private static int getId() {
        int idDb = 0;
        idDb = Integer.parseInt(TxtDbCommunicator.readFile("idCount"));
        idDb++;
        updateId(idDb);
        return idDb;
    }

    private static void updateId(int id) {
        String newTxt = Integer.toString(id);
        TxtDbCommunicator.writeFile("idCount", newTxt);
    }
}
