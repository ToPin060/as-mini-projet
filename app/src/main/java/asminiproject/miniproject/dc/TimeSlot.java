package asminiproject.miniproject.dc;

public class TimeSlot {
    public int startH;
    public int startM;
    public int endH;
    public int endM;

    public TimeSlot() { }

    public TimeSlot(int startH_, int startM_, int endH_, int endM_) {
        // TODO: Checks values and intervals
        startH = startH_;
        startM = startM_;
        endH = endH_;
        endM = endM_;
    }
}
