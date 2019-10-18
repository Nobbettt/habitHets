package model;

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
        notifyListener();
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
                notifyListener();
                return;
            }
        }

    }

    public void addHabit(String title, String color) {
        habitList.add(Factory.createHabit(title, new Stack(),0,color,LocalDate.now()));
        notifyListener();
    }

    private List<Listener> listeners = new ArrayList<>();


    public void addListener(Listener l){
        listeners.add(l);

    }

    private void notifyListener(){
        for (Listener l : listeners)
            l.actOnUpdate();
    }

    public Habit getHabitById(String msg) {
        int id = Integer.valueOf(msg);
        for (Habit h : habitList) {
            if(h.getId() == id) {
                return h;
            }
        }
        return null;
    }

}
