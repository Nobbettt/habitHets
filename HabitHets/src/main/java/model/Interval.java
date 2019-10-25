package model;

import java.time.LocalDateTime;

/**
 * This function is used by OverlapsCounter and checks if events overlap in a day
 */
class Interval {

    private final LocalDateTime start;
    private final LocalDateTime end;

    private final boolean inclusiveStart;
    private final boolean inclusiveEnd;

    Interval(LocalDateTime start, boolean inclusiveStart, LocalDateTime end, boolean inclusiveEnd) {
        this.start = start;
        this.end = end;
        this.inclusiveStart = inclusiveStart;
        this.inclusiveEnd = inclusiveEnd;
    }

    /**
     * Checks if localDateTimes overlap
     * Returns true or false
     * @param other
     * @return
     */
    boolean overlaps(Interval other) {
        if ((this.start.equals(other.getEnd()) && this.inclusiveStart && other.isInclusiveEnd()) || (this.end.equals(other.getStart()) && this.inclusiveEnd && other.isInclusiveStart())) {
            return true;
        }
        if ((this.end.isAfter(other.getStart()) && this.start.isBefore(other.getStart())) || (other.getEnd().isAfter(this.start) && other.getStart().isBefore(this.start))) {
            return true;
        }
        if (((this.start.equals(other.getStart()) && other.isInclusiveStart()) || this.start.isAfter(other.getStart())) && ((this.end.equals(other.getEnd()) && other.isInclusiveEnd()) || this.end.isBefore(other.getEnd()))) {
            return true;
        }
        if((other.getStart().equals(this.start) && this.inclusiveStart) || other.getStart().isAfter(this.start) && ((other.end.equals(this.end) && this.inclusiveEnd) || other.getEnd().isBefore(this.end))){
            return true;
        }

        return false;
    }

    private LocalDateTime getStart() {
        return start;
    }

    private LocalDateTime getEnd() {
        return end;
    }

    private boolean isInclusiveStart() {
        return inclusiveStart;
    }

    private boolean isInclusiveEnd() {
        return inclusiveEnd;
    }
}