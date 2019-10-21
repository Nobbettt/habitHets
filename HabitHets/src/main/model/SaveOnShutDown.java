package main.model;

import java.util.List;

public class SaveOnShutDown {
    public static void saveAll() {
        saveTodo();
        saveDoneTodo();
    }

    private static void saveTodo() {
        // id,title;id,title;
        List<Todo> todoList = TodoOrganizer.getInstant().getTodoList();
        String todoTxt = "";
        for(Todo todo : todoList) {
            todoTxt = todoTxt + todo.getId() + "," + todo.getTitle() + ";";
        }
        TxtDbCommunicator.writeFile("todo", todoTxt);
    }

    private static void saveDoneTodo() {
        // id,title;id,title;
        List<Todo> doneTodoList = TodoOrganizer.getInstant().getDoneTodoList();
        String todoTxt = "";
        for(Todo todo : doneTodoList) {
            todoTxt = todoTxt + todo.getId() + "," + todo.getTitle() + ";";
        }
        TxtDbCommunicator.writeFile("todoDone", todoTxt);
    }
}
