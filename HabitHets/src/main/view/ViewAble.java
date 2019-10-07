package main.view;

import main.model.CalendarAble;

import java.util.List;

public interface ViewAble {
    public void updateView(List<? extends CalendarAble> variable);
    public void updateTimeLine(int hour, int minute);
}
