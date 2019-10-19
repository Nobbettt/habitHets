import main.model.DoneHabit;
import main.model.Habit;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Stack;

public class TestHabit {



    /**
     * A test-method for getStreak() in Habit.
     * By adding yesterday's and today's habit
     * in the doneHabit-list the test can check
     * if the streak increases to 2.
     */
    @Test
    public void getStreak() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(1)));
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now()));
        Assert.assertEquals(2, habit.getStreak());
    }


    /**
     * A test-method for getStreak() in Habit. This test
     * checks if the streak is broken when a habit is
     * missed to get ched one day. By adding today's habit
     * and the two days from now's habit
     * in the doneHabit-list, the test can check
     * if the streak gets broken. Since today's habit
     * is checked the streak is set to 1.
     */
    @Test
    public void getBrokenStreak(){
        //testing if streak is broken
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(2)));
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now()));
        Assert.assertEquals(1,habit.getStreak());
    }


    /**
     * A test-method for onClickHabit in Habit.
     * The method tests a habit, that was checked yesterday,
     * and then someone pushes the button, gets checked today as well.
     */
    @Test
    public void pressedButtonMakesAHabitChecked() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(1)));
        habit.onClickHabit();
        Assert.assertEquals(2, habit.getDoneHabits().size());
        Assert.assertEquals(true, habit.isCheckedToday());
    }


    /**
     * A test-method for onClickHabit in Habit.
     * A habit, that was checked yesterday,
     * and then someone pushes the button today.
     * Then someone press the button once again.
     * The test checks that the habit the gets unchecked.
     */
    @Test
    public void pressedButtonMakesAHabitUnchecked() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit(LocalDate.now().minusDays(1)));
        habit.onClickHabit();
        habit.onClickHabit();
        Assert.assertEquals(1,habit.getDoneHabits().size());
        Assert.assertEquals(false, habit.isCheckedToday());
    }


    /**
     * A test-method for isCheckedToday in Habit.
     * When a habit is unchecked, and the someone pushes the checked-button,
     * the methods tests if it is checked today
     */
    @Test
    public void isCheckedToday() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.onClickHabit();
        boolean testValue = habit.isCheckedToday();
        Assert.assertEquals(true, testValue);
    }

    /**
     * A test-method for isCheckedToday in Habit.
     * A checked habit gets unchecked, the method checks if the stack is empty.
     */
    @Test
    public void notCheckedToday() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.onClickHabit();
        habit.onClickHabit();
        Assert.assertEquals(false, habit.isCheckedToday());
    }


    /**
     * A test-method for isCheckedToday in Habit.
     * The test checks if miss-matched dates returns false.
     */
    @Test
    public void checkedTodayWrongData(){
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        DoneHabit dh = new DoneHabit();
        dh.setDate(LocalDate.now().minusDays(1));
        habit.getDoneHabits().push(dh);
        Assert.assertEquals(false, habit.isCheckedToday());
    }


    /**
     * A test-method for isCheckedYesterday in Habit.
     * Tests if a habit was checked yesterday
     */
    @Test
    public void isCheckedYesterday() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        boolean testValue = habit.isCheckedYesterday();
        Assert.assertEquals(false, testValue);


        DoneHabit dh = new DoneHabit();
        dh.setDate(LocalDate.now().minusDays(1));
        habit.getDoneHabits().push(dh);
        System.out.println(habit.getDoneHabits().peek().getDate());
        System.out.println(habit.getDoneHabits().get(0).getDate());
        Assert.assertEquals(true, habit.isCheckedYesterday());
    }


    /**
     * A test-method for isCheckedYesterday in Habit.
     * Tests if checked today will return false in method.
     */
    @Test
    public void notCheckedTodayInYesterdayMethod(){
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        DoneHabit dh = new DoneHabit();
        dh.setDate(LocalDate.now().minusDays(2));
        habit.getDoneHabits().push(dh);
        Assert.assertEquals(false, habit.isCheckedYesterday());
    }

    /**
     * A test-method for isCheckedYesterday in Habit.
     * Tests if checked 2 days ago will return false in method.
     */
    @Test
    public void notChecked2DaysAgo(){
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        DoneHabit dh = new DoneHabit();
        dh.setDate(LocalDate.now());
        habit.getDoneHabits().push(dh);
        Assert.assertEquals(false, habit.isCheckedYesterday());
    }


    /**
     * A test-method for bestStreak in Habit.
     * Tests if the setter is working.
     */
    @Test
    public void bestStreakSetter() {
        Habit habit = new Habit(1, "testHabit", new Stack(), 6, "blue", LocalDate.now());
        habit.bestStreak();
        Assert.assertEquals(6, habit.getBestStreak());
        habit.setBestStreak(10);
        Assert.assertEquals(10, habit.getBestStreak());
    }


    /**
     * A test-method for bestStreak in Habit.
     * A test that is checking if a habit
     * that gets checked yet another day the best streak updates.
     */
    @Test
    public void updateBestStreak() {
        Stack<DoneHabit> history = new Stack<>();
        history.add(new DoneHabit(LocalDate.now().minusDays(3)));
        history.add(new DoneHabit(LocalDate.now().minusDays(2)));
        history.add(new DoneHabit(LocalDate.now().minusDays(1)));
        Habit habit = new Habit(1,"testHabit", history,3,"blue",LocalDate.now());
        habit.getDoneHabits().add(new DoneHabit());
        habit.bestStreak();
        Assert.assertEquals(4,habit.getBestStreak());
    }



    /**
     * A test-method for getTitle() in Habit.
     * The test checks that a title can get reached
     * and also set if needed to.
     */
    @Test
    public void getTitle(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        Assert.assertEquals("testHabit",habit.getTitle());
        habit.setTitle("k");
        Assert.assertEquals("k",habit.getTitle());
    }

    /**
     * A test-method for getColor() in Habit.
     * The test checks that a color can get reached
     * and also set if needed to.
     */
    @Test
    public void testColor(){
        Habit habit = new Habit(1,"testHabit", new Stack(),6,"blue",LocalDate.now());
        Assert.assertEquals("blue",habit.getColor());
        habit.setColor("pink");
        Assert.assertEquals("pink",habit.getColor());
    }



}
