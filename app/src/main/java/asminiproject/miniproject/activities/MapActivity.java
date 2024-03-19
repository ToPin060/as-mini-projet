package asminiproject.miniproject.activities;

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

import asminiproject.miniproject.R;
import asminiproject.miniproject.map.Map;

public class MapActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private Context _context = null;
    private Map map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _context = getApplicationContext();

        requestPermissionsIfNecessary(
                new String[] {
                    // Not used actually
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    // WRITE_EXTERNAL_STORAGE is required in order to show the map
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                });

        Configuration.getInstance().load(_context, PreferenceManager.getDefaultSharedPreferences(_context));
        setContentView(R.layout.activity_map);

        initializeMap();
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

    private void initializeMap() {
        map = new Map(_context, (MapView) findViewById(R.id.map));

    }

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