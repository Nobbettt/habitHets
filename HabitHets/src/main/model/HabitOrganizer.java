package main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *This class handles logic that has to do with more than one habit.
 */
public class HabitOrganizer implements IHandler{

    public static HabitOrganizer instant;
    private static List<Habit> habitList;


    /**
     * Constructor of HabitOrganizer that creates
     * a list of habits
     */
    private HabitOrganizer() {
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
    public static HabitOrganizer getInstant() {
        if (instant == null) {
            instant = new HabitOrganizer();
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
        Stack s = new Stack();
        s.add(new DoneHabit(LocalDate.now().minusDays(3)));
        s.add(new DoneHabit(LocalDate.now().minusDays(2)));
        s.add(new DoneHabit(LocalDate.now().minusDays(1)));
        s.add(new DoneHabit(LocalDate.now()));
        habitList.add(Factory.createHabit("testHabit", s,0,"white",LocalDate.now()));
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
        habitList.add(Factory.createHabit(title, new Stack(),0,color,LocalDate.now()));
    }

    public Habit getHabitFromId(int id){
        for (Habit habit : getHabitList()){
            if (habit.getId() == id){
                return habit;
            }
        }
        return null;
    }

    public List<Integer> getAllHabitIDs(){
        List<Integer> ids = new ArrayList<>();
        for (Habit habit : getHabitList()){
            ids.add(habit.getId());
        }
        return ids;
    }

}
