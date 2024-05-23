package com.application.a1_sit305_91p;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.gms.common.api.Status;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.Arrays;
import android.content.ContentValues;
import android.widget.Toast;

public class CreateAdvertActivity extends AppCompatActivity {
    private EditText locationEditText;
    private DatabaseHelper dbHelper;
    private LatLng selectedLatLng;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationEditText = findViewById(R.id.locationEditText);


        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
            getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                selectedLatLng = place.getLatLng();
                locationEditText.setText(place.getName());
            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });
    }

    public void saveAdvert(View view) {
        EditText typeEditText = findViewById(R.id.typeEditText);
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText dateEditText = findViewById(R.id.dateEditText);

        ContentValues values = new ContentValues();
        values.put("type", typeEditText.getText().toString());
        values.put("name", nameEditText.getText().toString());
        values.put("phone", phoneEditText.getText().toString());
        values.put("description", descriptionEditText.getText().toString());
        values.put("date", dateEditText.getText().toString());
        values.put("location", locationEditText.getText().toString());
        if (selectedLatLng != null) {
            values.put("latitude", selectedLatLng.latitude);
            values.put("longitude", selectedLatLng.longitude);
        }

        dbHelper.insertAdvert(values);
        finish();
    }

    public void getCurrentLocation(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        selectedLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        locationEditText.setText("Current Location");
                    }
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Failed to get current location", Toast.LENGTH_SHORT).show();
                });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation(null);
        }
    }

}
