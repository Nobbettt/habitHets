package model;

import org.junit.Assert;
import org.junit.Test;
public class TestHabitOrganizer {

    HabitOrganizer habitOrganizer = new HabitOrganizer();

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
        Assert.assertEquals(1, habitOrganizer.getHabitList().get(1).getId());
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
        Assert.assertEquals(2, habitOrganizer.getHabitList().get(2).getId());

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
        Assert.assertEquals(1, habitOrganizer.getHabitList().size());
        Assert.assertEquals(0, habitOrganizer.getHabitList().get(0).getId());
    }


}
