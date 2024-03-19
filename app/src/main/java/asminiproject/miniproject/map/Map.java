package asminiproject.miniproject.map;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import java.util.List;

import asminiproject.miniproject.R;
import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.services.RestaurantsService;

public class Map {
    private final Context _context;
    private final MapView _mapView;

    private final RestaurantsService _restaurantsService;

    private IGeoPoint _myLocation = new GeoPoint(43.6047, 1.4442);
    private double _mapZoom = 18.0;


    public Map(Context context, MapView mapView) {
        _context = context;
        _mapView = mapView;
        _restaurantsService = RestaurantsService.getInstance();

        setupMap();
    }


    private void setupMap() {
        generateCurrentLocationMarker();
        generateRestaurantMarkers();

        _mapView.setTileSource(TileSourceFactory.MAPNIK);
        _mapView.getController().setCenter(_myLocation);
        _mapView.getController().setZoom(_mapZoom);
        _mapView.setMultiTouchControls(true);
    }


    private void generateCurrentLocationMarker() {
        Marker marker = new Marker(_mapView);

        marker.setTitle("Your location");
        marker.setPosition((GeoPoint) _myLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setIcon(ResourcesCompat.getDrawable(_context.getResources(), R.drawable.pin_green, null));

        _mapView.getOverlays().add(marker);
    }

    private void generateRestaurantMarkers() {
        List<Restaurant> restaurants = _restaurantsService.getRestaurants();

        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            _mapView.getOverlays().add(new RestaurantMarker(_mapView, _context, restaurant));
        }
    }


    public MapView getMapView() {
        return _mapView;
    }

    public GeoPoint getMyLocation() {
        return (GeoPoint) _myLocation;
    }
    public void setMyLocation(GeoPoint myLocation) { _myLocation = myLocation; }

    public double getMapZoom() { return _mapZoom; }
    public void setMapZoom(double mapZoom) { _mapZoom = mapZoom; }
}
