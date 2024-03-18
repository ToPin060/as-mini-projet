package asminiproject.miniproject.dc;

import android.util.Pair;

import java.util.List;

public class Schedule {
    public List<Pair<TimeSlot, TimeSlot>> schedules;

    public Schedule() { }
    public Schedule(List<Pair<TimeSlot, TimeSlot>> schedules_) {
        if (schedules_.size() != 7) throw new RuntimeException();
        schedules = schedules_;
    }
}
