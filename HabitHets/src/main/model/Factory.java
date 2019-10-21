package main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Stack;

public class Factory {
    private static int id = 0;

    public static Event createBasicEvent(LocalDateTime start, LocalDateTime end, String title){
        Event createdBasicEvent = new Event(id,start, end, title);
        id++;
        return createdBasicEvent;
    }

    public static Event createAdvEvent(LocalDateTime start, LocalDateTime end, String title, String location, String desc, String color){
        Event createdAdvEvent = new Event(id,start, end, title,location,desc,color);
        id++;
        return createdAdvEvent;
    }
    public static Note createNote(String description, LocalDateTime day){
        Note createdNote = new Note(id, description, day);
        id++;
        return createdNote;
    }

    public static Todo createTodo(String title){
        int idDb = getId();
        Todo createTodo = new Todo(title,idDb);
        return createTodo;
    }

    public static Habit createHabit( String title, Stack doneHabits, int bestStreak, String color,LocalDate dateRecord){
        Habit createHabit = new Habit(id,title,doneHabits,bestStreak,color, dateRecord);
        id++;
        return createHabit;
    }

    private static int getId() {
        int idDb = 0;
        Scanner scanner;

        try {
            scanner = new Scanner( new File("C:\\Users\\norbe\\Documents\\habitHets\\HabitHets\\src\\main\\model\\idCount") );
            while (scanner.hasNextLine()) {
                idDb = Integer.parseInt(scanner.useDelimiter("\\A").next());
                idDb++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        updateId(idDb);
        return idDb;
    }

    private static void updateId(int id) {
        try {
            String newTxt = Integer.toString(id);
            Files.write( Paths.get("C:\\Users\\norbe\\Documents\\habitHets\\HabitHets\\src\\main\\model\\idCount"), newTxt.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
