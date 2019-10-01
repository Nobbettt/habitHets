package main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitHandler implements IHandler{

    public static HabitHandler instant;
    private static List<Habit> habitList;



    private HabitHandler() {
        habitList = new ArrayList<>();
    }

    public static HabitHandler getInstant() {
        if (instant == null) {
            instant = new HabitHandler();
        }
        return instant;

    }

    public List<Habit> getHabitList() {
        return habitList;
    }

    @Override
    public void add() {
        habitList.add(Factory.createHabit("testHabit", true, LocalDate.now(),2,6,"test only","blue"));
    }

    /**
     * The method-parameter (id) is refering to the habit the user want to remove
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
        System.out.println("This habit do not exist");

    }
}
