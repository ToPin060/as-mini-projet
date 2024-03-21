package asminiproject.miniproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import asminiproject.miniproject.R;
import asminiproject.miniproject.services.PairService;
import asminiproject.miniproject.services.RestaurantService;
import asminiproject.miniproject.services.ScheduleService;
import asminiproject.miniproject.services.TimeSlotService;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    public void initializeActivity() {
        setContentView(R.layout.activity_loading);

        loadTimeslots();
    }

    private void loadTimeslots() {
        TimeSlotService.getInstance().getAllTimeslots(data -> {
            loadPairs();
        });
    }
    private void loadPairs() {
        PairService.getInstance().getAllTimeslots(data -> {
            loadSchedule();
        });
    }
    private void loadSchedule() {
        ScheduleService.getInstance().getAllTimeslots(data -> {
            loadRestaurants();
        });
    }
    private void loadRestaurants() {
        RestaurantService.getInstance().getAllRestaurants(data -> {
            Intent intent = new Intent(LoadingActivity.this, MapActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }
}
