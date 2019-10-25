package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class OverlapsCounter {

    static double countOverlaps(LocalDateTime dateTime, int id) {

        double i = 0;
        List<Event> overlaps = new ArrayList<>();
        List<Event> overlaps2 = new ArrayList<>();
        List<Event> events = EventOrganizer.getInstance().getEventsOfDay(dateTime);
        Interval interval = new Interval(EventOrganizer.getEventOfId(id).getStartTime(), true, EventOrganizer.getEventOfId(id).getEndTime(), true);
        for (Event tmpEvent : events) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                i++;
                if (!overlaps.contains(tmpEvent) && !tmpEvent.equals(EventOrganizer.getEventOfId(id))) {
                    overlaps.add(tmpEvent);
                }
            }
        }
        for (Event event1 : overlaps) {
            Interval interval1 = new Interval(event1.getStartTime(), true, event1.getEndTime(), true);
            for (Event event2 : events) {
                Interval interval2 = new Interval(event2.getStartTime(), true, event2.getEndTime(), true);
                if (interval1.overlaps(interval2) && event2 != event1 && event2 != EventOrganizer.getEventOfId(id)) {
                    i++;
                    if (!overlaps2.contains(event2) && !event2.equals(event1)) {
                        overlaps2.add(event2);
                    }
                }
            }
        }
        events.add(EventOrganizer.getEventOfId(id));
        return i;
    }
}
