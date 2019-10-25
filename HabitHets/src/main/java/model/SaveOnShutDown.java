package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Stack;

/**
 * Writes every object to txt files
 */
public class SaveOnShutDown {
    private static final String[] conflicts = {"<end>", "<//>"};

    /**
     * Function creates a new txt file (overwrites a existing file if name already taken)
     * Sets the new files content to newTxt string parameter
     * @param file
     * @param newTxt
     */
    private static void writeFile(String file, String newTxt) {
        Path currentRelativePath = Paths.get("");
        String relativePath = currentRelativePath.toAbsolutePath().toString();

        try {
            Files.write( Paths.get(relativePath + "/src/main/resources/db/"+file), newTxt.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Access function to save all the objects (Todos, DoneTodos, Habits, Notes, Events) on shutdown.
     */
    public static void saveAll() {
        saveTodo();
        saveDoneTodo();
        saveHabit();
        saveNote();
        saveEvent();
    }

    /**
     * Saves all the local Todos from the TodoOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the todo.txt which is found in the db folder
     */
    private static void saveTodo() {
        TodoOrganizer todoOrganizer = new TodoOrganizer();
        List<Todo> todoList = todoOrganizer.getTodoList();
        StringBuilder txt = new StringBuilder();
        for(Todo todo : todoList) {
            txt.append(todo.getId()).append("<//>");
            String cleanTitle = removeConflicts(todo.getTitle());
            txt.append(cleanTitle).append("<end>");
        }
        writeFile("todo", txt.toString());
    }

    /**
     * Saves all the local DoneTodos from the TodoOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the todoDone.txt which is found in the db folder
     */
    private static void saveDoneTodo() {
        TodoOrganizer todoOrganizer = new TodoOrganizer();
        List<Todo> doneTodoList = todoOrganizer.getDoneTodoList();
        StringBuilder txt = new StringBuilder();
        for(Todo todo : doneTodoList) {
            txt.append(todo.getId()).append("<//>");
            String cleanTitle = removeConflicts(todo.getTitle());
            txt.append(cleanTitle).append("<end>");
        }
        writeFile("todoDone", txt.toString());
    }

    /**
     * Saves all the local Habits from the HabitOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the habit.txt which is found in the db folder
     */
    private static void saveHabit() {
        HabitOrganizer habitOrganizer = new HabitOrganizer();
        List<Habit> habitList = habitOrganizer.getHabitList();
        StringBuilder txt = new StringBuilder();
        for(Habit habit : habitList) {
            txt.append(habit.getId()).append("<//>");
            String cleanTitle = removeConflicts(habit.getTitle());
            txt.append(cleanTitle).append("<//>");
            txt.append(habit.getBestStreak()).append("<//>");
            String cleanColor = removeConflicts(habit.getColor());
            txt.append(cleanColor).append("<//>");
            txt.append(habit.getDateRecord().toString()).append("<//>");
            txt.append(formatDoneHabits(habit.getDoneHabits())).append("<end>");
        }
        writeFile("habit", txt.toString());
    }

    /**
     * Helper method to saveHabit function to format stack of DoneHabit to a string
     */
    private static String formatDoneHabits(Stack<DoneHabit> stack) {
        StringBuilder txt = new StringBuilder();
        for (DoneHabit doneHabit : stack) {
            txt.append(doneHabit.getDate().toString()).append("=");
        }
        if(stack.isEmpty()) {
            txt.append("=");
        }
        return txt.toString();
    }

    /**
     * Saves all the local Notes from the NoteOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the note.txt which is found in the db folder
     */
    private static void saveNote() {
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        List<Note> noteList = noteOrganizer.getNotes();
        StringBuilder txt = new StringBuilder();
        for(Note note : noteList) {
            if(note.getDescription() != null && !note.getDescription().isEmpty()) {
                txt.append(note.getId()).append("<//>");
                String cleanDesc = removeConflicts(note.getDescription());
                txt.append(cleanDesc).append("<//>");
                txt.append(LocalDate.parse(note.getDay().toString())).append("<end>");
            }
        }
        writeFile("note", txt.toString());
    }

    /**
     * Saves all the local Event from the EventOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the event.txt which is found in the db folder
     */
    private static void saveEvent() {
        EventOrganizer eventOrganizer = new EventOrganizer();
        List<Event> eventList = eventOrganizer.getEventList();
        StringBuilder txt = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        for(Event event : eventList) {
            if (event.getDescription() != null) {
                txt.append(event.getId()).append("<//>");
                String startTime = formatter.format(event.getStartTime().withSecond(02));
                txt.append(startTime).append("<//>");
                String endTime = formatter.format(event.getEndTime().withSecond(02));
                txt.append(endTime).append("<//>");
                String cleanTitle = removeConflicts(event.getTitle());
                txt.append(cleanTitle).append("<//>");
                String cleanLocation = removeConflicts(event.getLocation());
                txt.append(cleanLocation).append("<//>");
                String cleanDesc = removeConflicts(event.getDescription());
                txt.append(cleanDesc).append("<//>");
                String cleanColor = removeConflicts(event.getColor());
                txt.append(cleanColor).append("<end>");
            }
        }
        writeFile("event", txt.toString());
    }

    /**
     * User to reformat string if conflict is found before save.
     * Checks for permutations / combinations of strings that is in the conflicts array
     * @param str
     * @return
     */
    static String removeConflicts(String str) {
        if (str != null && !str.isEmpty()) {
            for (String conflict : conflicts) {
                str = checkString(str, conflict);
            }
        }
        return str;
    }

    /**
     * Looks for conflicts and reformats them by adding a "_" after the first character in the conflicted string.
     * ex <end> turns into <_end> which no longer causes any future conflicts on read.
     * @param s
     * @param conflictStr
     * @return
     */
    private static String checkString(String s, String conflictStr) {
        StringBuilder strB = new StringBuilder();
        char[] conflictArr = conflictStr.toCharArray();
        char[] toCheck = s.toCharArray();
        int cc = 0;
        for (int i = 0; i < toCheck.length; i++) {
            StringBuilder tmpSB = new StringBuilder();
            tmpSB.append(toCheck[i]);
            while (toCheck[i] == conflictArr[cc] && i+1 < toCheck.length && cc+1 < conflictArr.length) {
                cc++;
                i++;
                tmpSB.append(toCheck[i]);
            }
            if(cc+1 == conflictArr.length) {
                strB.append(conflictArr[0]).append("_").append(conflictStr.substring(1,conflictArr.length));
            } else {
                strB.append(tmpSB.toString());
            }
            cc = 0;
        }
        return strB.toString();
    }
}