package main.model;

import java.time.LocalDate;
import java.util.Stack;

/**
 * The class Habit contains all of the variables a habit needs.
 * All logic that has to do with only one habit.
 */
public class Habit {

    private int id;
    private String title;
    private int bestStreak;
    private String description;
    private String color;
    private LocalDate dateRecord;

    Stack<DoneHabit> doneHabits;
    /**
     * This is the only constructor of the Habit class.
     * It contains all of the instance variables. This makes
     * sure that there is no need of adding exceptions to the class
     * methods. (Exceptions that handles if a variable is not set).
     * It also calls the method todayHabit(), that checks if
     * a habit already has a streak when created (that is if the
     * program is shut down and then opened again, the habits that
     * existed before are retrieved from the database and alrealy has
     * a streak.)
     * @param id
     * @param title
     * @param bestStreak
     * @param description
     * @param color
     */
    public Habit(int id, String title, Stack doneHabits, int bestStreak,String description, String color) {
        this.id = id;
        this.title = title;
        this.bestStreak = bestStreak;
        this.description = description;
        this.color = color;
        this.doneHabits =  doneHabits;
    }


    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStreak() {
        int streak = 0;
        LocalDate date;
        if(isCheckedToday()) {
            date = LocalDate.now();
        } else if(isCheckedYesterday()) {
            date = LocalDate.now().minusDays(1);
        } else {
            return 0;
        }

        for(DoneHabit d : doneHabits){
            if(d.getDate() == date) {
                streak++;
            } else {
                return streak;
            }
            date = date.minusDays(1);
        }
        return streak;
    }

    /**
     * This method is called when someone pushes the
     * checked-button in the application. If already checked
     * when someone pushes the button, checkToggle() is called.
     * If not, checkedToday is set to true and streak() is called to
     * update streaks and possibly best streak.
     */
    public void onClickHabit() {
        if(doneHabits.size() > 0) {
            if(isCheckedToday())  {
                doneHabits.pop();
                if(getStreak() == bestStreak && dateRecord == LocalDate.now()) {
                    bestStreak--;
                }
                return;
            }
        }
        doneHabits.push(new DoneHabit());
    }

    public Boolean isCheckedToday() {
        LocalDate lastCheck = doneHabits.peek().getDate();
        if(lastCheck == LocalDate.now()) {
            return true;
        }
        return false;
    }

    public Boolean isCheckedYesterday() {
        LocalDate lastCheck = doneHabits.get(doneHabits.size()-1).getDate();
        if(lastCheck == LocalDate.now().minusDays(-1)) {
            return true;
        }
        return false;
    }


    /**
     * This method is called if the streak increases
     * and one want to check if it might be the new
     * best streak.
     */
    public void bestStreak() {
        if(getStreak() > bestStreak){
            bestStreak = getStreak();
            dateRecord = LocalDate.now();
        }
    }

}
