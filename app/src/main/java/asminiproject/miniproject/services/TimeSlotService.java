package asminiproject.miniproject.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asminiproject.miniproject.dc.TimeSlot;
import asminiproject.miniproject.misc.SimpleCallback;

public class TimeSlotService {
    private static final String TAG = "TimeSlotService";
    private static final String COLLECTION_PATH = "TimeSlot";
    private final FirebaseFirestore _database;

    private Map<String, TimeSlot> _cache;

    public void getAllTimeslots(SimpleCallback<List<TimeSlot>> callback) {

        // Query cached ?
        if (_cache != null) {
            callback.onDataUpdate(new ArrayList<>(_cache.values()));
            return;
        }

        Map<String, TimeSlot> timeslot = new HashMap<>();
        CollectionReference collectionReferenceRestaurant = _database.collection(COLLECTION_PATH);
        collectionReferenceRestaurant
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().forEach(document -> {
                            timeslot.put(
                                document.getId(),
                                document.toObject(TimeSlot.class));
                        });
                    }

                    // Save the result on cache
                    _cache = timeslot;

                    callback.onDataUpdate(new ArrayList<>(_cache.values()));
                }})
            .addOnFailureListener(e -> Log.e(TAG, "An error occurred while fetching all the restaurants."));
    }

    public TimeSlot getTimeslotById(String id) {
        return _cache.get(id);
    }

    /*
        Singleton properties
     */
    private static TimeSlotService _instance;
    private TimeSlotService() {
        _instance = this;
        _database = FirebaseFirestore.getInstance();
    }
    public static TimeSlotService getInstance() {
        if (TimeSlotService._instance == null) {
            new TimeSlotService();
        }

        return _instance;
    }
}
