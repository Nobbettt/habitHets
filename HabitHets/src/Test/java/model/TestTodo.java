package model;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class TestTodo {

    static TodoOrganizer todoOrganizer = new TodoOrganizer();
    static List<Todo> todos;
    static List<Todo> doneTodos;

    @BeforeClass
    public static void setUp() {
        TxtDbCommunicator.importDb();
        todos = copyTodoList();
        doneTodos = copyDoneTodoList();
        todoOrganizer.setTodoList(new ArrayList<>());
        todoOrganizer.setDoneTodoList(new ArrayList<>());
    }

    @Before
    public void clear(){
        todoOrganizer.getTodoList().clear();
        todoOrganizer.getDoneTodoList().clear();
    }

    @AfterClass
    public static void resetClass(){
        TodoOrganizer.setTodoList(todos);
        TodoOrganizer.setTodoList(doneTodos);
    }

    private static List<Todo> copyTodoList(){
        List<Todo> tmpList = new ArrayList<>();
        for (Todo todo : todoOrganizer.getTodoList()){
            tmpList.add(todo);
        }
        return tmpList;
    }

    private static List<Todo> copyDoneTodoList(){
        List<Todo> tmpList = new ArrayList<>();
        for (Todo todo : todoOrganizer.getDoneTodoList()){
            tmpList.add(todo);
        }
        return tmpList;
    }

    /**
     * This Test checks that the add-method works the way it is suppose to do.
     */

    @Test
    public void testTodoAdd(){
        todoOrganizer.addTodo("Undvika todo 2");
        todoOrganizer.addTodo("Säga hej till Tina");
        Assert.assertEquals(2, todoOrganizer.getTodoList().size());
        Assert.assertEquals("Undvika todo 2", todoOrganizer.getTodoList().get(0).getTitle());

    }


    /**
     * This Test checks that you can change the title of the todoo.
     */

    @Test
    public void testTodoUpdateSubject(){
        todoOrganizer.addTodo("Undvika todo 2");
        todoOrganizer.addTodo("Säga hej till Tina");
        Assert.assertEquals("Undvika todo 2", todoOrganizer.getTodoList().get(0).getTitle() );
        todoOrganizer.getTodoList().get(0).setTitle("Städa");
        Assert.assertEquals("Städa", todoOrganizer.getTodoList().get(0).getTitle() );
        Assert.assertEquals("Säga hej till Tina", todoOrganizer.getTodoList().get(1).getTitle() );
    }

    /**
     * This Test checks that when you complete a todoo that the todoo go from the todolist to donetodolist.
     */

    @Test
    public void testTodoRemove() {
        todoOrganizer.addTodo("Undvika todo 2");
        todoOrganizer.addTodo("Säga hej till Tina");
        Assert.assertEquals(2, todoOrganizer.getTodoList().size());
        todoOrganizer.getTodoList().get(0).setTitle("Städa");
        Assert.assertEquals("Städa", todoOrganizer.getTodoList().get(0).getTitle());
        int id = todoOrganizer.getTodoList().get(0).getId();
        todoOrganizer.doneTodoRemove(id);
        Assert.assertEquals(1, todoOrganizer.getTodoList().size());
        Assert.assertEquals(1, todoOrganizer.getDoneTodoList().size());
        Assert.assertEquals("Säga hej till Tina", todoOrganizer.getTodoList().get(0).getTitle() );
        Assert.assertEquals("Städa", todoOrganizer.getDoneTodoList().get(0).getTitle() );
    }

    /**
     * This Test checks that the length of the donetodolist never goes over the limit of 5.
     */

    @Test
    public void testLengthOfDoneTodoList(){
        for (int i = 0; i<7; i++){
            todoOrganizer.addTodo("test");
        }
        for (int i = 0; i<7; i++) {
            todoOrganizer.doneTodoRemove(todoOrganizer.getTodoList().get(0).getId());
        }
        Assert.assertEquals(0, todoOrganizer.getTodoList().size());
        Assert.assertEquals(5, todoOrganizer.getDoneTodoList().size());
    }

    /**
     * This Test checks that you remove a todoo without complete it.
     */


    @Test
    public void deleteTodo(){
        todoOrganizer.addTodo("Undvika todo 2");
        todoOrganizer.addTodo("Säga hej till Tina");
        Assert.assertEquals(2, todoOrganizer.getTodoList().size());
        todoOrganizer.remove(todoOrganizer.getTodoList().get(0).getId());
        Assert.assertEquals(1, todoOrganizer.getTodoList().size());
    }

    @Test
    public void redoATodo(){
        todoOrganizer.addTodo("Hej");
        Assert.assertEquals(1, todoOrganizer.getTodoList().size());
        int id = todoOrganizer.getTodoList().get(0).getId();
        todoOrganizer.doneTodoRemove(id);
        Assert.assertEquals(0, todoOrganizer.getTodoList().size());
        int id2 = todoOrganizer.getDoneTodoList().get(0).getId();
        todoOrganizer.moveBackDoneTodo(id2);
        Assert.assertEquals(1, todoOrganizer.getTodoList().size());
        Assert.assertEquals("Hej", todoOrganizer.getTodoList().get(0).getTitle());

    }

}
