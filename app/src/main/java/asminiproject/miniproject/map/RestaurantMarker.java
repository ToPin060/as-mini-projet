package asminiproject.miniproject.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import asminiproject.miniproject.R;
import asminiproject.miniproject.dc.Restaurant;

public class RestaurantMarker extends Marker {
    private final Context _context;
    private final Restaurant _restaurant;

    @SuppressLint("UseCompatLoadingForDrawables")
    public RestaurantMarker(MapView mapView, Context context, Restaurant restaurant) {
        super(mapView);

        _context = context;
        _restaurant = restaurant;

        this.setTitle(restaurant.name);
        this.setPosition(restaurant.localization);
        this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        this.setIcon(_context.getDrawable(R.drawable.pin_green));
    }

    @Override
    public boolean onSingleTapConfirmed(final MotionEvent event, final MapView mapView) {
        if (hitTest(event, mapView)){
            onMarkerClickDefault(this, mapView);
            Log.d("RestaurantMarker", "Restaurant id: " + _restaurant.id);
            return true;
        }

        return false;
    }
}
