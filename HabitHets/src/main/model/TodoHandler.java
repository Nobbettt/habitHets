package main.model;

import java.util.ArrayList;
import java.util.List;

public class TodoHandler implements IHandler {


    public static TodoHandler instant;
    private static List<Todo> todoList;
    private static List<Todo> doneTodoList;

    public TodoHandler() {
        this.todoList = new ArrayList<>();
        this.doneTodoList = new ArrayList<>();
    }

    public static TodoHandler getInstant() {
        if (instant == null) {
            instant = new TodoHandler();
            return instant;

        } else {
            return instant;
        }
    }

    @Override
    public void add() {
        todoList.add(Factory.createTodo("testTodo"));
    }


    @Override
    public void remove(int id) {
        for (Todo todo: todoList){
            if(todo.getId() == id){
                doneTodoList.add(todo);
                doneTodoList();
                todoList.remove(todo);
                return;
            }
        } System.out.println("The ID " +id + " does not exist.");
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public List<Todo> getDoneTodoList() {
        return doneTodoList;
    }

    private void doneTodoList(){
        if (doneTodoList.size()>5){
            int limit = doneTodoList.size()-5;
            for (int a=0; a<limit ;a++){
                doneTodoList.remove(0);
            }
        }
    }


}
