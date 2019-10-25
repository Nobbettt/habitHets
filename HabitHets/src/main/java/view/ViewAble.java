package view;

import java.time.LocalDateTime;

/**
 * is used to group the major/ parent views that need to be updated in the calender on actions from window
 */
public interface ViewAble {
    /**
     * Is used to update the views given a LocalDateTime
     */
    void updateView(LocalDateTime variable);

    /**
     * Is used to update the timeLine (that is used in week and expanded day view) Given a hour and minute
     * @param hour
     * @param minute
     */
    void updateTimeLine(int hour, int minute);
}
