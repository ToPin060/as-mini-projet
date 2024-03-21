package asminiproject.miniproject.dc;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    /*
        Basic attributes
     */
    @DocumentId
    public String documentId;
    public String name;
    public String address;
    public GeoPoint localization;
    public String phone;
    public DocumentReference schedule;
    public List<DocumentReference> reviews;

    /*
        Cumputed attributes
     */
    public float overallRating = 0;
    public int ratingsNumber = 0;

    public Restaurant() { }
    public Restaurant(String id_, String name_, String address_, GeoPoint localization_, String phone_, DocumentReference schedule_) {
        documentId = id_;
        name = name_;
        address = address_;
        localization = localization_;
        phone = phone_;
        schedule = schedule_;
        reviews = new ArrayList<>();

    }
    public Restaurant(String id_, String name_, String address_, GeoPoint localization_, String phone_, DocumentReference schedule_, List<DocumentReference> ratings_) {
        documentId = id_;
        name = name_;
        address = address_;
        phone = phone_;
        schedule = schedule_;
        localization = localization_;
        reviews = ratings_;
    }

    /*
        GETTERS | SETTERS
     */
    public String getDocumentId() {
        return this.documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPoint getLocalization() {
        return localization;
    }
    public void setLocalization(GeoPoint localization) {
        this.localization = localization;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DocumentReference getSchedule() {
        return schedule;
    }
    public void setSchedule(DocumentReference schedule) {
        this.schedule = schedule;
    }

    public List<DocumentReference> getReviews() {
        return reviews;
    }
    public void setReviews(List<DocumentReference> reviews) {
        this.reviews = reviews;
    }
}
