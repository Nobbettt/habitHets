package view;

import java.time.LocalDateTime;

public interface ViewAble {
    void updateView(LocalDateTime variable);
    void updateTimeLine(int hour, int minute);
}
