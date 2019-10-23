package main.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Stack;

public class SaveOnShutDown {
    public static void saveAll() {
        saveTodo();
        saveDoneTodo();
        saveHabit();
        saveNote();
        saveEvent();
    }

    private static void saveTodo() {
        // id,title;id,title;
        List<Todo> todoList = TodoOrganizer.getInstant().getTodoList();
        StringBuilder txt = new StringBuilder();
        for(Todo todo : todoList) {
            txt.append(todo.getId()).append("<//>");
            txt.append(todo.getTitle()).append("<end>");
        }
        TxtDbCommunicator.writeFile("todo", txt.toString());
    }

    private static void saveDoneTodo() {
        // id,title;id,title;
        List<Todo> doneTodoList = TodoOrganizer.getInstant().getDoneTodoList();
        StringBuilder txt = new StringBuilder();
        for(Todo todo : doneTodoList) {
            txt.append(todo.getId()).append("<//>");
            txt.append(todo.getTitle()).append("<end>");
        }
        TxtDbCommunicator.writeFile("todoDone", txt.toString());
    }

    private static void saveHabit() {
        // id,title,bestStreak,Color,daterecord,ldt=ldt=ldt=ldt;id,title,bestStreak,Color,daterecord,ldt=ldt=ldt=ldt;
        List<Habit> habitList = HabitOrganizer.getInstant().getHabitList();
        StringBuilder txt = new StringBuilder();
        for(Habit habit : habitList) {
            txt.append(habit.getId()).append("<//>");
            txt.append(habit.getTitle()).append("<//>");
            txt.append(habit.getBestStreak()).append("<//>");
            txt.append(habit.getColor()).append("<//>");
            txt.append(habit.getDateRecord().toString()).append("<//>");
            txt.append(formatDoneHabits(habit.getDoneHabits())).append("<end>");
        }
        TxtDbCommunicator.writeFile("habit", txt.toString());
    }
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

    private static void saveNote() {
        // id,title,2019-10-21;
        List<Note> noteList = NoteOrganizer.getInstance().getNotes();
        StringBuilder txt = new StringBuilder();
        for(Note note : noteList) {
            if(note.getDescription() != null && !note.getDescription().isEmpty()) {
                txt.append(note.getId()).append("<//>");
                txt.append(note.getDescription()).append("<//>");
                txt.append(LocalDate.parse(note.getDay().toString())).append("<end>");
            }
        }
        TxtDbCommunicator.writeFile("note", txt.toString());
    }

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
                txt.append(event.getTitle()).append("<//>");
                txt.append(event.getLocation()).append("<//>");
                txt.append(event.getDescription()).append("<//>");
                txt.append(event.getColor()).append("<end>");
            }
        }
        TxtDbCommunicator.writeFile("event", txt.toString());
    }
}
