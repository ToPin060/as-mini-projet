package asminiproject.miniproject.dc;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

public class Pair {
    @DocumentId
    public String documentId;
    public DocumentReference first;
    public DocumentReference second;

    public Pair() {}

    public Pair(String documentId_, DocumentReference first_, DocumentReference second_) {
        documentId = documentId_;
        first = first_;
        second = second_;
    }

    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public DocumentReference getFirst() {
        return first;
    }
    public void setFirst(DocumentReference first) {
        this.first = first;
    }

    public DocumentReference getSecond() {
        return second;
    }
    public void setSecond(DocumentReference second) {
        this.second = second;
    }
}
