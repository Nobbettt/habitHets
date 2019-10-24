import main.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TestTxtDbCommunicator {
    @Test
    public void testSave(){
        Factory.createAdvEvent(LocalDateTime.now(), LocalDateTime.now().plusMinutes(120), "Test", "test", "test", "#fff");
        Factory.createHabit("Title", new Stack(), 0, "#47bcad", null);
        Factory.createNote("THIS STRING IS BEEING TESTED", LocalDate.now());
        Factory.createTodo("TODO");
        Todo todo = Factory.createTodo("TODO");
        List<Todo> list = new ArrayList<>();
        list.add(todo);
        TodoOrganizer.getInstant().setTodoList(list);
        TodoOrganizer.doneTodoRemove(todo.getId());

        SaveOnShutDown.saveAll();
        TxtDbCommunicator.importDb();
        Note note = NoteOrganizer.getInstance().getNoteDate(LocalDate.now());
        String desc = note.getDescription();
        Assert.assertEquals("THIS STRING IS BEEING TESTED", desc);
    }

    @Test
    public void testTimeLine() {
        //Application instance = new HabitHets();

        /*
        cc.updateTimeline();
        WeekView week = new WeekView();
        week.updateTimeLine(12, 15);
        double value = week.weekDayEvents.get(0).timeHeight;
        double expected = 12*15*100;
        Assert.assertEquals(expected, value);*/
    }
}
