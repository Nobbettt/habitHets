package main.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Stack;

public class SaveOnShutDown {
    private static final String[] conflicts = {"<end>", "<//>"};
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
        // id,title;id,title;
        List<Todo> todoList = TodoOrganizer.getInstant().getTodoList();
        StringBuilder txt = new StringBuilder();
        for(Todo todo : todoList) {
            txt.append(todo.getId()).append("<//>");
            String cleanTitle = removeConflicts(todo.getTitle());
            txt.append(cleanTitle).append("<end>");
        }
        TxtDbCommunicator.writeFile("todo", txt.toString());
    }

    /**
     * Saves all the local DoneTodos from the TodoOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the todoDone.txt which is found in the db folder
     */
    private static void saveDoneTodo() {
        // id,title;id,title;
        List<Todo> doneTodoList = TodoOrganizer.getInstant().getDoneTodoList();
        StringBuilder txt = new StringBuilder();
        for(Todo todo : doneTodoList) {
            txt.append(todo.getId()).append("<//>");
            String cleanTitle = removeConflicts(todo.getTitle());
            txt.append(cleanTitle).append("<end>");
        }
        TxtDbCommunicator.writeFile("todoDone", txt.toString());
    }

    /**
     * Saves all the local Habits from the HabitOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the habit.txt which is found in the db folder
     */
    private static void saveHabit() {
        // id,title,bestStreak,Color,daterecord,ldt=ldt=ldt=ldt;id,title,bestStreak,Color,daterecord,ldt=ldt=ldt=ldt;
        List<Habit> habitList = HabitOrganizer.getInstant().getHabitList();
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
        TxtDbCommunicator.writeFile("habit", txt.toString());
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
        // id,title,2019-10-21;
        List<Note> noteList = NoteOrganizer.getInstance().getNotes();
        StringBuilder txt = new StringBuilder();
        for(Note note : noteList) {
            if(note.getDescription() != null && !note.getDescription().isEmpty()) {
                txt.append(note.getId()).append("<//>");
                String cleanDesc = removeConflicts(note.getDescription());
                txt.append(cleanDesc).append("<//>");
                txt.append(LocalDate.parse(note.getDay().toString())).append("<end>");
            }
        }
        TxtDbCommunicator.writeFile("note", txt.toString());
    }

    /**
     * Saves all the local Event from the EventOrganizer
     * Goes through all the objects and converts their attributes to string and adds them to a string builder separating the attributes by <//> tag an objects by <end> tag
     * When all objects all converted to a single string it writes it to the event.txt which is found in the db folder
     */
    private static void saveEvent() {
        // id,2019-10-22T05:00:25,2019-10-22T05:00:25,Title thing,Location thing,Description thing,#47bcad
        List<Event> eventList = EventOrganizer.getInstant().getEventList();
        StringBuilder txt = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        for(Event event : eventList) {
            if (event.getDescription() != null) {
                txt.append(event.getId()).append("<//>");
                String startTime = formatter.format(event.getStartTime());
                txt.append(startTime).append("<//>");
                String endTime = formatter.format(event.getEndTime());
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
        TxtDbCommunicator.writeFile("event", txt.toString());
    }

    /**
     * User to reformat string if conflict is found before save.
     * Checks for permutations / combinations of strings that is in the conflicts array
     * @param str
     * @return
     */
    public static String removeConflicts(String str) {
        for (String conflict : conflicts) {
            str = checkString(str, conflict);
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