package asminiproject.miniproject.map;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;

import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import asminiproject.miniproject.R;
import asminiproject.miniproject.activities.RestaurantProfileActivity;
import asminiproject.miniproject.dc.Restaurant;

public class RestaurantMarker extends Marker {
    private final Restaurant _restaurant;

    private final Context _context;


    public RestaurantMarker(MapView mapView, Context context, Restaurant restaurant) {
        super(mapView);

        _context = context;
        _restaurant = restaurant;

        this.setTitle(restaurant.name);
        this.setPosition(restaurant.localization);
        this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        this.setIcon(ResourcesCompat.getDrawable(_context.getResources(), R.drawable.pin_red, null));
    }


    @Override
    public boolean onSingleTapConfirmed(final MotionEvent event, final MapView mapView) {
        if (!hitTest(event, mapView)) return false;

        Intent restaurantDescriptionIntent = new Intent(_context, RestaurantProfileActivity.class);
        restaurantDescriptionIntent.putExtra("RESTAURANT_ID", _restaurant.id);
        restaurantDescriptionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(restaurantDescriptionIntent);
        return true;
    }
}
