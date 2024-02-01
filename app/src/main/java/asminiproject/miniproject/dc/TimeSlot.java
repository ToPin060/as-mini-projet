package asminiproject.miniproject.dc;

import com.google.firebase.firestore.DocumentId;

public class TimeSlot {
    @DocumentId
    public String documentId;
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

    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getStartH() {
        return startH;
    }
    public void setStartH(int startH) {
        this.startH = startH;
    }

    public int getStartM() {
        return startM;
    }
    public void setStartM(int startM) {
        this.startM = startM;
    }

    public int getEndH() {
        return endH;
    }
    public void setEndH(int endH) {
        this.endH = endH;
    }

    public int getEndM() {
        return endM;
    }
    public void setEndM(int endM) {
        this.endM = endM;
    }
}
