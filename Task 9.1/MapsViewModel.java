package com.application.a1_sit305_91p;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsViewModel extends ViewModel {
    private final MutableLiveData<LatLng> currentLocation = new MutableLiveData<>();
    private final MutableLiveData<List<MarkerOptions>> markers = new MutableLiveData<>(new ArrayList<>());

    public LiveData<LatLng> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LatLng location) {
        currentLocation.setValue(location);
    }

    public LiveData<List<MarkerOptions>> getMarkers() {
        return markers;
    }

    public void addMarker(MarkerOptions marker) {
        List<MarkerOptions> currentMarkers = markers.getValue();
        if (currentMarkers != null) {
            currentMarkers.add(marker);
            markers.setValue(currentMarkers);
        }
    }

    public void clearMarkers() {
        List<MarkerOptions> currentMarkers = markers.getValue();
        if (currentMarkers != null) {
            currentMarkers.clear();
            markers.setValue(currentMarkers);
        }
    }
}
