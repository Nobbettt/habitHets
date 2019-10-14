package main.java.application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import main.model.Aggregate;
import main.model.CalendarAble;
import main.model.Day;
import main.model.Month;
import main.model.*;
import main.view.ExpandedDayView;
import main.view.TodoView;
import main.view.ViewAble;
import main.view.WeekView;
import main.view.YearView;
import main.view.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable, Listener {
    @FXML private GridPane mainGrid;
    @FXML private Label currentValueLbl;
    @FXML private AnchorPane creationPage;
    @FXML private Button prevBtn;
    @FXML private Button nextBtn;
    @FXML private TextField titleField;
    @FXML private DatePicker dateChooser;
    @FXML private ComboBox<String> fromHourTime;
    @FXML private ComboBox<String>fromMinuteTime;
    @FXML private TextField LocationField;
    @FXML private ComboBox<String> toHourTime;
    @FXML private ComboBox<String> toMinuteTime;
    @FXML private TextArea descField;
    @FXML private Button creationButton;
    @FXML private Button closeButton;
    @FXML private Button addButton;
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

    // function is called every min to update timeline in GUI
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



        //temporary
        Aggregate aggregate = new Aggregate();

        List<Day> days = aggregate.getDayFromDate(masterDateTime);
        renderDay(days.get(0));

        List<Day> week = aggregate.getWeekFromDate(LocalDateTime.now());

        renderWeek(week);

        AnchorPane ap = new AnchorPane();
        calendarPane.getChildren().add(ap);

        setupHabit();
        habitPane.getChildren().add(habitView);
        fitItem(habitPane, habitView);
        populateHabit();
        setUpChoiceBoxes();
    }

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
        Day d = aggregate.getDayFromDate(LocalDateTime.now()).get(0);
        renderDay(d);
    }

    private void renderDay(Day day) {
        List<Day> days = new ArrayList<>();
        days.add(day);
        currentView = expandedDayView;
        expandedDayView.updateView(days);
        renderCalendar(expandedDayView);
    }

    // Week stuff
    @FXML
    private void showCalendarWeekClick() {
        //temporary
        List<Day> week = aggregate.getWeekFromDate(LocalDateTime.now());
        renderWeek(week);
    }

    private void renderWeek(List<Day> week) {
        currentView = weekView;
        weekView.updateView(week);
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
        mainGrid.add(calendarPane, 1, 0);
    }

    private void setupHabit() {
        habitPane = new AnchorPane();
        mainGrid.add(habitPane,0,0);
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

    private void setupTodo() {
        todoPane = new AnchorPane();
        mainGrid.add(todoPane, 2, 0);
    }



    private void renderCalendar(Node node) {
        if(calendarPane.getChildren() != null) {
            calendarPane.getChildren().clear();
        }
        calendarPane.getChildren().add(node);
        fitItem(calendarPane, node);
        updateHeadLbl();
    }

    private void fitItem(AnchorPane parent, Node child) {
        parent.setTopAnchor(child, 0.0);
        parent.setRightAnchor(child, 0.0);
        parent.setBottomAnchor(child, 0.0);
        parent.setLeftAnchor(child, 0.0);
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

    @FXML
    private void addButtonClick(){
        creationPage.toFront();
    }
    @FXML
    private void closeButtonClick(){
        clearAllField();
        creationPage.toBack();
    }
    @FXML
    private void createButtonClick(){
        EventHandler ev = EventHandler.getInstant();
        LocalDate ld = dateChooser.getValue();
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.now());
        int fromHour = Integer.parseInt(fromHourTime.getValue());
        int fromMinute = Integer.parseInt(fromMinuteTime.getValue());
        int toHour = Integer.parseInt(toHourTime.getValue());
        int toMinute = Integer.parseInt(toMinuteTime.getValue());
        ev.addEvent(ldt, fromHour, fromMinute, toHour, toMinute, titleField.getText(), LocationField.getText(), descField.getText());
        clearAllField();
        creationPage.toBack();
    }

    private void setUpChoiceBoxes(){
        setUpChoicebox(fromHourTime, true);
        setUpChoicebox(fromMinuteTime, false);
        setUpChoicebox(toHourTime, true);
        setUpChoicebox(toMinuteTime, false);
    }

    private void setUpChoicebox (ComboBox<String> c, boolean isHour){
        ArrayList<String> a = new ArrayList<>();
        c.setEditable(true);
        if (isHour) {
            System.out.println("Hour");
            for (int i = 0; i < 24; i++) {
                a.add("" + i);
            }
        }
        else {
            System.out.println("Minute");
            for (int i = 0; i < 60; i++) {
                a.add("" + i);
            }
        }
        c.getItems().addAll(a);
        c.getSelectionModel().selectNext();

    }

    public void clearAllField(){
        titleField.clear();
        dateChooser.getEditor().clear();
        LocationField.clear();
        descField.clear();
        fromHourTime.getSelectionModel().clearSelection();
        fromMinuteTime.getSelectionModel().clearSelection();
        toHourTime.getSelectionModel().clearSelection();
        toMinuteTime.getSelectionModel().clearSelection();
    }

}
