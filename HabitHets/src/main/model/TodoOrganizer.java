package main.model;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains methods and lists that affect a todoo.
 */

public class TodoOrganizer implements IHandler {
    public static TodoOrganizer instant;
    private static List<Todo> todoList;
    private static List<Todo> doneTodoList;


    /**
     * The constructor of TodoOrganizer sets the list of todos and doneTodos.
     */
    public TodoOrganizer() {
        todoList = new ArrayList<>();
        doneTodoList = new ArrayList<>();
        getDb();
    }

    /**
     * Control if object is created , so that there may only be one at a time.
     * If there is an instant, another one will not be created.
     * If there is not, an instant will be created.
     */

    public static TodoOrganizer getInstant() {
        if (instant == null) {
            instant = new TodoOrganizer();
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
        Todo todo = Factory.createTodo("testTodo");
        todoList.add(todo);
        notifyListener();
    }

    public void addTodo(String title) {
        Todo todo = Factory.createTodo(title);
        todoList.add(todo);
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
                int id = doneTodoList.get(0).getId();
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

    public void moveBackDoneTodo(int id) {
        for (int i = 0; i < doneTodoList.size(); i++) {
            if (doneTodoList.get(i).getId() == id) {
                doneTodoList.remove(i);
                notifyListener();
                return;
            }
        }
    }

    private void getDb() {
        String todoTxt = TxtDbCommunicator.readFile("todo");
        if(!todoTxt.isEmpty()) {
            String[] todos = todoTxt.split(";");
            for (String todo : todos) {
                String[] todoAttr = todo.split(",");
                todoList.add(new Todo(todoAttr[1], Integer.parseInt(todoAttr[0])));
            }
        }

        String doneTodoTxt = TxtDbCommunicator.readFile("todoDone");
        if(!doneTodoTxt.isEmpty()) {
            String[] doneTodos = doneTodoTxt.split(";");
            for (String doneTodo : doneTodos) {
                String[] doneTodoAttr = doneTodo.split(",");
                doneTodoList.add(new Todo(doneTodoAttr[1], Integer.parseInt(doneTodoAttr[0])));
            }
        }
    }
}

