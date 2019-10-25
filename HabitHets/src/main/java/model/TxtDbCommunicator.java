package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class reads content from txt files and creates objects from them
 */
public class TxtDbCommunicator {

    /**
     * Function reads txt file given a filename from the model/db folder
     * Returns the whole content of the file in form of a sting
     * @param file
     * @return
     */
    private static String readFile(String file) {

        Path currentRelativePath = Paths.get("");
        String relativePath = currentRelativePath.toAbsolutePath().toString();

        String text = "";
        Scanner scanner;
        try {
            scanner = new Scanner( new File(relativePath + "/src/main/resources/db/"+file) );
            while (scanner.hasNextLine()) {
                text = scanner.useDelimiter("\\A").next();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }


    /**
     * This function is the access point to this static class.
     * On call this function initiates all the getDb(...) functions
     */
    public static void importDb() {
        getDbEvent();
        getDbNote();
        getDbHabit();
        int highestId = 0;
        highestId = getDbTodo(highestId);
        highestId = getDbTodoDone(highestId);
        Factory.setTodoIdCount(highestId);
    }

    /**
     * This function reads the event.txt file -->
     * Divides the string up to separate objects -->
     * Pareses its string attribute to the correct format -->
     * Creates local Events and adds them to the EventOrganizers List of Events.
     * Keeps track of the highest ID attribute and updates the Factory classes local eventIdCount variable.
     */
    private static void getDbEvent() {
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
            EventOrganizer.getInstance().setEventList(tmpList);
        }
    }

    /**
     * This function reads the note.txt file -->
     * Divides the string up to separate objects -->
     * Pareses its string attribute to the correct format -->
     * Creates local Notes and adds them to the NoteOrganizers List of Notes.
     * Keeps track of the highest ID attribute and updates the Factory classes local noteIdCount variable.
     */
    private static void getDbNote() {
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

    /**
     * This function reads the habit.txt file -->
     * Divides the string up to separate objects -->
     * Pareses its string attribute to the correct format -->
     * Creates local Habit and adds them to the HabitOrganizers List of Habit.
     * Keeps track of the highest ID attribute and updates the Factory classes local habitIdCount variable.
     */
    private static void getDbHabit() {
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

    /**
     * Is used by getDbHabit to parse a string (array) of LocalDateTimes to a Stack of DoneHabits
     * @param raw
     * @return
     */
    private static Stack<DoneHabit> formatDoneHabits(String raw) {
        Stack<DoneHabit> doneHabits = new Stack<>();
        String[] doneArr = raw.split("=");
        for (String done : doneArr) {
            LocalDate tmpLd = LocalDate.parse(done);
            doneHabits.add(new DoneHabit(tmpLd));
        }
        return doneHabits;
    }

    /**
     * This function reads the todo.txt file -->
     * Divides the string up to separate objects -->
     * Pareses its string attribute to the correct format -->
     * Creates local Todos and adds them to the TodoOrganizers List of Todo.
     * Keeps track of the highest ID attribute and updates the Factory classes local todoIdCount variable.
     */
    private static int getDbTodo(int highestId) {
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

    /**
     * This function reads the todoDone.txt file -->
     * Divides the string up to separate objects -->
     * Pareses its string attribute to the correct format -->
     * Creates local Todos and adds them to the TodoOrganizers List of todoDone.
     * Keeps track of the highest ID attribute and updates the Factory classes local todoIdCount variable.
     */
    private static int getDbTodoDone(int highestId) {
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
