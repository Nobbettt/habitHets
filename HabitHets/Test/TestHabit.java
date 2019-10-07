import main.model.DoneHabit;
import main.model.Habit;
import main.model.HabitHandler;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Stack;

public class TestHabit {

    @Test
    public void testAddHabit(){
        HabitHandler habitHandler = HabitHandler.getInstant();

        Assert.assertEquals(0,habitHandler.getHabitList().size());
        habitHandler.add();
        habitHandler.add();
        Assert.assertEquals(2,habitHandler.getHabitList().size());
        Assert.assertEquals(0, habitHandler.getHabitList().get(0).getId());
        Assert.assertEquals(1,habitHandler.getHabitList().get(1).getId());

    }

    @Test
    public void testRemoveHabit(){
        HabitHandler habitHandler = HabitHandler.getInstant();
        habitHandler.add();
        habitHandler.add();
        habitHandler.remove(1);
        Assert.assertEquals(1,habitHandler.getHabitList().size());
        Assert.assertEquals(0, habitHandler.getHabitList().get(0).getId());
    }


   @Test
   public void testTitle(){
      Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        Assert.assertEquals("testHabit",habit.getTitle());
        habit.setTitle("k");
        Assert.assertEquals("k",habit.getTitle());
    }


    @Test
    public void testIsCheckedToday() {
        //When a habit is unchecked, and the someone pushes the checked-button, the methos tests if it is checked today
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.onClickHabit();
        boolean testValue = habit.isCheckedToday();
        Assert.assertEquals(true, testValue);

        //A checked habit gets unchecked, the method checks if the stack is empty
        habit.onClickHabit();
        Assert.assertEquals(false,habit.isCheckedToday());

        //missmatch dates returns false
        DoneHabit dh = new DoneHabit();
        dh.setDate(LocalDate.now().minusDays(1));
        habit.getDoneHabits().push(dh);
        Assert.assertEquals(false, habit.isCheckedToday());

    }


    @Test
    public void testIsCheckedYesterday(){
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        boolean testValue = habit.isCheckedYesterday();
        Assert.assertEquals(false, testValue);


        //test if checked yesterday
        DoneHabit dh = new DoneHabit();
        dh.setDate(LocalDate.now().minusDays(1));
        habit.getDoneHabits().push(dh);
        System.out.println(habit.getDoneHabits().peek().getDate());
        System.out.println(habit.getDoneHabits().get(0).getDate());
        Assert.assertEquals(true, habit.isCheckedYesterday());


        //test if checked today will return false in method
        DoneHabit dh2 = new DoneHabit();
        dh2.setDate(LocalDate.now().minusDays(2));
        habit.getDoneHabits().push(dh2);
        Assert.assertEquals(false, habit.isCheckedYesterday());


        //test if checked 2 days ago will return false in method
        DoneHabit dh3 = new DoneHabit();
        dh3.setDate(LocalDate.now());
        habit.getDoneHabits().push(dh3);
        Assert.assertEquals(false, habit.isCheckedYesterday());
    }

/*
    @Test
    public void testCreateHabit(){
        Habit habit = new Habit(2,"test",true, LocalDate.now(),2,6,"testd","green");
        LocalDate date = LocalDate.now();
        LocalDate yesterday = date.now().minusDays(1);
        LocalDate dayBeforeYesterday = date.now().minusDays(2);
        habit.setLastChecked(yesterday);
        habit.createHabit();
        Assert.assertEquals(3,habit.getCurrentStreak());
        habit.setCurrentStreak(1);
        habit.createHabit();
        Assert.assertEquals(1,habit.getCurrentStreak());
        habit.setLastChecked(dayBeforeYesterday);
        habit.setCurrentStreak(3);
        habit.createHabit();
        Assert.assertEquals(0,habit.getCurrentStreak());
        habit.setLastChecked(dayBeforeYesterday);
        habit.setCurrentStreak(1);
        habit.createHabit();
        Assert.assertEquals(0,habit.getCurrentStreak());
    }

    @Test
    public void testOnClickHabitTrue(){
        Habit habit = new Habit(2,"test",true, LocalDate.now(),2,6,"testd","green");
        habit.onClickHabit();
        Assert.assertEquals(habit.getCheckedToday(), false);
        Assert.assertEquals(1,habit.getCurrentStreak());
    }

    @Test
    public void testOnClickHabitFalse(){
        Habit habit = new Habit(2,"test",false, LocalDate.now(),2,6,"testd","green");
        LocalDate date = LocalDate.now();
        LocalDate yesterday = date.now().minusDays(1);
        LocalDate dayBeforeYesterday = date.now().minusDays(2);
        habit.setLastChecked(yesterday);
        habit.onClickHabit();
        Assert.assertTrue(habit.getCheckedToday());
        Assert.assertEquals(3,habit.getCurrentStreak());
        habit.setLastChecked(dayBeforeYesterday);
        habit.setCheckedToday(false);
        habit.onClickHabit();
        Assert.assertEquals(1,habit.getCurrentStreak());
    }

    @Test
    public void testStreak(){
        Habit habit = new Habit(2,"test",true, LocalDate.now(),2,2,"testd","green");
        Assert.assertEquals(2,habit.getCurrentStreak());
        LocalDate date = LocalDate.now();
        LocalDate yesterday = date.now().minusDays(1);
        LocalDate dayBeforeYesterday = date.now().minusDays(2);
        habit.setLastChecked(yesterday);
        habit.streak();
        Assert.assertEquals(3,habit.getCurrentStreak());
        Assert.assertEquals(habit.getBestStreak(),3);
        habit.setLastChecked(dayBeforeYesterday);
        habit.streak();
        Assert.assertEquals(1,habit.getCurrentStreak());
        Assert.assertEquals(habit.getBestStreak(),3);
    }
*/

    @Test
    public void testBestStreak(){
        HabitHandler habitHandler = HabitHandler.getInstant();
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        habit.bestStreak();
        Assert.assertEquals(6,habit.getBestStreak());

        //testing setter
        habit.setBestStreak(10);
        Assert.assertEquals(10,habit.getBestStreak());


        //checking a habit yet another day and test if best streak updates
        Stack<DoneHabit> history = new Stack<>();
        history.add(new DoneHabit(LocalDate.now().minusDays(3)));
        history.add(new DoneHabit(LocalDate.now().minusDays(2)));
        history.add(new DoneHabit(LocalDate.now().minusDays(1)));
        System.out.println(history.get(history.size()-1).getDate());
        Habit habit2 = new Habit(1,"testHabit", history,3,"blue",LocalDate.now());
        habit2.getDoneHabits().add(new DoneHabit());
        habit2.bestStreak();
        Assert.assertEquals(4,habit2.getBestStreak());
    }


    @Test
    public void testColor(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        Assert.assertEquals("blue",habit.getColor());
        habit.setColor("pink");
        Assert.assertEquals("pink",habit.getColor());
    }



}
