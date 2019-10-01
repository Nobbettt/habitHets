package main.model;

import java.time.LocalDate;

/**
 * The class Habit is
 */
public class Habit {

    private int id;
    private String title;
    private boolean checkedToday;
    private LocalDate lastChecked = null;
    private int currentStreak;
    private int bestStreak;
    private String description;
    private String color;

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
     * @param checkedToday
     * @param lastChecked
     * @param currentStreak
     * @param bestStreak
     * @param description
     * @param color
     */
    public Habit(int id, String title, boolean checkedToday, LocalDate lastChecked, int currentStreak, int bestStreak,String description, String color) {
        this.id = id;
        this.title = title;
        this.checkedToday = checkedToday;
        this.lastChecked = lastChecked;
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.description = description;
        this.color = color;
        todayHabit();
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

    public boolean getCheckedToday() {
        return checkedToday;
    }

    public void setCheckedToday(boolean checkedToday) {
        this.checkedToday = checkedToday;
    }

    public LocalDate getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(LocalDate lastChecked) {
        this.lastChecked = lastChecked;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
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


    /**
     * This method is called if one makes a new habit or
     * if a habit is retrieved from the database. The first if-
     * statement checks if the habit already exists or not
     * by checking if it has been checked before. If not the
     * constructor will set it.
     */
    public void todayHabit() {
        LocalDate date = LocalDate.now();
        LocalDate yesterday = date.now().minusDays(1);
        if (getLastChecked() != null) {
            if(lastChecked.equals(yesterday) && currentStreak > 1) {
                currentStreak++;
                bestStreak();
            }
            else if(lastChecked.equals(yesterday)){
                currentStreak = 1;
            }
            else if(lastChecked.isBefore(yesterday)) {
                currentStreak = 0;
            }
        }
    }

    /**
     * This method is called when someone pushes the
     * checked-button in the application. If already checked
     * when someone pushes the button, checkToggle() is called.
     * If not, checkedToday is set to true and streak() is called to
     * update streaks and possibly best streak.
     */
    public void onClickHabit() {
        if(checkedToday) {
            checkToggle();
        }
        else {
            setCheckedToday(true);
            streak();
        }
    }


    public void checkToggle(){
        if(getCheckedToday() == checkedToday){
            setCheckedToday(false);
            currentStreak--;
        }
        else
            setCheckedToday(true);

    }


    public void streak() {
        LocalDate date = LocalDate.now();
        LocalDate yesterday = date.now().minusDays(1);
        if (lastChecked.equals(yesterday)) {
            currentStreak++;
            bestStreak();
        }
        else if(lastChecked.isBefore(yesterday)) {
            currentStreak = 1;
        }
    }


    public void bestStreak(){
        if(currentStreak > bestStreak){
            bestStreak = currentStreak;
        }
    }

}
