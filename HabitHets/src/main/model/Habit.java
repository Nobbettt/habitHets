package main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The class Habit contains all of the variables a habit needs.
 * All logic that has to do with only one habit.
 */
public class Habit {

    private int id;
    private String title;
    private int bestStreak;
    private String color;
    private LocalDate dateRecord;

    private Stack<DoneHabit> doneHabits;
    /**
     * This is the only constructor of the Habit class.
     * It contains all of the instance variables. This makes
     * sure that there is no need of adding exceptions to the class
     * methods. (Exceptions that handles if a variable is not set).
     * @param id
     * @param title
     * @param bestStreak
     * @param color
     */
    public Habit(int id, String title, Stack doneHabits, int bestStreak, String color, LocalDate dateRecord) {
        this.id = id;
        this.title = title;
        this.doneHabits =  doneHabits;
        this.bestStreak = bestStreak;
        this.color = color;
        this.dateRecord = dateRecord;
    }

    public LocalDate getDateRecord() {
        return dateRecord;
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

    public String getColor(){
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Stack<DoneHabit> getDoneHabits() {
        return doneHabits;
    }


    /**
     * This method is used as a getter of streak.
     * It counts the streak using the Stack doneHabits.
     * By calling the methods isCheckedToday() and isCheckedYesterday(),
     * date is set to today's date or yesterday's. If neither, the method
     * directly returns 0, since the streak is lost.
     * The for-loop is constructed so date decides what day
     * to count from (hence the if-statements before.)
     * This is because the streak shouldn't be lost, do to the
     * fact that someone hasn't checked their habit YET today.
     * @return
     */

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

        for(int i = doneHabits.size()-1; i >= 0; i--){
            if(doneHabits.get(i).getDate().equals(date)) {
                streak++;
            } else {
                notifyListener();
                return streak;
            }
            date = date.minusDays(1);
        }
        notifyListener();
        return streak;
    }

    /**
     * This method is called when someone pushes the
     * checked-button in the application. If already checked
     * when someone pushes the button, the last done habit is
     * removed from the list. This leeds to the need to check
     * if the the removed habit was set to the best streak and if it
     * was set today, beststreak decreases.
     * If not, and it is the first time someone checks the habit today,
     * a done-habit is added to the stack and bestStreak() is called
     * is case of an update.
     */
    public void onClickHabit() {
        if(isCheckedToday()) {
            if(getStreak() == bestStreak && dateRecord.equals(LocalDate.now())) {
                bestStreak--;
            }
            doneHabits.pop();
            notifyListener();
            return;
        } else {
            doneHabits.push(new DoneHabit());
            bestStreak();
            notifyListener();
        }
    }


    /**
     * This method checks if the habit was checked today
     * by looking at the Stack and comparing it to today's date.
     * @return
     */
    public boolean isCheckedToday() {
        if (doneHabits.size() > 0) {
            LocalDate lastCheck = doneHabits.peek().getDate();
            return LocalDate.now().equals(lastCheck);
        }
        return false;
    }


    /**
     * This method checks if the habit was checked yesterday
     * by looking at the Stack and comparing it to yesterday's date.
     * @return
     */
    public boolean isCheckedYesterday() {
        if (doneHabits.size() > 0) {
            LocalDate lastCheck = doneHabits.get(doneHabits.size()-1).getDate();
            return LocalDate.now().minusDays(1).equals(lastCheck);
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
            notifyListener();
        }
    }

    private List<Listener> listeners = new ArrayList<>();


    public void addListener(Listener l){
        listeners.add(l);

    }

    /**
     * This method launches a nuclear attack on china
     * USE WITH CARE!
     */
    private void notifyListener(){
        for (Listener l : listeners)
            l.actOnUpdate();
    }

}
