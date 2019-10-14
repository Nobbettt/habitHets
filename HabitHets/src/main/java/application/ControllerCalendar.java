package main.java.application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.model.*;
import main.view.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable, Listener {
    @FXML private AnchorPane mainPane;
    @FXML private Label currentValueLbl;
    @FXML private Button prevBtn;
    @FXML private Button nextBtn;
    private AnchorPane calendarPane;
    private AnchorPane habitPane;
    private YearView yearView;
    private AnchorPane todoPane;
    private WeekView weekView;
    private HabitView habitView;
    private ExpandedDayView expandedDayView;
    private MonthView monthView;
    private Timeline timeLineCaller;
    public ViewAble currentView;
    private LocalDateTime timeNow;
    EventHandler eventHandler = EventHandler.getInstant();
    private LocalDateTime masterDateTime;
    private Aggregate aggregate;
    private HabitHandler handler = HabitHandler.getInstant();
    private TodoView todoView;
    private TodoHandler todoHandler= TodoHandler.getInstant();

    public ControllerCalendar() {
        masterDateTime = LocalDateTime.now();
        aggregate = new Aggregate();
        yearView = new YearView();
        weekView = new WeekView();
        habitView = new HabitView();
        expandedDayView = new ExpandedDayView();
        currentView = weekView;


        updateTimeline();

        todoView = new TodoView();
        monthView = new MonthView();

    }

    /**
     * function is called every min to update timeline in GUI
     */
    public void updateTimeline() {
        timeNow = LocalDateTime.now();
        currentView.updateTimeLine(timeNow.getHour(), timeNow.getMinute());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // timeLine setup stuff
        timeLineCaller = new Timeline(new KeyFrame(Duration.seconds(60), event -> updateTimeline()));
        timeLineCaller.setCycleCount(Timeline.INDEFINITE);
        timeLineCaller.play();

        setupCalender();
        setupTodo();
        todoHandler.addListener(this);



        todoPane.getChildren().add(todoView);
        populateTodo();


        renderDay();

        List<Day> week = aggregate.getWeekFromDate(LocalDateTime.now());

        renderWeek();

        AnchorPane ap = new AnchorPane();
        calendarPane.getChildren().add(ap);

        setupHabit();
        habitPane.getChildren().add(habitView);
        fitItem(habitPane, habitView, 0, 0, 0, 0);
        populateHabit();
    }

    /**
     * on click this methods moves the represented time unit back in time depending on the current view.
     */
    @FXML
    private void prevClick() {
        List<? extends CalendarAble> calendarData = new ArrayList<>();
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.minusDays(1);
            calendarData = aggregate.getDayFromDate(masterDateTime);
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.minusWeeks(1);
            calendarData = aggregate.getWeekFromDate(masterDateTime);
            //} else if(currentView == monthView) {
            // masterDateTime = masterDateTime.minusMonths(1);
            // calendarData = aggregate. todo
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.minusYears(1);
            calendarData = aggregate.getYearFromDate(masterDateTime);
        }
        currentView.updateView(calendarData);
        updateHeadLbl();
    }

    /**
     * on click this methods moves the represented time unit forward in time depending on the current view.
     */
    @FXML
    private void nextClick() {
        System.out.println("next");
        List<? extends CalendarAble> calendarData = new ArrayList<>();
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.plusDays(1);
            calendarData = aggregate.getDayFromDate(masterDateTime);
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.plusWeeks(1);
            calendarData = aggregate.getWeekFromDate(masterDateTime);
            //} else if(currentView == monthView) {
            // masterDateTime = masterDateTime.minusMonths(1);
            // calendarData = aggregate. todo
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.plusYears(1);
            calendarData = aggregate.getYearFromDate(masterDateTime);
        }
        currentView.updateView(calendarData);
        updateHeadLbl();
    }

    private void updateHeadLbl() {
        String headLbl = "";
        if(currentView == expandedDayView) {
            headLbl = aggregate.getDayFromDate(masterDateTime).get(0).getWeekDayString();
        } else if(currentView == weekView) {
            Integer weekNb = aggregate.getDayFromDate(masterDateTime).get(0).getWeekNr();
            headLbl = "Week " + weekNb.toString();
            //} else if(currentView == monthView) {

        } else if(currentView == yearView) {
            Integer yearNb = masterDateTime.getYear();
            headLbl = yearNb.toString();
        }

        currentValueLbl.setText(headLbl);
    }
    // Day stuff
    @FXML
    private void showCalendarDayClick() {
        //temporary
        renderDay();
    }

    private void renderDay() {
        Day day = aggregate.getDayFromDate(LocalDateTime.now()).get(0);
        List<Day> days = new ArrayList<>();
        days.add(day);
        currentView = expandedDayView;
        expandedDayView.updateView(days);
        renderCalendar(expandedDayView);
    }

    // Week stuff
    @FXML
    private void showCalendarWeekClick() {
        renderWeek();
    }

    private void renderWeek() {
        currentView = weekView;
        weekView.updateView(aggregate.getWeekFromDate(masterDateTime));
        renderCalendar(weekView);
    }


    // Month stuff
    @FXML
    private void showCalendarMonthClick() {
        List<Day> monthDays = new ArrayList<>();
        Month m = aggregate.getMonth(LocalDateTime.now());
        for (Day day : m.getDays()){
            monthDays.add(day);
        }
        renderMonth(monthDays);
    }

    private void renderMonth(List<Day> month){
        currentView = monthView;
        monthView.updateView(month);
        renderCalendar(monthView);
    }

    // Year stuff
    @FXML
    private void showCalendarYearClick() {
        // tmp things
        List<Month> year = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            year.add(new Month(2019, i));
        }


        renderYear(year);
    }

    private void renderYear(List<Month> year) {
        currentView = yearView;
        yearView.updateView(year);
        renderCalendar(yearView);
    }

    private void setupCalender() {
        calendarPane = new AnchorPane();
        mainPane.getChildren().add(calendarPane);
        fitItem(mainPane, calendarPane, 70, 200, 0, 200);
        //mainGrid.add(calendarPane, 1, 0);
    }

    private void setupHabit() {
        habitPane = new AnchorPane();
        mainPane.getChildren().add(habitPane);
        habitPane.setPrefWidth(200);
        fitItem(mainPane, habitPane, 70, -1, 0, 0);
        //mainGrid.add(habitPane,0,0);
    }

    private void setupTodo() {
        todoPane = new AnchorPane();
        mainPane.getChildren().add(todoPane);
        todoPane.setPrefWidth(200);
        fitItem(mainPane, todoPane, 70, 0, 0, -1);
        //mainGrid.add(todoPane, 2, 0);
    }

    private void populateHabit(){
        handler.add();
        handler.add();
        handler.getHabitList().get(0).setTitle("elintina");
        handler.getHabitList().get(0).onClickHabit();
        handler.getHabitList().get(0).setColor("blue");
        handler.getHabitList().get(1).setTitle("nobbhelge");
        handler.getHabitList().get(1).setColor("pink");
        habitView.updateHabitView(handler.getHabitList());
    }

    @FXML
    private void toggleHabitClick() {
        int expanded = 200;
        int collapsed= 70;
        if(habitView.getIsExpanded()) {
            for(double i = expanded; i >= collapsed; i-=1) {
                habitPane.setPrefWidth(i);
                mainPane.setLeftAnchor(calendarPane, i);
            }
            habitView.setIsExpanded(false);
            // todo call on hide things in habit view
        } else {
            for(double i = collapsed; i <= expanded; i+=1) {
                habitPane.setPrefWidth(i);
                mainPane.setLeftAnchor(calendarPane, i);
            }
            habitView.setIsExpanded(true);
            // todo call on show things in habit view
        }
        if(currentView == weekView) {
            renderWeek();
        } else if(currentView == expandedDayView) {
            renderDay();
        }
    }

    private void renderCalendar(Node node) {
        if(calendarPane.getChildren() != null) {
            calendarPane.getChildren().clear();
        }
        calendarPane.getChildren().add(node);
        fitItem(calendarPane, node, 0, 0, 0, 0);
        updateHeadLbl();
    }

    private void fitItem(AnchorPane parent, Node child, double top, double right, double bottom, double left) {
        if (top != -1) {
            parent.setTopAnchor(child, top);
        }
        if (right != -1) {
            parent.setRightAnchor(child, right);
        }
        if (bottom != -1) {
            parent.setBottomAnchor(child, bottom);
        }
        if (left != -1) {
            parent.setLeftAnchor(child, left);
        }
    }




    private void populateTodo(){ //KAn byta ut denna mot updateTodoview() sen när jag inte vill ha hårdkodat
        todoHandler.add();
        todoHandler.add();
        todoHandler.getTodoList().get(1).setTitle("Hej");
        todoView.updateTodoView(todoHandler.getTodoList());

    }

    private void updateTodoView(){
        todoView.updateTodoView(todoHandler.getTodoList());
    }

    @Override
    public void actOnUpdate() {
        updateTodoView();
    }

}
