package main.view;

import java.time.LocalDateTime;
import java.util.List;

public interface ViewAble {
    void updateView(List<LocalDateTime> variable);
    void updateTimeLine(int hour, int minute);
}
