package main.view;

import main.model.CalendarAble;

import java.util.List;

public interface ViewAble {
    void updateView(List<? extends CalendarAble> variable);
    void updateTimeLine(int hour, int minute);
}
