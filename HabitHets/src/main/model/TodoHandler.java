package main.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains methods and lists that affect a todoo.
 */

public class TodoHandler implements IHandler {


    public static TodoHandler instant;
    private static List<Todo> todoList;
    private static List<Todo> doneTodoList;


    /**
     * The constructor of TodoHandler sets the list of todos and doneTodos.
     */


    public TodoHandler() {
        this.todoList = new ArrayList<>();
        this.doneTodoList = new ArrayList<>();
    }

    /**
     * Control if object is created , so that there may only be one at a time.
     * If there is an instant, another one will not be created.
     * If there is not, an instant will be created.
     */

    public static TodoHandler getInstant() {
        if (instant == null) {
            instant = new TodoHandler();
            return instant;

        } else {
            return instant;
        }
    }

    /**
     * This method creates a new toddoo. You have to enter the todoo's title, the todoo get it's id from the factory class
     * where the todoo object is made.
     */

    @Override
    public void add() {
        todoList.add(Factory.createTodo("testTodo"));
        notifyListener();

    }

    public void addTodo(String title) {
        todoList.add(Factory.createTodo(title));
        notifyListener();

    }

    /**
     *This method delete a todoo that hasn't been completed, for example
     * if you change your mind and don't want to do that todoo anymore.
     * The remove-method checks that the id of the todoo you want to delete exists in the todolist.
     * If the id exist, the todoo deletes from the list. If not, you get an error messages
     * that the id does not exist.
     * @param id
     */

    @Override
    public void remove(int id) {
        for (Todo todo: todoList){
            if(todo.getId() == id){
                todoList.remove(todo);
                return;
            }
        } System.out.println("The ID " +id + " does not exist.");
    }

    /**
     * This method is used when you have completed a todoo. It takes in the id of the
     * todoo that has been completed. It checks that id is connected to an existing todoo.
     * If it is, then the todoo is added to the list of completed todos. The method calls on
     * another method to check that the donetodo list is not to big. The last step is to remove
     * the todoo from the list of existing todos.
     * If the id does not exist you get an error message.     *
     * @param id
     */

    public void doneTodoRemove(int id){ // to remove a todoo when you have completed the task
        for (Todo todo: todoList){
            if(todo.getId() == id){
                doneTodoList.add(todo);
                doneTodoList();
                todoList.remove(todo);
                notifyListener();
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

    /**
     * When you have completed a todoo, it goes from todolist to doneTodolist. This method
     * checks that the doneTodolist only have the five most recent completed todos.
     */

    private void doneTodoList(){
        if (doneTodoList.size()>5){
            int limit = doneTodoList.size()-5;
            for (int a=0; a<limit ;a++){
                doneTodoList.remove(0);
                notifyListener();
            }
        }
    }


    private List<Listener> listeners = new ArrayList<>();


    public void addListener(Listener l){
        listeners.add(l);

    }

    private void notifyListener(){
        for (Listener l : listeners)
            l.actOnUpdate();
    }
}
