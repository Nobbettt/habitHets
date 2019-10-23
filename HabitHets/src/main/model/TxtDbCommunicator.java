package main.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class TxtDbCommunicator {

    public static String readFile(String file) {
        String text = "";
        Scanner scanner;
        try {
            scanner = new Scanner( new File("HabitHets/src/main/model/db/"+file) );
            while (scanner.hasNextLine()) {
                text = scanner.useDelimiter("\\A").next();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void writeFile(String file, String newTxt) {
        try {
            Files.write( Paths.get("HabitHets/src/main/model/db/"+file), newTxt.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importDb() {
        getDbEvent();
        getDbNote();
        getDbHabit();
        int highestId = 0;
        highestId = getDbTodo(highestId);
        highestId = getDbTodoDone(highestId);
        Factory.setTodoIdCount(highestId);
    }

    // ALLT SKA VARA PRIVATE NEDANFÖR -->
    public static void getDbEvent() {
        int highestId = 0;

        String eventTxt = TxtDbCommunicator.readFile("event");
        if(!eventTxt.isEmpty()) {
            List<Event> tmpList = new ArrayList();
            String[] events = eventTxt.split("<end>");
            for (String eventString : events) {
                String[] attr = eventString.split("<//>");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime startTime = LocalDateTime.parse(attr[1], formatter);
                LocalDateTime endTime = LocalDateTime.parse(attr[2], formatter);
                Event event = new Event(Integer.parseInt(attr[0]), LocalDateTime.parse(startTime.toString(),formatter), LocalDateTime.parse(endTime.toString(), formatter), attr[3], attr[4], attr[5], attr[6]);
                tmpList.add(event);
                if(highestId < event.getId()) {
                    highestId = event.getId();
                }
            }
            Factory.setEventIdCount(highestId);
            EventOrganizer.getInstant().setEventList(tmpList);
        }
    }

    public static void getDbNote() {
        int highestId = 0;
        String noteTxt = TxtDbCommunicator.readFile("note");
        if(!noteTxt.isEmpty()) {
            List<Note> tmpList = new ArrayList<>();
            String[] notes = noteTxt.split("<end>");
            for (String noteString : notes) {
                String[] attr = noteString.split("<//>");
                Note note = new Note(Integer.parseInt(attr[0]), attr[1], LocalDate.parse(attr[2]));
                tmpList.add(note);
                if(highestId < note.getId()) {
                    highestId = note.getId();
                }
            }
            Factory.setNoteIdCount(highestId);
            NoteOrganizer.getInstance().setNotesList(tmpList);
        }
    }

    public static void getDbHabit() {
        int highestId = 0;
        String todoTxt = TxtDbCommunicator.readFile("habit");
        if(!todoTxt.isEmpty()) {
            List<Habit> tmpList = new ArrayList<>();
            String[] objects = todoTxt.split("<end>");
            for (String obj : objects) {
                String[] attr = obj.split("<//>");
                Stack<DoneHabit> doneHabits = formatDoneHabits(attr[5]);
                Habit h = new Habit(Integer.parseInt(attr[0]), attr[1], doneHabits, Integer.parseInt(attr[2]), attr[3], LocalDate.parse(attr[4]));
                tmpList.add(h);
                if(highestId < h.getId()) {
                    highestId = h.getId();
                }
            }
            Factory.setHabitIdCount(highestId);
            HabitOrganizer.getInstant().setHabitList(tmpList);
        }
    }

    public static Stack<DoneHabit> formatDoneHabits(String raw) {
        Stack<DoneHabit> doneHabits = new Stack<>();
        String[] doneArr = raw.split("=");
        for (String done : doneArr) {
            LocalDate tmpLd = LocalDate.parse(done);
            doneHabits.add(new DoneHabit(tmpLd));
        }
        return doneHabits;
    }

    public static int getDbTodo(int highestId) {
        String todoTxt = TxtDbCommunicator.readFile("todo");
        if (!todoTxt.isEmpty()) {
            List<Todo> tmpList = new ArrayList<>();
            String[] todos = todoTxt.split("<end>");
            for (String todo : todos) {
                String[] todoAttr = todo.split("<//>");
                Todo t = new Todo(todoAttr[1], Integer.parseInt(todoAttr[0]));
                tmpList.add(t);
                if(highestId < t.getId()) {
                    highestId = t.getId();
                }
            }
            TodoOrganizer.getInstant().setTodoList(tmpList);
        }
        return highestId;
    }

    public static int getDbTodoDone(int highestId) {
        String doneTodoTxt = TxtDbCommunicator.readFile("todoDone");
        if(!doneTodoTxt.isEmpty()) {
            List<Todo> tmpList = new ArrayList<>();
            String[] doneTodos = doneTodoTxt.split("<end>");
            for (String doneTodo : doneTodos) {
                String[] doneTodoAttr = doneTodo.split("<//>");
                Todo t = new Todo(doneTodoAttr[1], Integer.parseInt(doneTodoAttr[0]));
                tmpList.add(t);
                if(highestId < t.getId()) {
                    highestId = t.getId();
                }
            }
            TodoOrganizer.getInstant().setDoneTodoList(tmpList);
        }
        return highestId;
    }
}
