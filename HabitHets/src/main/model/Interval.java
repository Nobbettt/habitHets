package main.model;

import java.time.LocalDateTime;

public class Interval {

    private final LocalDateTime start;
    private final LocalDateTime end;

    private final boolean inclusiveStart;
    private final boolean inclusiveEnd;

    Interval(LocalDateTime start, boolean inclusiveStart,
             LocalDateTime end, boolean inclusiveEnd) {

        this.start = start;
        this.end = end;

        this.inclusiveStart = inclusiveStart;
        this.inclusiveEnd = inclusiveEnd;
    }

    boolean overlaps(Interval other) {

        // intervals share at least one point in time
        if(    ( this.start.equals(other.getEnd())
                && this.inclusiveStart
                && other.isInclusiveEnd() )

                || ( this.end.equals(other.getStart())
                && this.inclusiveEnd
                && other.isInclusiveStart() )
        )
            return true;


        // intervals intersect
        if(    ( this.end.isAfter(other.getStart()) && this.start.isBefore(other.getStart()) )
                || ( other.getEnd().isAfter(this.start) && other.getStart().isBefore(this.start) )
        )
            return true;


        // this interval contains the other interval
        if(
                ( ( this.start.equals(other.getStart()) && other.isInclusiveStart() )
                        || this.start.isAfter(other.getStart()) )
                        &&
                        ( ( this.end.equals(other.getEnd()) && other.isInclusiveEnd() )
                                || this.end.isBefore(other.getEnd()) )
        )
            return true;


        // the other interval contains this interval
        return ((other.getStart().equals(this.start) && this.inclusiveStart)
                || other.getStart().isAfter(this.start))
                &&
                ((other.end.equals(this.end) && this.inclusiveEnd)
                        ||
                        other.getEnd().isBefore(this.end));


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