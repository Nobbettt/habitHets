package Controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import model.calendar.Calender;
import model.Facade;
import model.Listener;
import model.TxtDbCommunicator;
import view.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable, Listener, ViewListener {
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
    @FXML private AnchorPane eventCreatePane;
    @FXML private AnchorPane eventEditPane;

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
    private TodoView todoView;

    /**
     * A constructor for the class that sets all the needed attributes as well as imports all the
     * data stored from previous runs
     */

    public ControllerCalendar() {
        // to change -->
        TxtDbCommunicator.importDb();

        masterDateTime = LocalDateTime.now();
        facade = new Facade();
        calender = Calender.getInstance();
        yearView = new YearView();
        weekView = new WeekView();
        habitView = new HabitView();
        expandedDayView = new ExpandedDayView();
        currentView = weekView;
        todoView = new TodoView();
        monthView = new MonthView();
        EventObserver.addListener(this);
    }

    /**
     * A method is called every min to update the timeline in GUI
     */

    private void updateTimeline() {
        timeNow = LocalDateTime.now();
        currentView.updateTimeLine(timeNow.getHour(), timeNow.getMinute());
    }

    /**
     * A method that is called when the program starts up, it is implemented since the class implements initializeable
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTimeline();
        setupCalender();
        setupTodo();
        setupHabit();
        setUpChoiceBoxes();
        renderWeek();
        updateTimeline();
        setUpdateViewOnStart();
    }


    private void setUpdateViewOnStart() {
        timeLineCaller = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateViewOnStart()));
        timeLineCaller.setCycleCount(10);
        timeLineCaller.play();
    }

    private void updateViewOnStart() {
        currentView = expandedDayView;
        expandedDayView.updateView(masterDateTime);
        currentView = weekView;
        weekView.updateView(masterDateTime);
    }

    /**
     * Sets up all things needed in order for the timeline to properly work
     */
    private void setupTimeline(){
        timeLineCaller = new Timeline(new KeyFrame(Duration.seconds(60), event -> updateTimeline()));
        timeLineCaller.setCycleCount(Timeline.INDEFINITE);
        timeLineCaller.play();
    }

    /**
     * Is called upon application start
     * Attaches the habit view to application window
     */
    private void setupHabit() {
        habitPane = new AnchorPane();
        mainPane.getChildren().add(habitPane);
        habitPane.setPrefWidth(70);
        habitPane.setMinWidth(70);
        habitPane.setMaxWidth(70);

        toggleHabitBtn.toFront();
        double centerY = getCenterHeightOfMainGrid();
        toggleHabitBtn.setTranslateY(centerY);

        fitItem(mainPane, toggleHabitBtn, -1, -1, -1, 70);
        fitItem(mainPane, habitPane, 70, -1, 0, 0);
        facade.addHabitListener(this);

        habitPane.getChildren().add(habitView);
        fitItem(habitPane, habitView, 0, 0, 0, 0);

        habitView.updateHabitView(facade.getAllHabitIds());
        habitView.hide();
        toggleHabitBtn.setText(">");
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

        todoPane.getChildren().add(todoView);

        todoView.updateTodoView();

        facade.addTodoListener(this);
    }

    /**
     * Is called upon application start
     * Attaches the calendar view to application window
     */
    private void setupCalender() {
        calendarPane = new AnchorPane();
        mainPane.getChildren().add(calendarPane);
        fitItem(mainPane, calendarPane, 70, 200, 0, 70);
        addButton.toFront();
        AnchorPane ap = new AnchorPane();
        calendarPane.getChildren().add(ap);

        setAsMarkedInNavBar(weekBtn);
    }


    /**
     * on click this methods moves the represented time unit back in time depending on the current view.
     */

    @FXML
    private void prevClick() {
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.minusDays(1);
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.minusWeeks(1);
        } else if(currentView == monthView) {
            masterDateTime = masterDateTime.minusMonths(1);
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.minusYears(1);
        }
        currentView.updateView(masterDateTime);
        updateHeadLbl();
    }

    /**
     * This method updates the current view that is shown to the user
     */
    private void updateCurrentView(){
        currentView.updateView(masterDateTime);
    }

    /**
     * on click this methods moves the represented time unit forward in time depending on the current view.
     */

    @FXML
    private void nextClick() {
        if(currentView == expandedDayView) {
            masterDateTime = masterDateTime.plusDays(1);
        } else if(currentView == weekView) {
            masterDateTime = masterDateTime.plusWeeks(1);
        } else if(currentView == monthView) {
            masterDateTime = masterDateTime.plusMonths(1);
        } else if(currentView == yearView) {
            masterDateTime = masterDateTime.plusYears(1);
        }
        currentView.updateView(masterDateTime);
        updateHeadLbl();
    }

    /**
     * Updates the main label between the arrow buttons in the navigation bar depending on view/ time-unit and masterDateTime (the date that is visible on screen)
     */

    private void updateHeadLbl() {
        String headLbl = "";
        if(currentView == expandedDayView) {
            headLbl = calender.getWeekdayString(masterDateTime);
        } else if(currentView == weekView) {
            headLbl = "Week " + calender.getWeekFromLdt(masterDateTime);
        } else if(currentView == monthView) {
            headLbl = calender.getMonthString(masterDateTime) + " " + masterDateTime.getYear();
        } else if(currentView == yearView) {
            headLbl = String.valueOf(masterDateTime.getYear());
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
        expandedDayView.updateView(masterDateTime);
        renderCalendar(expandedDayView);
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
        weekView.updateView(masterDateTime);
        renderCalendar(weekView);
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
        monthView.updateView(masterDateTime);
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
        yearView.updateView(masterDateTime);
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
     * Is used to calculate center y-value of calender view
     * Needed to calculate y-position of the habit toggle button
     */
    private double getCenterHeightOfMainGrid() {
        double centerY = mainPane.getBoundsInLocal().getHeight()/2;
        centerY += navbarGrid.getPrefHeight();
        return centerY;
    }

    /**
     * A fxml method that prevents the user from shutting down the create-event pop-up screen by consuming the click
     * @param event the event that the mousetrap protects
     */

    @FXML
    public void mouseTrapCreateEvent(Event event){
        event.consume();
    }

    /**
     * A fxml method that prevents the user from shutting down the edit-event pop-up screen by consuming the click
     * @param event the event that the mousetrap protects
     */

    @FXML
    public void mouseTrapEditEvent(Event event){
        event.consume();
    }

    /**
     * A fxml method that is called when the create-event pop-up shall be closed
     */

    @FXML
    public void closeCreateEvent() {
        resetAllField();
        creationPage.toBack();
    }

    /**
     * A fxml method that is called when the edit-event pop-up shall be closed
     */

    @FXML
    public void closeEditEvent() {
        editPage.toBack();
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
            habitView.visible();
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

    /**
     * Updates the TodoView, it is called from the actOnUpdate that is implemented from the Listener interface
     */
    private void updateTodoView(){
        todoView.updateTodoView();
    }

    /**
     * Updates the HabitView, it is called from the actOnUpdate that is implemented from the Listener interface
     */

    private void updateHabitView(){
        habitView.updateHabitView(facade.getAllHabitIds());
    }

    /**
     * A method that is implemented from the Listener interface, when something this class listens to notifies its
     * listeners this method will be called
     */

    @Override
    public void actOnUpdate() {
        updateTodoView();
        updateHabitView();
    }

    /**
     * A fxml method that is called when the button that enables the user to add events is called,
     * it resets the value of the datePicker, sets a temporary title to "New Event" and centers the popup
     */

    @FXML
    private void addButtonClick(){
        dateChooser.setValue(LocalDate.now());
        titleField.setText("New Event");
        displayPopUps(creationPage, eventCreatePane);
    }

    /**
     * Closes the pop-up where the user creates a new event and returns back to the previous screen
     */

    @FXML
    private void closeButtonClick(){
        resetAllField();
        creationPage.toBack();
    }

    /**
     * when the "create" button is clicked in the creation page of a new event is pressed, this method collects what
     * input the user has given and sends it to the facade where it creates this new event
     */

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

    /**
     * Calculates the center of the window to attach the pop-up at that center
     * @param bkgPane a pane to be centered
     * @param pane a pane to be centered
     */

    private void displayPopUps(AnchorPane bkgPane, AnchorPane pane) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = screenBounds.getWidth()/2;
        centerX -= (pane.getPrefWidth()/2);
        double centerY = screenBounds.getHeight()/2;
        centerY -= (pane.getPrefHeight()/2);
        pane.setLayoutX(centerX);
        pane.setLayoutY(centerY);
        bkgPane.toFront();
    }

    /**
     * Makes sure that input the user has given is correct, if it isn't it corrects it in order to make sure no errors
     * will occur
     */

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

    /**
     * Delegates all choiceboxes in the create/edit page to another function that gives them all needed information
     * to be used
     */

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

    /**
     * the method that sets up a given checkbox as either a hour or a minute box
     * @param c the checkbox that shall be set up
     * @param isHour a boolean that is true if the checkbox is made to show hours or false if the checkbox will
     *               show minutes
     */

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

    /**
     * Clears all the different javafx elements a user can edit so it is empty at next use
     */

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

    /**
     * An fxml method that opens the edit-page pop-up for events and populates that screen with the information
     * of the event that is to be edited
     * @param id the id of the event that is going to be edited
     */

    @FXML
    public void editEventPressed(int id){
        populateExtendedEvent(id);
        displayPopUps(editPage, eventEditPane);
    }

    /**
     * A fxml method that closes the edit-page pop-up and brings forward the previous the screen that was used
     * before the edit-page was opened
     */

    @FXML
    public void editClosePressed(){
        editPage.toBack();
    }

    /**
     * Saves the input the user has given in the edit screen by editing the event in the EventOrganizer eventList through
     * the facade interface
     */

    @FXML
    public void editSavePressed() {
        int fromHour = Integer.parseInt(editFromHourTime.getValue());
        int fromMinute = Integer.parseInt(editFromMinuteTime.getValue());
        int toHour = Integer.parseInt(editToHourTime.getValue());
        int toMinute = Integer.parseInt(editToMinuteTime.getValue());
        if (toHour > fromHour || toHour == fromHour && toMinute > fromMinute) {
            LocalDateTime from = LocalDateTime.of(editDate.getValue().getYear(), editDate.getValue().getMonth(), editDate.getValue().getDayOfMonth(), fromHour, fromMinute);
            LocalDateTime to = LocalDateTime.of(editDate.getValue().getYear(), editDate.getValue().getMonth(), editDate.getValue().getDayOfMonth(), toHour, toMinute);
            checkEditInput();
            facade.editEvent(Integer.valueOf(idLabel.getText()), editTitle.getText(), editLocation.getText(), editDesc.getText(), from, to);

        }
        updateCurrentView();
        editPage.toBack();
    }

    /**
     * Goes through all the input given by the user and edits necessary fields so that to not cause any errors when saving
     */

    private void checkEditInput(){
        if (editTitle.getText() == null || editTitle.getText().isEmpty()){
            editTitle.setText("Edited Event");
        }
        if (editLocation.getText() == null){
            editLocation.setText("");
        }
        if (editDesc.getText() == null){
            editDesc.setText("");
        }
    }

    /**
     * When the "delete-event" button in the edit page has been pressed by the user, this methods is called. This
     * closes the edit-page and deletes the event from the EventOrganizer list through the facade
     */

    @FXML
    private void deleteEventPressed(){
        editPage.toBack();
        facade.deleteEvent(Integer.valueOf(idLabel.getText()));
        updateCurrentView();
    }

    /**
     * When the edit page is to be opened this methods sets the values in the different javafx elements of the
     * corresponding values of the event that is to be edited
     * @param id the id of the event that is going to be edited
     */

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

    /**
     * Creates a new LocalDateTime copy of the masterDateTime to ensure that is not edited somewhere else, increasing
     * immutability
     */

    private LocalDateTime copyMasterdate(){
        return LocalDateTime.of(masterDateTime.getYear(), masterDateTime.getMonthValue(), masterDateTime.getDayOfMonth(), masterDateTime.getHour(), masterDateTime.getMinute());
    }

    @Override
    public void actOnUpdate(String msg) {
        int id = Integer.valueOf(msg);
        editEventPressed(id);
    }
}
