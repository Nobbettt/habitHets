package java;

import main.java.model.TodoHandler;
import org.junit.Assert;
import org.junit.Test;

public class TestTodo {

    /**
     * This test checks that the add-method works the way it is suppose to do.
     */

    @Test
    public void testTodoAdd(){
        TodoHandler todoHandler = TodoHandler.getInstant();
        todoHandler.add();
        todoHandler.add();
        Assert.assertEquals(2, todoHandler.getTodoList().size());
        Assert.assertEquals("testTodo", todoHandler.getTodoList().get(0).getTitle() );
        Assert.assertEquals(0, todoHandler.getTodoList().get(0).getId());
        Assert.assertEquals(1, todoHandler.getTodoList().get(1).getId());

    }


    /**
     * This test checks that you can change the title of the todoo.
     */

    @Test
    public void testTodoUpdateSubject(){
        TodoHandler todoHandler = TodoHandler.getInstant();
        todoHandler.add();
        todoHandler.add();
        Assert.assertEquals("testTodo", todoHandler.getTodoList().get(0).getTitle() );
        todoHandler.getTodoList().get(0).setTitle("Städa");
        Assert.assertEquals("Städa", todoHandler.getTodoList().get(0).getTitle() );
        Assert.assertEquals("testTodo", todoHandler.getTodoList().get(1).getTitle() );
    }

    /**
     * This test checks that when you complete a todoo that the todoo go from the todolist to donetodolist.
     */

    @Test
    public void testTodoRemove() {
        TodoHandler todoHandler = TodoHandler.getInstant();
        todoHandler.add();
        todoHandler.add();
        Assert.assertEquals(2, todoHandler.getTodoList().size());
        todoHandler.getTodoList().get(0).setTitle("Städa");
        Assert.assertEquals("Städa", todoHandler.getTodoList().get(0).getTitle() );
        todoHandler.doneTodoRemove(0);
        Assert.assertEquals(1, todoHandler.getTodoList().size());
        Assert.assertEquals(1, todoHandler.getDoneTodoList().size());
        Assert.assertEquals("testTodo", todoHandler.getTodoList().get(0).getTitle() );
        Assert.assertEquals("Städa", todoHandler.getDoneTodoList().get(0).getTitle() );
    }

    /**
     * This test checks that the length of the donetodolist never goes over the limit.
     */

    @Test
    public void testLengthOfDoneTodoList() {
        TodoHandler todoHandler = TodoHandler.getInstant();
        for (int a =0; a<8 ;a++){
            todoHandler.add();
            todoHandler.getTodoList().get(a).setTitle(""+a+"");
        }
        for (int r=0; r<7; r++){
           todoHandler.doneTodoRemove(todoHandler.getTodoList().size()-1);
        }

        Assert.assertEquals(1, todoHandler.getTodoList().size());
        Assert.assertEquals(5, todoHandler.getDoneTodoList().size());
        for(int i =0; i<todoHandler.getDoneTodoList().size(); i++){

            System.out.println(todoHandler.getDoneTodoList().get(i).getTitle());
        }
    }

    /**
     * This test checks that you remove a todoo without complete it.
     */


    @Test
    public void deleteTodo(){
        TodoHandler todoHandler = TodoHandler.getInstant();
        todoHandler.add();
        todoHandler.add();
        Assert.assertEquals(2, todoHandler.getTodoList().size());
        todoHandler.remove(todoHandler.getTodoList().get(0).getId());
        Assert.assertEquals(1, todoHandler.getTodoList().size());
    }



}
