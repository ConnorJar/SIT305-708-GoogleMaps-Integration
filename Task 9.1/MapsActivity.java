package com.application.a1_sit305_91p;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsViewModel mapsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Advert> advertList = dbHelper.getAllAdverts();
        for (Advert advert : advertList) {
            mapsViewModel.addMarker(new MarkerOptions()
                .position(new LatLng(advert.getLatitude(), advert.getLongitude()))
                .title(advert.getName())
                .snippet(advert.getDescription()));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mapsViewModel.getMarkers().observe(this, markers -> {
            mMap.clear();
            for (MarkerOptions marker : markers) {
                mMap.addMarker(marker);
            }
            if (!markers.isEmpty()) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 10));
            }
        });
    }
}
