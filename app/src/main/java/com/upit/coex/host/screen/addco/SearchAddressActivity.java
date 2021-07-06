package com.upit.coex.host.screen.addco;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.viewmodel.searchaddress.SearchAddressActivityViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class SearchAddressActivity extends BaseActivity<SearchAddressActivityViewModel> implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        BaseInterfaceView,
        LifecycleObserver {
    private static final String TAG = "SearchAddressActivity";

    private Button btnSubmit;
    private ImageButton btnClose;
    private TextView tvAddress, tvName;
    private LinearLayout lnPlaceContent;

    private Double lat, lng;
    private String name, address;

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_googlemap);
        bindView();
        mViewModal = ViewModelProviders.of(this).get(SearchAddressActivityViewModel.class);
        checkPermission();
        initSearchBox();
        initMap();

    }

    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void setMap(GoogleMap googleMap) {
        L.d(TAG, "onMapReady!");
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.coex_map_style));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

        //Initializing googleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClientStart();
    }

    public void googleApiClientStart() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    public void initSearchBox() {

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), CommonConstants.GOOGLE_MAP_KEY);
        }
        PlacesClient placesClient = Places.createClient(this); // require create a place client connection
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                name = place.getName();
                address = place.getAddress();

                lnPlaceContent.setVisibility(View.VISIBLE);
                tvAddress.setText(address);
                tvName.setText(name);

                moveCamera(lat, lng);
                addMarker(lat, lng);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                L.d(TAG, "An error occurred: " + status);
            }
        });
    }

    private void submitDataSelectedPlace() {
        Intent data = new Intent(getApplication(), Step1.class);
        data.putExtra("nameAdd", name);
        data.putExtra("addressAdd", address);
        data.putExtra("l1", lng);
        data.putExtra("l2", lat);

        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void checkPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        mViewModal.requestPermission(permissions);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        L.d(TAG, "onMapReady");
        setMap(googleMap);
    }

    private void addMarker(Double lat, Double lng) {
        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lng));
        mMap.addMarker(markerOptions);
    }

    private void moveCamera(Double lat, Double lng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12));
    }

    private void getCurrentLocation() {
        L.d(TAG, "getCurrentLocation!");

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    //Getting longitude and latitude
                    Double longitude = location.getLongitude();
                    Double latitude = location.getLatitude();
                    L.d(TAG, "current location", latitude + "", longitude + "");
                    addMarker(latitude, longitude);
                    moveCamera(latitude, longitude);

                } else {

                }
            });
        } catch (SecurityException e) {

        }


    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        btnSubmit = (Button) findViewById(R.id.mapBtnSubmit);
        btnClose = (ImageButton) findViewById(R.id.mapBtnClose);
        tvAddress = (TextView) findViewById(R.id.mapTvAddress);
        tvName = (TextView) findViewById(R.id.mapTvName);
        lnPlaceContent = (LinearLayout) findViewById(R.id.lnPlaceContent);
        lnPlaceContent.setVisibility(View.GONE);

        btnClose.setOnClickListener(view -> {
            clearDataPlace();
            lnPlaceContent.setVisibility(View.GONE);
        });

        btnSubmit.setOnClickListener(view -> {
            submitDataSelectedPlace();
        });

    }

    private void clearDataPlace() {
        tvAddress.setText("");
        tvName.setText("");
        lng = 0d;
        lat = 0d;
    }

    //

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Toast.makeText(this, "back 1", Toast.LENGTH_SHORT).show();

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            Log.d("bao.nt", "------------------------------");
            setResult(Activity.RESULT_CANCELED);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
