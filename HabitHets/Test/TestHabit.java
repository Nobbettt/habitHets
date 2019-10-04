import main.model.Habit;
import main.model.HabitHandler;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Stack;

public class TestHabit {
/*
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
      Habit habit = new Habit(1,"testHabit", new Stack(),6,"test only","blue");
        Assert.assertEquals("testHabit",habit.getTitle());
        habit.setTitle("k");
        Assert.assertEquals("k",habit.getTitle());
    }


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
    public void testToggle(){
        Habit habit = new Habit(2,"test",true, LocalDate.now(),2,6,"testd","green");
        habit.checkToggle();
        boolean testHabit = habit.getCheckedToday();
        Assert.assertEquals(testHabit,false);
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


    @Test
    public void testBestStreak(){
        Habit habit = new Habit(2,"test",true, LocalDate.now(),2,6,"testd","green");
        habit.bestStreak();
        Assert.assertEquals(6,habit.getBestStreak());
        habit.setBestStreak(10);
        Assert.assertEquals(10,habit.getBestStreak());
        habit.setCurrentStreak(11);
        habit.bestStreak();
        Assert.assertEquals(11,habit.getBestStreak());
    }



    @Test
    public void testDescription(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"test only","blue");
        Assert.assertEquals("test only",habit.getDescription());
        habit.setDescription("this test will work");
        Assert.assertEquals("this test will work",habit.getDescription());
    }

    @Test
    public void testColor(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"test only","blue");
        Assert.assertEquals("blue",habit.getColor());
        habit.setColor("pink");
        Assert.assertEquals("pink",habit.getColor());
    }
    */


}
