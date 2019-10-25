package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Facade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the graphical representation of a single in a week in the week view, in the application
 */
class DayEventListView extends StackPane {
    private VBox vBox;
    private List<HBox> hBoxList;
    private Line tl;
    private double hHeight;
    private double vBoxWidth;
    private LocalDateTime ldt;
    private Facade facade;

    DayEventListView(LocalDateTime ldt) {
        this.ldt = ldt;
        setUpDayView();
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares it to make it possible to just update the existing content in future
     */
    private void setUpDayView() {
        this.facade = new Facade();
        hBoxList = new ArrayList<>();
        vBox = new VBox();
        for (int i = 0; i < 10; i++) {
            hBoxList.add(new HBox());
        }
        for (int i = 0; i < 24; i++) {
            HourView hour = new HourView();
            vBox.getChildren().add(hour);
        }
        this.getChildren().add(vBox);
        for (HBox hBox : hBoxList) {
            this.getChildren().add(hBox);
            hBox.setPickOnBounds(false);
        }
        setUpTimeLine();
        vBox.setPickOnBounds(false);
    }

    /**
     * Is called from the constructor
     * Sets up the view and prepares the time line
     */
    private void setUpTimeLine() {
        tl = new Line(0, 0, 2000, 0);
        tl.setStroke(Color.valueOf("#e64e4e"));
        tl.setStrokeWidth(2);
        this.getChildren().add(tl);

        hHeight = new HourView().getPrefHeight();
    }

    /**
     * Updates a days containing events
     * @param ldt
     * @param vBoxWidth
     */
    void updateDay(LocalDateTime ldt, double vBoxWidth) {
        this.vBoxWidth = vBoxWidth;
        this.ldt = ldt;
        for (HBox hBox : hBoxList) {
            hBox.getChildren().clear();
        }
        int i = 0;
        int overlap = 0;
        for (int id : facade.getAllIdsOfDay(ldt))
            if (this.ldt.getDayOfYear() == ldt.getDayOfYear()) {
                i++;
                EventView eventView = new EventView((id));
                translateYEvent(eventView, id);
                overlap = translateXEvent(id, eventView, overlap);
                hBoxList.get(i - 1).getChildren().add(eventView);
            }
    }

    /**
     * Sets an events dimensions given an id and eventView object
     * @param eventView
     * @param id
     */
    private void translateYEvent(EventView eventView, int id){
        eventView.setTranslateY((facade.getEventStarttime(id).getHour() * 120) + (facade.getEventStarttime(id).getMinute()) * 2); //todo WTF
        double height = calculateLenght(id) * 2;
        eventView.setPrefHeight(height);
        double width = this.vBoxWidth/facade.calculateOverlaps(ldt,id);
        eventView.setPrefWidth((width));
    }

    /**
     * Checks how many overlaps a eventView has in the day view
     * @param id
     * @param eventView
     * @param overlap
     * @return
     */
    private int translateXEvent(int id, EventView eventView, int overlap){
        if (calculateTranslateX(id) != 0) {
            if (overlap == 3) {
                eventView.setTranslateX(calculateTranslateX(id) * 3);
            } else if (overlap == 2) {
                eventView.setTranslateX(calculateTranslateX(id) * 2);
            } else if (overlap == 1) {
                eventView.setTranslateX(calculateTranslateX(id));
            }
            overlap++;
        }
        return overlap;
    }

    /**
     * Updates the time lines position given a hour and minute count
     * @param hour
     * @param minute
     */
    void updateTimeline(int hour, int minute) {
        double timeHeight = 0;
        if (hour < 12) {
            timeHeight = (12 - hour) * hHeight;
            timeHeight -= (minute * (hHeight/60));
            timeHeight *= -1;
        } else {
            timeHeight = (hour - 12) * hHeight;
            timeHeight += (minute * (hHeight/60));
        }
        tl.setTranslateY(timeHeight);
    }

    /**
     * Calculates an events length given an id
     * @param id
     * @return
     */
    private double calculateLenght(int id) {
        Facade facade = new Facade();
        return facade.getLength(id);
    }

    /**
     * Calculates an events width given an id
     * @param id
     * @return
     */
    private double calculateTranslateX(int id) {
        Facade facade = new Facade();
        double x = vBoxWidth/facade.calculateOverlaps(ldt,id);
        if (x == vBoxWidth) {
            return 0;
        } else {
            return x;
        }
    }
}
