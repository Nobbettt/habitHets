package main.java.application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    @FXML private Button toggleHabitBtn;
    @FXML private GridPane navbarGrid;
    @FXML private Button dayBtn;
    @FXML private Button weekBtn;
    @FXML private Button monthBtn;
    @FXML private Button yearBtn;
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
        setAsMarkedInNavBar(weekBtn);
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
        } else if(currentView == monthView) {
            masterDateTime = masterDateTime.minusMonths(1);
            calendarData = aggregate.getMonthFromDate(masterDateTime);
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
        List<? extends CalendarAble> calendarData = new ArrayList<>();
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.plusDays(1);
            calendarData = aggregate.getDayFromDate(masterDateTime);
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.plusWeeks(1);
            calendarData = aggregate.getWeekFromDate(masterDateTime);
        } else if(currentView == monthView) {
            masterDateTime = masterDateTime.plusMonths(1);
            calendarData = aggregate.getMonthFromDate(masterDateTime);
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.plusYears(1);
            calendarData = aggregate.getYearFromDate(masterDateTime);
        }
        currentView.updateView(calendarData);
        updateHeadLbl();
    }

    /**
     * Updates the main label between the arrow buttons in the navigation bar depending on view/ time-unit and masterDateTime (the date that is visible on screen)
     */
    private void updateHeadLbl() {
        String headLbl = "";
        if(currentView == expandedDayView) {
            headLbl = aggregate.getDayFromDate(masterDateTime).get(0).getWeekDayString();
        } else if(currentView == weekView) {
            Integer weekNb = aggregate.getDayFromDate(masterDateTime).get(0).getWeekNr();
            headLbl = "Week " + weekNb.toString();
        } else if(currentView == monthView) {
            Integer yearNb = masterDateTime.getYear();
            headLbl = aggregate.getMonth(masterDateTime).getString() + " " + yearNb;
        } else if(currentView == yearView) {
            Integer yearNb = masterDateTime.getYear();
            headLbl = yearNb.toString();
        }

        currentValueLbl.setText(headLbl);
    }

    /**
     * On click method for day button, responsible for changing the calendar view to desired time unit
     */
    @FXML
    private void showCalendarDayClick() {
        renderDay();
        setAsMarkedInNavBar(dayBtn);
    }

    /**
     * Is called upon click from view (and by showCalendarDayClick())
     * Changes currentView to expandedDayView
     * Updates child view, in this case expandedDayView with day info
     * Changes the calendar view to single day view
     */
    private void renderDay() {
        currentView = expandedDayView;
        expandedDayView.updateView(aggregate.getDayFromDate(masterDateTime));
        renderCalendar(expandedDayView);
    }

    /**
     * On click method for week button, responsible for changing the calendar view to desired time unit
     */
    @FXML
    private void showCalendarWeekClick() {
        renderWeek();
        setAsMarkedInNavBar(weekBtn);
    }

    /**
     * Is called upon click from view (and by showCalendarWeekClick())
     * Changes currentView to weekView
     * Updates child view, in this case weeView with week info
     * Changes the calendar view to week schedule view
     */
    private void renderWeek() {
        currentView = weekView;
        weekView.updateView(aggregate.getWeekFromDate(masterDateTime));
        renderCalendar(weekView);
    }

    /**
     * On click method for month button, responsible for changing the calendar view to desired time unit
     */
    @FXML
    private void showCalendarMonthClick() {
        renderMonth();
        setAsMarkedInNavBar(monthBtn);
    }

    /**
     * Is called upon click from view (and by showCalendarMonthClick())
     * Changes currentView to monthView
     * Updates child view, in this case monthView with month info
     * Changes the calendar view to overview of a month view
     */
    private void renderMonth(){
        currentView = monthView;
        monthView.updateView(aggregate.getMonthFromDate(masterDateTime));
        renderCalendar(monthView);
    }

    /**
     * On click method for year button, responsible for changing the calendar view to desired time unit
     */
    @FXML
    private void showCalendarYearClick() {
        renderYear();
        setAsMarkedInNavBar(yearBtn);
    }

    /**
     * Is called upon click from view (and by showCalendarYearClick())
     * Changes currentView to yearView
     * Updates child view, in this case yearView with year info
     * Changes the calendar view to overview of a year view
     */
    private void renderYear() {
        currentView = yearView;
        yearView.updateView(aggregate.getYearFromDate(masterDateTime));
        renderCalendar(yearView);
    }

    /**
     * Is called upon every time one of the 4 view options (in the navigation bar) are clicked.
     * Resets all buttons to default style
     * Marks the clicked button by making its background darker than the default style
     */
    private void setAsMarkedInNavBar(Button b) {
        yearBtn.setStyle("-fx-background-color: transparent");
        monthBtn.setStyle("-fx-background-color: transparent");
        weekBtn.setStyle("-fx-background-color: transparent");
        dayBtn.setStyle("-fx-background-color: transparent");
        b.setStyle("-fx-background-color: #474747");
    }

    private void setupCalender() {
        calendarPane = new AnchorPane();
        mainPane.getChildren().add(calendarPane);
        fitItem(mainPane, calendarPane, 70, 200, 0, 200);
    }

    private void setupHabit() {
        habitPane = new AnchorPane();
        mainPane.getChildren().add(habitPane);
        habitPane.setPrefWidth(200);
        habitPane.setMinWidth(200);
        habitPane.setMaxWidth(200);

        toggleHabitBtn.toFront();
        double centerY = getCenterHeightOfMainGrid();
        toggleHabitBtn.setTranslateY(centerY);

        fitItem(mainPane, toggleHabitBtn, -1, -1, -1, 200);
        fitItem(mainPane, habitPane, 70, -1, 0, 0);
    }

    private double getCenterHeightOfMainGrid() {
        double centerY = mainPane.getBoundsInLocal().getHeight()/2;
        centerY += navbarGrid.getPrefHeight();
        return centerY;
    }

    private void setupTodo() {
        todoPane = new AnchorPane();
        mainPane.getChildren().add(todoPane);
        todoPane.setPrefWidth(200);
        fitItem(mainPane, todoPane, 70, 0, 0, -1);
    }

    private void populateHabit(){
        handler.add();
        handler.add();
        handler.getHabitList().get(0).setTitle("elintina");
        handler.getHabitList().get(0).onClickHabit();
        handler.getHabitList().get(0).setColor("#47BCAD");
        handler.getHabitList().get(1).setTitle("nobbhelge");
        handler.getHabitList().get(1).setColor("#47BCAD");
        habitView.updateHabitView(handler.getHabitList());
    }

    @FXML
    private void toggleHabitClick() {
        double widthValue;
        if(habitView.getIsExpanded()) {
            widthValue = 70;
            habitView.setIsExpanded(false);
            toggleHabitBtn.setText(">");
            habitView.hide();
        } else {
            widthValue = 200;
            toggleHabitBtn.setText("<");
            habitView.setIsExpanded(true);
            habitView.visiable();
        }

        habitPane.setPrefWidth(widthValue);
        habitPane.setMinWidth(widthValue);
        habitPane.setMaxWidth(widthValue);
        mainPane.setLeftAnchor(calendarPane, widthValue);
        mainPane.setLeftAnchor(toggleHabitBtn, widthValue);
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
