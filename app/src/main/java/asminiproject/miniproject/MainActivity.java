package asminiproject.miniproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

import asminiproject.miniproject.map.Map;
import asminiproject.miniproject.services.RestaurantsService;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private Context _context = null;
    private RestaurantsService _restaurantsService = null;
    private Map map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissionsIfNecessary(new String[]{
                // Not used actually
                Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        _context = getApplicationContext();
        Configuration.getInstance().load(_context, PreferenceManager.getDefaultSharedPreferences(_context));
        setContentView(R.layout.activity_main);

        _restaurantsService = new RestaurantsService();

        map = new Map(_context, (MapView) findViewById(R.id.map), _restaurantsService);
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toArray(new String[0]),
                REQUEST_PERMISSIONS_REQUEST_CODE
            );
        }
    }
    
    /**
     * App life cycle override
     */
    @Override
    protected void onPause() {
        super.onPause();
        map.getMapView().onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        map.getMapView().onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.getMapView().onDetach();
    }
}