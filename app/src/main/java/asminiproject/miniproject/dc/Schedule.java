package asminiproject.miniproject.dc;

import android.util.Pair;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    @DocumentId
    public String documentId;
    public List<DocumentReference> pairs;

    public Schedule() { }
    public Schedule(String documentId_, List<DocumentReference> pairs_) {
        documentId = documentId_;
        pairs = pairs_;
    }

    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<DocumentReference> getPairs() {
        return pairs;
    }
    public void setPairs(List<DocumentReference> pairs) {
        this.pairs = pairs;
    }
}
