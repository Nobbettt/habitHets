package main.java.application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import main.model.*;
import main.view.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable, Listener {
    @FXML private AnchorPane mainPane;
    @FXML private Label currentValueLbl;
    @FXML private AnchorPane creationPage;
    @FXML private Button prevBtn;
    @FXML private Button nextBtn;
    @FXML private Button toggleHabitBtn;
    @FXML private GridPane navbarGrid;
    @FXML private Button dayBtn;
    @FXML private Button weekBtn;
    @FXML private Button monthBtn;
    @FXML private Button yearBtn;
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
    @FXML private AnchorPane editPage;
    @FXML private TextField editTitle;
    @FXML private DatePicker editDate;
    @FXML private TextField editLocation;
    @FXML private TextArea editDesc;
    @FXML private Button saveButton;
    @FXML private Button editCloseButton;
    @FXML private ComboBox<String> editFromHourTime;
    @FXML private ComboBox<String> editFromMinuteTime;
    @FXML private ComboBox<String> editToHourTime;
    @FXML private ComboBox<String> editToMinuteTime;
    @FXML private Button deleteButton;
    @FXML private Label idLabel;

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
    EventOrganizer eventOrganizer = EventOrganizer.getInstant();
    private LocalDateTime masterDateTime;
    private Aggregate aggregate;
    public static ControllerCalendar instance;

    private HabitOrganizer handler = HabitOrganizer.getInstant();
    private TodoView todoView;
    private TodoOrganizer todoOrganizer = TodoOrganizer.getInstant();

    public ControllerCalendar() {
        masterDateTime = LocalDateTime.now();
        aggregate = new Aggregate();
        yearView = new YearView();
        weekView = new WeekView();
        habitView = new HabitView();
        expandedDayView = new ExpandedDayView();
        currentView = weekView;
        instance = this;

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
        todoOrganizer.addListener(this);

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
        setUpChoiceBoxes();
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
        addButton.toFront();
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
        handler.getHabitList().get(0).setBestStreak(4);
        //handler.getHabitList().get(0).onClickHabit();
        handler.getHabitList().get(0).setColor("#47BCAD");
        handler.getHabitList().get(1).setTitle("nobbhelge");
        handler.getHabitList().get(1).setColor("#47BCAD");
        handler.getHabitList().get(1).setBestStreak(7);
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
        AnchorPane.setLeftAnchor(calendarPane, widthValue);
        AnchorPane.setLeftAnchor(toggleHabitBtn, widthValue);
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
            AnchorPane.setTopAnchor(child, top);
        }
        if (right != -1) {
            AnchorPane.setRightAnchor(child, right);
        }
        if (bottom != -1) {
            AnchorPane.setBottomAnchor(child, bottom);
        }
        if (left != -1) {
            AnchorPane.setLeftAnchor(child, left);
        }
    }

    private void populateTodo(){ //KAn byta ut denna mot updateTodoview() sen när jag inte vill ha hårdkodat
        todoOrganizer.add();
        todoOrganizer.add();
        todoOrganizer.add();
        todoOrganizer.doneTodoRemove(todoOrganizer.getTodoList().get(0).getId());
        todoOrganizer.getTodoList().get(1).setTitle("Hej");
        todoView.updateTodoView();


    }

    private void updateTodoView(){
        todoView.updateTodoView();
    }

    @Override
    public void actOnUpdate() { updateTodoView();

    }

    @FXML
    private void addButtonClick(){
        creationPage.toFront();
        dateChooser.setValue(LocalDate.now());
        titleField.setText("New Event");
    }

    @FXML
    private void closeButtonClick(){
        resetAllField();
        creationPage.toBack();
    }

    @FXML
    private void createButtonClick(){
        EventOrganizer ev = EventOrganizer.getInstant();
        LocalDate ld = dateChooser.getValue();
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.now());
        int fromHour = Integer.parseInt(fromHourTime.getValue());
        int fromMinute = Integer.parseInt(fromMinuteTime.getValue());
        int toHour = Integer.parseInt(toHourTime.getValue());
        int toMinute = Integer.parseInt(toMinuteTime.getValue());
        checkCreationInput();
        if (toHour > fromHour || toHour == fromHour && toMinute > fromMinute) {
            checkCreationInput();
            ev.addEvent(ldt, fromHour, fromMinute, toHour, toMinute, titleField.getText(), LocationField.getText(), descField.getText());
            resetAllField();
            creationPage.toBack();
        }
    }

    private void checkCreationInput(){
        if (titleField.getText() == null){
            titleField.setText("New Event");
        }
        if (LocationField.getText() == null){
            LocationField.setText("");
        }
        if (descField.getText() == null){
            descField.setText("");
        }
    }

    private void setUpChoiceBoxes(){
        setUpChoicebox(fromHourTime, true);
        setUpChoicebox(fromMinuteTime, false);
        setUpChoicebox(toHourTime, true);
        setUpChoicebox(toMinuteTime, false);
        setUpChoicebox(editFromHourTime, true);
        setUpChoicebox(editFromMinuteTime, false);
        setUpChoicebox(editToHourTime, true);
        setUpChoicebox(editToMinuteTime, false);
    }

    private void setUpChoicebox (ComboBox<String> c, boolean isHour){
        ArrayList<String> a = new ArrayList<>();
        c.setEditable(true);
        if (isHour) {
            for (int i = 0; i < 24; i++) {
                a.add("" + i);
            }
        }
        else {
            for (int i = 0; i < 60; i++) {
                a.add("" + i);
            }
        }
        c.getItems().addAll(a);
        c.getSelectionModel().selectNext();

    }

    public void resetAllField(){
        titleField.clear();
        dateChooser.setValue(LocalDate.now());
        LocationField.clear();
        descField.clear();
        fromHourTime.getSelectionModel().selectFirst();
        fromMinuteTime.getSelectionModel().selectFirst();
        toHourTime.getSelectionModel().selectFirst();
        toMinuteTime.getSelectionModel().selectFirst();
    }

    @FXML
    public void editEventPressed(Event event){
        editPage.toFront();
        populateExtendedEvent(event);
    }

    @FXML
    public void editClosePressed(){
        editPage.toBack();
    }

    @FXML
    public void editSavePressed() {
        EventOrganizer ev = EventOrganizer.getInstant();
        Event editedEvent = ev.getEventOfId(Integer.valueOf(idLabel.getText()));
        int fromHour = Integer.parseInt(editFromHourTime.getValue());
        int fromMinute = Integer.parseInt(editFromMinuteTime.getValue());
        int toHour = Integer.parseInt(editToHourTime.getValue());
        int toMinute = Integer.parseInt(editToMinuteTime.getValue());
        if (toHour > fromHour || toHour == fromHour && toMinute > fromMinute) {
            checkEditInput();
            editedEvent.setTitle(editTitle.getText());
            editedEvent.setLocation(editLocation.getText());
            editedEvent.setDescription(editDesc.getText());
            editedEvent.setStartTime(LocalDateTime.of(editDate.getValue().getYear(), editDate.getValue().getMonth(), editDate.getValue().getDayOfMonth(), fromHour, fromMinute));
            editedEvent.setEndTime(LocalDateTime.of(editDate.getValue().getYear(), editDate.getValue().getMonth(), editDate.getValue().getDayOfMonth(), toHour, toMinute));
        }
        editPage.toBack();
    }

    private void checkEditInput(){
        if (editTitle.getText() == null){
            editTitle.setText("Edited Event");
        }
        if (editLocation.getText() == null){
            editLocation.setText("");
        }
        if (editDesc.getText() == null){
            editDesc.setText("");
        }
    }

    @FXML
    private void deleteEventPressed(){
        EventOrganizer ev = EventOrganizer.getInstant();
        Event editedEvent = ev.getEventOfId(Integer.valueOf(idLabel.getText()));
        editPage.toBack();
        ev.getEventList().remove(editedEvent);
    }

    public void populateExtendedEvent(Event event){
        System.out.println(event.getEndTime().getMinute());
        editTitle.setText(event.getTitle());
        editDate.setValue(event.getStartTime().toLocalDate());
        editFromHourTime.setValue(Integer.toString(event.getStartTime().getHour()));
        editFromMinuteTime.setValue(Integer.toString(event.getStartTime().getMinute()));
        editToHourTime.setValue(Integer.toString(event.getEndTime().getHour()));
        editToMinuteTime.setValue(Integer.toString(event.getEndTime().getMinute()));
        editDesc.setText(event.getDescription());
        editLocation.setText(event.getLocation());
        idLabel.setText(Integer.toString(event.getId()));
    }

}
