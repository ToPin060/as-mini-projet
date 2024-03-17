package asminiproject.miniproject.dc;

public class TimeSlot {
    public int startH;
    public int startM;
    public int endH;
    public int endM;

    /*
        Carefull with the empty constructor !
        You are going to avoid the timeslot check..
     */
    public TimeSlot() { }

    public TimeSlot(int startH_, int startM_, int endH_, int endM_) {
        if (startH_ > endH_) throw new RuntimeException(
                "TimeSlot is invalid: ER1");
        if (startH_ == endH_ && startM_ >= endM_) throw new RuntimeException(
                "TimeSlot is invalid: ER2");

        startH = startH_;
        startM = startM_;
        endH = endH_;
        endM = endM_;
    }
}
