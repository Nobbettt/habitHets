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
import main.model.Calender;
import main.model.Facade;
import main.model.Listener;
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
    private ViewAble currentView;
    private LocalDateTime timeNow;
    private LocalDateTime masterDateTime;
    private Facade facade;
    private Calender calender;
    public static ControllerCalendar instance;

    private TodoView todoView;

    public ControllerCalendar() {
        masterDateTime = LocalDateTime.now();
        facade = new Facade();
        calender = Calender.getInstant();
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

    private void updateTimeline() {
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
        facade.addHabitListener(this);
        facade.addTodoListener(this);

        todoPane.getChildren().add(todoView);
        populateTodo();
        renderDay();


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
        List<LocalDateTime> calendarData = new ArrayList<>();
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.minusDays(1);
            calendarData.add(copyMasterdate());
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.minusWeeks(1);
            calendarData = calender.getLdtWeekFromLdt(masterDateTime);
        } else if(currentView == monthView) {
            masterDateTime = masterDateTime.minusMonths(1);
            calendarData = calender.getLdtMonthFromDate(masterDateTime);
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.minusYears(1);
            calendarData = calender.getLdtYearFromDate(masterDateTime);
        }
        currentView.updateView(calendarData);
        updateHeadLbl();
    }

    private void updateCurrentView(){
        List<LocalDateTime> calendarData = new ArrayList<>();
        if(currentView == expandedDayView) {
            calendarData.add(copyMasterdate());
        } else if(currentView == weekView) {
            calendarData = calender.getLdtWeekFromLdt(masterDateTime);
        } else if(currentView == monthView) {
            calendarData = calender.getLdtMonthFromDate(masterDateTime);
        } else if(currentView == yearView) {
            calendarData = calender.getLdtYearFromDate(masterDateTime);
        }
        currentView.updateView(calendarData);
    }

    /**
     * on click this methods moves the represented time unit forward in time depending on the current view.
     */

    @FXML
    private void nextClick() {
        List<LocalDateTime> calendarData = new ArrayList<>();
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.plusDays(1);
            calendarData.add(copyMasterdate());
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.plusWeeks(1);
            calendarData = calender.getLdtWeekFromLdt(masterDateTime);
        } else if(currentView == monthView) {
            masterDateTime = masterDateTime.plusMonths(1);
            calendarData = calender.getLdtMonthFromDate(masterDateTime);
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.plusYears(1);
            calendarData = calender.getLdtYearFromDate(masterDateTime);
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
            headLbl = calender.getDayFromDate(masterDateTime).get(0).getWeekDayString();
        } else if(currentView == weekView) {
            Integer weekNb = calender.getWeekFromLdt(masterDateTime);
            headLbl = "Week " + weekNb.toString();
        } else if(currentView == monthView) {
            Integer yearNb = masterDateTime.getYear();
            headLbl = calender.getMonthString(masterDateTime) + " " + yearNb;
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
        List<LocalDateTime> list = new ArrayList<>();
        list.add(copyMasterdate());
        currentView = expandedDayView;
        expandedDayView.updateView(list);
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
        weekView.updateView(calender.getLdtWeekFromLdt(copyMasterdate()));
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
        monthView.updateView(calender.getLdtMonthFromDate(copyMasterdate()));
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
        yearView.updateView(calender.getLdtYearFromDate(copyMasterdate()));
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

    /**
     * Is called upon application start
     * Attaches the calendar view to application window
     */
    private void setupCalender() {
        calendarPane = new AnchorPane();
        mainPane.getChildren().add(calendarPane);
        fitItem(mainPane, calendarPane, 70, 200, 0, 200);
        addButton.toFront();
    }

    /**
     * Is called upon application start
     * Attaches the habit view to application window
     */
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

    /**
     * Is used to calculate center y-value of calender view
     * Needed to calculate y-position of the habit toggle button
     */
    private double getCenterHeightOfMainGrid() {
        double centerY = mainPane.getBoundsInLocal().getHeight()/2;
        centerY += navbarGrid.getPrefHeight();
        return centerY;
    }

    /**
     * Is called upon application start
     * Attaches the to do view to application window
     */
    private void setupTodo() {
        todoPane = new AnchorPane();
        mainPane.getChildren().add(todoPane);
        todoPane.setPrefWidth(200);
        fitItem(mainPane, todoPane, 70, 0, 0, -1);
    }

    private void populateHabit(){
        habitView.updateHabitView(facade.getAllHabitIds());
    }

    /**
     * Click function for the habit toggle button
     * Collapses and extends the habit view and stretches the calendar view accordingly
     */
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

    /**
     * Is used by the 4 different calendar views to clean and add the current view to the application window
     * Accepts a node which gets attached to the calender pane
     * Also calls method to update the head label with appropriate text depending on current view and date-time
     * @param node
     */
    private void renderCalendar(Node node) {
        if(calendarPane.getChildren() != null) {
            calendarPane.getChildren().clear();
        }
        calendarPane.getChildren().add(node);
        fitItem(calendarPane, node, 0, 0, 0, 0);
        updateHeadLbl();
    }

    /**
     * Method is used as a tool to anchor child nodes to parent nodes given doubles as distances between the nodes
     * Method ignores values of -1
     * @param parent
     * @param child
     * @param top
     * @param right
     * @param bottom
     * @param left
     */
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
        /*
        todoOrganizer.add();
        todoOrganizer.add();
        todoOrganizer.add();
        todoOrganizer.doneTodoRemove(todoOrganizer.getTodoList().get(0).getId());
        todoOrganizer.getTodoList().get(1).setTitle("Hej");
        */
        todoView.updateTodoView();


    }

    private void updateTodoView(){
        todoView.updateTodoView();
    }

    private void updateHabitView(){
        habitView.updateHabitView(facade.getAllHabitIds());
    }

    @Override
    public void actOnUpdate() {
        updateTodoView();
        updateHabitView();
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
        LocalDate ld = dateChooser.getValue();
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.now());
        int fromHour = Integer.parseInt(fromHourTime.getValue());
        int fromMinute = Integer.parseInt(fromMinuteTime.getValue());
        int toHour = Integer.parseInt(toHourTime.getValue());
        int toMinute = Integer.parseInt(toMinuteTime.getValue());
        checkCreationInput();
        if (toHour > fromHour || toHour == fromHour && toMinute > fromMinute) {
            checkCreationInput();
            facade.createEvent(ldt, fromHour, fromMinute, toHour, toMinute, titleField.getText(), LocationField.getText(), descField.getText());
            resetAllField();
            creationPage.toBack();
        }
        updateCurrentView();
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
                if (i < 10) {
                    a.add("0" + i);
                } else {
                    a.add("" + i);
                }
            }
        }
        else {
            for (int i = 0; i < 12; i++) {
                if (i*5 < 10) {
                    a.add("0" + i * 5);
                } else {
                    a.add("" + i*5);
                }
            }
        }
        c.getItems().addAll(a);
        c.setVisibleRowCount(12);
        c.getSelectionModel().selectFirst();

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
    public void editEventPressed(int id){
        editPage.toFront();
        populateExtendedEvent(id);
    }

    @FXML
    public void editClosePressed(){
        editPage.toBack();
    }

    @FXML
    public void editSavePressed() {
        int fromHour = Integer.parseInt(editFromHourTime.getValue());
        int fromMinute = Integer.parseInt(editFromMinuteTime.getValue());
        int toHour = Integer.parseInt(editToHourTime.getValue());
        int toMinute = Integer.parseInt(editToMinuteTime.getValue());
        if (toHour > fromHour || toHour == fromHour && toMinute > fromMinute) {
            checkEditInput();
            LocalDateTime from = LocalDateTime.of(editDate.getValue().getYear(), editDate.getValue().getMonth(), editDate.getValue().getDayOfMonth(), fromHour, fromMinute);
            LocalDateTime to = LocalDateTime.of(editDate.getValue().getYear(), editDate.getValue().getMonth(), editDate.getValue().getDayOfMonth(), toHour, toMinute);
            facade.editEvent(Integer.valueOf(idLabel.getText()), editTitle.getText(), editLocation.getText(), editDesc.getText(), from, to);

        }
        updateCurrentView();
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
        editPage.toBack();
        facade.deleteEvent(Integer.valueOf(idLabel.getText()));
        updateCurrentView();
    }

    private void populateExtendedEvent(int id){
        LocalDateTime eventStarttime = facade.getEventStarttime(id);
        LocalDateTime eventEndtime = facade.getEventEndtime(id);
        editTitle.setText(facade.getEventTitle(id));
        editDate.setValue(facade.getEventStarttime(id).toLocalDate());
        editFromHourTime.setValue(String.valueOf(eventStarttime.getHour()));
        editFromMinuteTime.setValue(String.valueOf(eventStarttime.getMinute()));
        editToHourTime.setValue(String.valueOf(eventEndtime.getHour()));
        editToMinuteTime.setValue(String.valueOf(eventEndtime.getMinute()));
        editDesc.setText(facade.getEventDesc(id));
        editLocation.setText(facade.getEventLocation(id));
        idLabel.setText(String.valueOf(id));
    }

    private LocalDateTime copyMasterdate(){
        return LocalDateTime.of(masterDateTime.getYear(), masterDateTime.getMonthValue(), masterDateTime.getDayOfMonth(), masterDateTime.getHour(), masterDateTime.getMinute());
    }

}
