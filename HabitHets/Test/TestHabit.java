import main.model.DoneHabit;
import main.model.Habit;
import main.model.HabitOrganizer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Stack;

public class TestHabit {

    HabitOrganizer habitOrganizer = HabitOrganizer.getInstant();


    @Test
    public void testAddHabit(){
        Assert.assertEquals(0, habitOrganizer.getHabitList().size());
        habitOrganizer.add();
        habitOrganizer.add();
        Assert.assertEquals(2, habitOrganizer.getHabitList().size());
        Assert.assertEquals(0, habitOrganizer.getHabitList().get(0).getId());
        Assert.assertEquals(1, habitOrganizer.getHabitList().get(1).getId());
    }

    @Test
    public void testRemoveHabit(){
        habitOrganizer.add();
        habitOrganizer.add();
        habitOrganizer.remove(1);
        Assert.assertEquals(1, habitOrganizer.getHabitList().size());
        Assert.assertEquals(0, habitOrganizer.getHabitList().get(0).getId());
    }


   @Test
   public void testTitle(){
      Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        Assert.assertEquals("testHabit",habit.getTitle());
        habit.setTitle("k");
        Assert.assertEquals("k",habit.getTitle());
    }


    @Test
    public void  testGetStreak(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(1)));
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now()));
        Assert.assertEquals(2,habit.getStreak());

        //testing if streak is broken
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(2)));
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now()));
        Assert.assertEquals(1,habit.getStreak());
    }


    @Test
    public void testOnClickHabit(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(1)));
        Assert.assertEquals(1,habit.getDoneHabits().size());
        habit.onClickHabit();
        Assert.assertEquals(2,habit.getDoneHabits().size());
        Assert.assertEquals(true, habit.isCheckedToday());

        habit.onClickHabit();
        Assert.assertEquals(1,habit.getDoneHabits().size());
        Assert.assertEquals(false, habit.isCheckedToday());
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



    @Test
    public void testBestStreak(){
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
