package asminiproject.miniproject.map;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
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

        initialization();
    }


    private void initialization() {
        _mapView.setTileSource(TileSourceFactory.MAPNIK);

        //DEPRECATED: _mapView.setBuiltInZoomControls(true);
        _mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        _mapView.setMultiTouchControls(true);

        // Place the device location marker
        generateCurrentLocationMarker();

        _mapView.getController().setCenter(_myLocation);
        _mapView.getController().setZoom(_mapZoom);

        generateRestaurantMarkers();

        CompassOverlay _compassOverlay = new CompassOverlay(_context, new InternalCompassOrientationProvider(_context), _mapView);
        _compassOverlay.enableCompass();
        _mapView.getOverlays().add(_compassOverlay);
    }


    private void generateCurrentLocationMarker() {
        Marker marker = new Marker(_mapView);

        marker.setTitle("Your location");
        marker.setPosition((GeoPoint) _myLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setIcon(ResourcesCompat.getDrawable(_context.getResources(), R.drawable.pin_red, null));


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
