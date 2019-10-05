package main.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.model.Day;

import java.util.ArrayList;
import java.util.List;

public class DayEventListView extends StackPane {
    private VBox vBox;
    private Line tl;
    private List<HourView> hours;
    private double hHeight;
    public double timeHeight;

    public DayEventListView() {
        hours = new ArrayList<>();
        vBox = new VBox();
        for(int i = 0; i < 24; i++) {
            HourView hour = new HourView();
            vBox.getChildren().add(hour);
            hours.add(hour);
        }
        this.getChildren().add(vBox);
        setUpTimeLine();
    }

    private void setUpTimeLine() {
        tl = new Line(0, 0, 2000, 0);
        tl.setStroke(Color.valueOf("#47bcad"));
        tl.setStrokeWidth(2);
        this.getChildren().add(tl);
        hHeight = hours.get(0).getPrefHeight();
    }

    public void updateDay(Day day) {

    }

    public void updateTimeline(int hour, int minute) {
        double m =  60/minute;
        if(hour < 12) {
            timeHeight = 12 - hour;
            timeHeight -= (1/m);
            timeHeight *= -1;
        } else {
            timeHeight = hour - 12;
            timeHeight += 1/m;
        }
        System.out.println(timeHeight);
        timeHeight = timeHeight*hHeight;
        timeHeight -= (tl.getStrokeWidth() / 2);
        tl.setTranslateY(timeHeight);
    }
}
