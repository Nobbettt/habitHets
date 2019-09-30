import main.model.Todo;
import main.model.TodoHandler;
import org.junit.Assert;
import org.junit.Test;

public class TestTodo {

    @Test
    public void testTodoadd(){
        TodoHandler todoHandler = TodoHandler.getInstant();
        todoHandler.add();
        todoHandler.add();
        Assert.assertEquals(2, todoHandler.getTodoList().size());
        Assert.assertEquals("testTodo", todoHandler.getTodoList().get(0).getTitle() );
        Assert.assertEquals(0, todoHandler.getTodoList().get(0).getId());
        Assert.assertEquals(1, todoHandler.getTodoList().get(1).getId());

    }

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

    @Test
    public void testTodoRemove() {
        TodoHandler todoHandler = TodoHandler.getInstant();
        todoHandler.add();
        todoHandler.add();
        Assert.assertEquals(2, todoHandler.getTodoList().size());
        todoHandler.getTodoList().get(0).setTitle("Städa");
        Assert.assertEquals("Städa", todoHandler.getTodoList().get(0).getTitle() );
        todoHandler.remove(0);
        Assert.assertEquals(1, todoHandler.getTodoList().size());
        Assert.assertEquals(1, todoHandler.getDoneTodoList().size());
        Assert.assertEquals("testTodo", todoHandler.getTodoList().get(0).getTitle() );
        Assert.assertEquals("Städa", todoHandler.getDoneTodoList().get(0).getTitle() );
    }

    @Test
    public void testLengthOfDoneTodoList() {
        TodoHandler todoHandler = TodoHandler.getInstant();
        for (int a =0; a<8 ;a++){
            todoHandler.add();
        }
        for (int r=0; r<7; r++){
            todoHandler.remove(todoHandler.getTodoList().size()-1);
        }
        Assert.assertEquals(1, todoHandler.getTodoList().size());
        Assert.assertEquals(5, todoHandler.getDoneTodoList().size());
        for(int i =0; i<todoHandler.getDoneTodoList().size(); i++){

            System.out.println(todoHandler.getDoneTodoList().get(i).getTitle());
        }
    }



}
