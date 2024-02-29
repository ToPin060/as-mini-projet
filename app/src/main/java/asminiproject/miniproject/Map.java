package asminiproject.miniproject;

import android.annotation.SuppressLint;
import android.content.Context;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import java.util.ArrayList;
import java.util.List;

public class Map {
    /**
     * Essential attributes
     */
    private final Context _context;
    private final MapView _mapView;

    /**
     * Configuration attributes
     */
    private final IGeoPoint _myLocation = new GeoPoint(43.6047, 1.4442);
    private final double _mapZoom = 18.0;

    public Map(Context context, MapView mapView) {
        _context = context;
        _mapView = mapView;

        initialisation();
    }

    private void initialisation() {
        _mapView.setTileSource(TileSourceFactory.MAPNIK);

        //DEPRECATED: _mapView.setBuiltInZoomControls(true);
        _mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        _mapView.setMultiTouchControls(true);

        // Place the device location marker
        _mapView.getOverlays().add(getDeviceLocationMarker());

        _mapView.getController().setCenter(_myLocation);
        _mapView.getController().setZoom(_mapZoom);

        // Restaurants
        List<Marker> restaurantMarkers = restaurantMarkerOverlay();
        for (Marker marker: restaurantMarkers)
            _mapView.getOverlays().add(marker);

        CompassOverlay _compassOverlay = new CompassOverlay(_context, new InternalCompassOrientationProvider(_context), _mapView);
        _compassOverlay.enableCompass();
        _mapView.getOverlays().add(_compassOverlay);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Marker getDeviceLocationMarker() {
        Marker marker = new Marker(_mapView);
        marker.setPosition((GeoPoint) _myLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setTitle("Your location");
        marker.setIcon(_context.getDrawable(R.drawable.pin_red));

        return marker;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private List<Marker> restaurantMarkerOverlay() {
        List<Marker> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Marker marker = new Marker(_mapView);
            marker.setPosition((GeoPoint) new GeoPoint(
                    _myLocation.getLatitude() + (Math.random() - 0.5) * 0.01,
                    _myLocation.getLongitude() + (Math.random() - 0.5) * 0.01));
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            marker.setTitle("Point #" + i);
            marker.setIcon(_context.getDrawable(R.drawable.pin_green));
            points.add(marker);
        }

        return points;
    }

    public MapView getMapView() {
        return _mapView;
    }
    public GeoPoint getMyLocation() {
        return (GeoPoint) _myLocation;
    }
}
