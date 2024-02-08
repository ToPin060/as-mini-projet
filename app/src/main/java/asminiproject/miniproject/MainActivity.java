package asminiproject.miniproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends Activity {
    private MapView _mapView = null;
    private GeoPoint _myLocation = new GeoPoint(43.6047, 1.4442);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        _mapView = (MapView) findViewById(R.id.map);
        _mapView.setTileSource(TileSourceFactory.MAPNIK);

        Marker myLocationMarker = new Marker(_mapView);
        myLocationMarker.setTitle("Your location");
        myLocationMarker.setPosition(_myLocation);
        _mapView.getOverlays().add(myLocationMarker);

        // Obtenez le contrôleur de carte et configurez le centrage et le niveau de zoom initiaux
        _mapView.getController().setZoom(15.0);
        _mapView.getController().setCenter(_myLocation); // Coordonnées pour Paris par exemple

        //CompassOverlay _compassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), _mapView);
        //_compassOverlay.enableCompass();
        //_mapView.getOverlays().add(_compassOverlay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _mapView.onDetach();
    }
}