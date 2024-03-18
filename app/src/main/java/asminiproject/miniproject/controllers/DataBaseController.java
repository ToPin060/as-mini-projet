package asminiproject.miniproject.controllers;

import android.util.Pair;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.dc.Schedule;
import asminiproject.miniproject.dc.TimeSlot;

public class DataBaseController {
    private static DataBaseController _instance;
    private DataBaseController() {
        _instance = this;
    }

    public List<Restaurant> getRestaurants() {
        // TODO: Make a DB query

        // Convert as Restaurant from DC
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        for (int i = 0; i < 10; i++) {
            GeoPoint localization = new GeoPoint(
                    43.6047 + (Math.random() - 0.5) * 0.01,
                    1.4442 + (Math.random() - 0.5) * 0.01);

            Restaurant restaurant = new Restaurant(
                    i, "Restaurant " + i,
                    "7 Rue Aristide Berges, 31270 Cugnaux", localization,
                    "05 61 59 32 80",
                    new Schedule(
                            new Pair<>(new TimeSlot(11, 00, 15, 00), null),
                            new Pair<>(new TimeSlot(11, 00, 15, 00), null),
                            new Pair<>(new TimeSlot(11, 00, 15, 00), null),
                            new Pair<>(new TimeSlot(11, 00, 15, 00), null),
                            new Pair<>(new TimeSlot(11, 00, 15, 00), null),
                            new Pair<>(
                                    new TimeSlot(11, 00, 15, 00),
                                    new TimeSlot(19, 00, 23, 30)),
                            new Pair<>(new TimeSlot(19, 00, 23, 30), null)
                    ));

            restaurants.add(restaurant);
        }

        return restaurants;
    }

    public static DataBaseController getDataBaseController() {
        if (DataBaseController._instance == null) {
            new DataBaseController();
        }
        return _instance;
    }
}