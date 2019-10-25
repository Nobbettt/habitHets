package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *This class handles logic that has to do with more than one habit.
 */
class HabitOrganizer implements IOrganizer {
    private static List<Habit> habitList;
    private static List<Listener> listeners = new ArrayList<>();

    /**
     * Constructor of HabitOrganizer that creates
     * a list of habits
     */
    HabitOrganizer() {
        if (habitList == null){
            habitList = new ArrayList<>();
        }
    }

    static void setHabitList(List<Habit> list) {
        HabitOrganizer.habitList = list;
    }

    static List<Habit> getHabitList() {
        return habitList;
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

    /**
     * This method adds habits by calling Factory class.
     */
    static void addHabit(String title, String color) {
        habitList.add(Factory.createHabit(title, new Stack(),0,color,LocalDate.now()));
        notifyListener();
    }

    static void addListener(Listener l){
        listeners.add(l);

    }

    private static void notifyListener(){
        for (Listener l : listeners)
            l.actOnUpdate();
    }

    static Habit getHabitById(String msg) {
        int id = Integer.valueOf(msg);
        for (Habit h : habitList) {
            if(h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    static Habit getHabitFromId(int id){
        for (Habit habit : getHabitList()){
            if (habit.getId() == id){
                return habit;
            }
        }
        return null;
    }

    static List<Integer> getAllHabitIDs(){
        List<Integer> ids = new ArrayList<>();
        for (Habit habit : getHabitList()){
            ids.add(habit.getId());
        }
        return ids;
    }
}