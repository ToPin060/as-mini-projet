package asminiproject.miniproject.dc;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends ArrayList<Pair<TimeSlot, TimeSlot>> {
    public Schedule() { }
    public Schedule(List<Pair<TimeSlot, TimeSlot>> items) {
        super();

        // TODO: Checks values and intervals
        this.addAll(items);
    }
}
