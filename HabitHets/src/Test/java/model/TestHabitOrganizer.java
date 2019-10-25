package model;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class TestHabitOrganizer {

    static HabitOrganizer habitOrganizer = new HabitOrganizer();
    static List<Habit> events;

    @BeforeClass
    public static void setUp() {
        TxtDbCommunicator.importDb();
        events = copyList();
        habitOrganizer.setHabitList(new ArrayList<>());
    }

    @Before
    public void clear(){
        habitOrganizer.getHabitList().clear();
    }

    @AfterClass
    public static void resetClass(){
        HabitOrganizer.setHabitList(events);
    }

    private static List<Habit> copyList(){
        List<Habit> tmpList = new ArrayList<>();
        for (Habit habit : habitOrganizer.getHabitList()){
            tmpList.add(habit);
        }
        return tmpList;
    }

    /**
     * A test-method for Add() in HabitOrganizer.
     * The test checks that the lists of habits increases
     * with the same amount times add-method is called.
     * The test also checks that the Id of each habit is
     * matching the Id in the list.
     */
    @Test
    public void add(){
        Assert.assertEquals(0, habitOrganizer.getHabitList().size());
        habitOrganizer.addHabit("test", "red");
        habitOrganizer.addHabit("test2", "red");
        Assert.assertEquals(2, habitOrganizer.getHabitList().size());
    }

    /**
     * A test-method for AddHabit() in HabitOrganizer.
     * The test checks that the lists of habits increases
     * with the same amount times add-method is called.
     * The test also checks that the Id of each habit is
     * matching the Id in the list.
     */
    @Test
    public void addHabit(){
        Assert.assertEquals(0, habitOrganizer.getHabitList().size());

        habitOrganizer.addHabit("sova", "red");
        habitOrganizer.addHabit("träna", "blue");
        habitOrganizer.addHabit("äta", "pink");
        Assert.assertEquals(3, habitOrganizer.getHabitList().size());

    }

    /**
     *  A test-method for removeHabit() in HabitOrganizer.
     *  The test checks that the lists of habits decreases
     *  with the same amount times remove-method is called.
     *  The test also checks that the Id of each habit is
     *  matching the Id in the list.
     */
    @Test
    public void removeHabit(){
        habitOrganizer.addHabit("test", "blue");
        habitOrganizer.addHabit("test", "pink");
        habitOrganizer.remove(1);
        Assert.assertEquals(2, habitOrganizer.getHabitList().size());
    }


}
