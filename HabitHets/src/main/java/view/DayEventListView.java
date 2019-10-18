package view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Day;
import model.Event;
import model.EventOrganizer;
import model.Interval;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DayEventListView extends StackPane {
    private VBox vBox;
    private List<HBox> hBoxList;
    private Line tl;
    private double hHeight;
    private double vBoxWidth;
    private Day day;

    public DayEventListView(Day day) {
        this.day = day;
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

    private void setUpTimeLine() {
        tl = new Line(0, 0, 2000, 0);
        tl.setStroke(Color.valueOf("#47bcad"));
        tl.setStrokeWidth(2);
        this.getChildren().add(tl);

        hHeight = new HourView().getPrefHeight();
    }

    public void updateDay(Day day, double vBoxWidth) {
        this.vBoxWidth = vBoxWidth;
        System.out.println(vBoxWidth);
        this.day = day;
        for (HBox hBox : hBoxList) {
            hBox.getChildren().clear();
        }
        int i = 0;
        int overlap = 0;
        for (Event event : EventOrganizer.getInstant().getEventsOfDay(day.getLdt())) {
            if (this.day.getLdt().getDayOfYear() == day.getLdt().getDayOfYear()) {
                i++;
                AnchorPane a = new EventView(event);
                a.setTranslateY((event.getStartTime().getHour() * 120) + event.getStartTime().getMinute()*2); //todo WTF
                double height = calculateLenght(event)*2;
                a.setPrefHeight(height);
                a.setPrefWidth(calculateWidth(event));
                if (calculateTranslateX(event) != 0) {
                    if (overlap == 3) {
                        a.setTranslateX(calculateTranslateX(event) * 3);
                    } else if (overlap == 2) {
                        a.setTranslateX(calculateTranslateX(event) * 2);
                    } else if (overlap == 1) {
                        a.setTranslateX(calculateTranslateX(event));
                    }
                    overlap++;
                }
                hBoxList.get(i - 1).getChildren().add(a);
            }
        }
    }

    public void updateTimeline(int hour, int minute) {
        double timeHeight = 0;
        if (hour < 12) {
            timeHeight = (12-hour)* hHeight;
            timeHeight -= hHeight-(minute*2);
            timeHeight *= -1;
        } else {
            timeHeight = (hour - 12)*hHeight;
            timeHeight += (minute*2);
        }
        tl.setTranslateY(timeHeight);
    }

    private double calculateLenght(Event event) {
        long valueMinute = ChronoUnit.MINUTES.between(event.getStartTime(),event.getEndTime());
        return (valueMinute);
    }

    private double calculateWidth(Event event) {
        double i = 0;
        List<Event> overlaps = new ArrayList<>();
        List<Event> overlaps2 = new ArrayList<>();
        List<Event> events = EventOrganizer.getInstant().getEventsOfDay(getDay().getLdt());
        Interval interval = new Interval(event.getStartTime(), true, event.getEndTime(), true);
        for (Event tmpEvent : events) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                i++;
                if (!overlaps.contains(tmpEvent) && !tmpEvent.equals(event)) {
                    overlaps.add(tmpEvent);
                }
            }
        }
        for (Event event1 : overlaps) {
            Interval interval1 = new Interval(event1.getStartTime(), true, event1.getEndTime(), true);
            for (Event event2 : events) {
                Interval interval2 = new Interval(event2.getStartTime(), true, event2.getEndTime(), true);
                if (interval1.overlaps(interval2) && event2 != event1 && event2 != event) {
                    i++;
                    if (!overlaps2.contains(event2) && !event2.equals(event1)) {
                        overlaps2.add(event2);
                    }
                }
            }
        }
        events.add(event);
        //double vboxWidth = getVboxWidth();
        return vBoxWidth / i;
    }

    private double calculateTranslateX(Event event) {
        double x = calculateWidth(event);
        if (x == vBoxWidth) {
            return 0;
        } else {
            return x;
        }
    }

    private double amountOfOverlaps(Event event) {
        int i = 0;
        Interval interval = new Interval(event.getStartTime(), true, event.getEndTime(), true);
        for (Event tmpEvent : EventOrganizer.getInstant().getEventsOfDay(getDay().getLdt())) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                i++;
            }
        }
        return i;
    }

    private Day getDay() {
        return day;
    }
}
