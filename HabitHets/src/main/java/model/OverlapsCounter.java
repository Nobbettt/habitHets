package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class that counts number of overlaps given an id that used to find event
 */
class OverlapsCounter {
    private static List<Event> overlaps;
    private static List<Event> overlaps2;
    private static Interval interval;
    private static double count;

    /**
     * Function calculates
     * @param dateTime
     * @param id
     * @return
     */
    static double countOverlaps(LocalDateTime dateTime, int id) {
        count = 0;
        overlaps = new ArrayList<>();
        overlaps2 = new ArrayList<>();
        interval = new Interval(EventOrganizer.getEventOfId(id).getStartTime(), true, EventOrganizer.getEventOfId(id).getEndTime(), true);

        List<Event> events = EventOrganizer.getInstance().getEventsOfDay(dateTime);

        firstCheck(events, id);
        secondCheck(events, id);

        events.add(EventOrganizer.getEventOfId(id));
        return count;
    }

    private static void firstCheck(List<Event> events, int id) {
        for (Event tmpEvent : events) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                count++;
                if (!overlaps.contains(tmpEvent) && !tmpEvent.equals(EventOrganizer.getEventOfId(id))) {
                    overlaps.add(tmpEvent);
                }
            }
        }
    }

    private static void secondCheck(List<Event> events, int id) {
        for (Event event1 : overlaps) {
            Interval interval1 = new Interval(event1.getStartTime(), true, event1.getEndTime(), true);
            for (Event event2 : events) {
                Interval interval2 = new Interval(event2.getStartTime(), true, event2.getEndTime(), true);
                if (interval1.overlaps(interval2) && event2 != event1 && event2 != EventOrganizer.getEventOfId(id)) {
                    count++;
                    if (!overlaps2.contains(event2) && !event2.equals(event1)) {
                        overlaps2.add(event2);
                    }
                }
            }
        }
    }
}
