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

import asminiproject.miniproject.dc.Schedule;
import asminiproject.miniproject.misc.SimpleCallback;

public class ScheduleService {
    private static final String TAG = "TimeSlotService";
    private static final String COLLECTION_PATH = "Schedule";
    private final FirebaseFirestore _database;

    private Map<String, Schedule> _cache;

    public void getAllTimeslots(SimpleCallback<List<Schedule>> callback) {

        // Query cached ?
        if (_cache != null) {
            callback.onDataUpdate(new ArrayList<>(_cache.values()));
            return;
        }

        Map<String, Schedule> timeslot = new HashMap<>();
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
                                document.toObject(Schedule.class));
                        });
                    }

                    // Save the result on cache
                    _cache = timeslot;

                    callback.onDataUpdate(new ArrayList<>(_cache.values()));
                }})
            .addOnFailureListener(e -> Log.e(TAG, "An error occurred while fetching all the restaurants."));
    }
    public Schedule getScheduleById(String id) {
        return _cache.get(id);
    }
    public Schedule getRestaurantById(String id) {
        if (_cache.get(id) == null) throw new RuntimeException();
        return _cache.get(id);
    }

    /*
        Singleton properties
     */
    private static ScheduleService _instance;
    private ScheduleService() {
        _instance = this;
        _database = FirebaseFirestore.getInstance();
    }
    public static ScheduleService getInstance() {
        if (ScheduleService._instance == null) {
            new ScheduleService();
        }

        return _instance;
    }
}
