package main.view;

import main.model.Day;

import java.util.List;

public interface ViewAble {
    public void updateView(List<Day> days);
    public void updateTimeLine(int hour, int minute);
}
