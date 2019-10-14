package main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *This class handles logic that has to do with more than one habit.
 */
public class HabitHandler implements IHandler{

    public static HabitHandler instant;
    private static List<Habit> habitList;


    /**
     * Constructor of HabitHandler that creates
     * a list of habits
     */
    private HabitHandler() {
        habitList = new ArrayList<>();
    }

    /**
     * This method controlls if an object is created.
     * This makes sure that there may only be one instance
     * at the time (singleton-pattern). If there already exists an
     * instance, another one will not be created. If there in not,
     * an instance will be created.
     * @return
     */
    public static HabitHandler getInstant() {
        if (instant == null) {
            instant = new HabitHandler();
        }
        return instant;

    }

    public List<Habit> getHabitList() {
        return habitList;
    }


    /**
     * This method adds habits by calling Factory class.
     */
    @Override
    public void add() {
        habitList.add(Factory.createHabit("testHabit", new Stack(),6,"blue",LocalDate.now()));
    }

    /**
     * The method-parameter (id) is referring to the habit
     * that the user want to remove from the list of habits.
     * @param id
     */
    @Override
    public void remove(int id) {
        for(Habit habit : habitList){
            if (habit.getId() == id){
                habitList.remove(habit);
                return;
            }
        }

    }

    public void addHabit(String title, String color) {
        habitList.add(Factory.createHabit(title, new Stack(),6,color,LocalDate.now()));
    }

}
