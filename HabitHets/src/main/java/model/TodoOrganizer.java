package model;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains methods and lists that affect a todoo.
 */

public class TodoOrganizer implements IOrganizer {
    private static List<Todo> todoList;
    private static List<Todo> doneTodoList;


    /**
     * The constructor of TodoOrganizer sets the list of todos and doneTodos.
     */
    TodoOrganizer() {
        if (todoList == null) {
            todoList = new ArrayList<>();
        }
        if (doneTodoList == null) {
            doneTodoList = new ArrayList<>();
        }
    }

    static void setTodoList(List<Todo> todoList) {
        TodoOrganizer.todoList = todoList;
    }

    static void setDoneTodoList(List<Todo> doneTodoList) {
        TodoOrganizer.doneTodoList = doneTodoList;
    }


    /**
     * This method creates a new toddoo. You have to enter the todoo's title, the todoo get it's id from the factory class
     * where the todoo object is made.
     */
    static void addTodo(String title) {
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
                notifyListener();
                return;
            }
        }
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
    static void doneTodoRemove(int id){ // to remove a todoo when you have completed the task
        for (Todo todo: todoList){
            if(todo.getId() == id){
                doneTodoList.add(todo);
                doneTodoList();
                todoList.remove(todo);
                notifyListener();
                return;
            }
        }
    }

    static List<Todo> getTodoList() {
        return todoList;
    }

    static List<Todo> getDoneTodoList() {
        return doneTodoList;
    }

    static List<Integer> getTodoIds(){
        List<Integer> ids = new ArrayList<>();
        for (Todo todo : getTodoList()){
            ids.add(todo.getId());
        }
        return ids;
    }

    static List<Integer> getDoneTodoIds(){
        List<Integer> ids = new ArrayList<>();
        for (Todo todo : getDoneTodoList()){
            ids.add(todo.getId());
        }
        return ids;
    }



    static Todo getTodoOfId(int id){
        for (Todo todo : getTodoList()){
            if (todo.getId() == id){
                return todo;
            }
        }
        return null;
    }

    static Todo getDoneTodoOfId(int id){
        for (Todo todo : getDoneTodoList()){
            if (todo.getId() == id){
                return todo;
            }
        }
        return null;
    }

    /**
     * When you have completed a todoo, it goes from todolist to doneTodolist. This method
     * checks that the doneTodolist only have the five most recent completed todos.
     */
    private static void doneTodoList(){
        if (doneTodoList.size()>5){
            int limit = doneTodoList.size()-5;
            for (int a=0; a<limit ;a++){
                doneTodoList.remove(0);
                notifyListener();
            }
        }
    }

    private static List<Listener> listeners = new ArrayList<>();


    static void addListener(Listener l){
        listeners.add(l);

    }

    private static void notifyListener(){
        for (Listener l : listeners)
            l.actOnUpdate();
    }

    static void moveBackDoneTodo(int id) {
        for (int i = 0; i < doneTodoList.size(); i++) {
            if (doneTodoList.get(i).getId() == id) {
                addTodo(getDoneTodoList().get(i).getTitle());
                doneTodoList.remove(i);
                notifyListener();
                return;
            }
        }
    }
}

