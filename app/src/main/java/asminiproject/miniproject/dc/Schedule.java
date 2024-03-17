package asminiproject.miniproject.dc;

import android.util.Pair;

public class Schedule {
    public Pair<TimeSlot, TimeSlot> monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    public Schedule() { }
    public Schedule(
            Pair<TimeSlot, TimeSlot> monday_,
            Pair<TimeSlot, TimeSlot> tuesday_,
            Pair<TimeSlot, TimeSlot> wednesday_,
            Pair<TimeSlot, TimeSlot> thursday_,
            Pair<TimeSlot, TimeSlot> friday_,
            Pair<TimeSlot, TimeSlot> saturday_,
            Pair<TimeSlot, TimeSlot> sunday_) {

        monday = monday_;
        tuesday = tuesday_;
        wednesday = wednesday_;
        thursday = thursday_;
        friday = friday_;
        saturday = saturday_;
        sunday = sunday_;
    }
}
