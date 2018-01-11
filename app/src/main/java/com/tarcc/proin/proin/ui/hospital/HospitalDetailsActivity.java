package com.tarcc.proin.proin.ui.hospital;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityHospitalDetailsBinding;
import com.tarcc.proin.proin.model.Hospital;
import com.tarcc.proin.proin.ui.BaseActivity;
import com.tarcc.proin.proin.util.PermissionUtil;


public class HospitalDetailsActivity extends BaseActivity {

    public static void start(Context context, Hospital hospital) {
        Intent starter = new Intent(context, HospitalDetailsActivity.class);
        starter.putExtra("hospital", hospital.toJson());
        context.startActivity(starter);
    }

    private ActivityHospitalDetailsBinding binding;
    private Hospital hospital;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hospital_details);
        hospital = Hospital.deserialize(getIntent().getStringExtra("hospital"));

        initToolbar();

        Glide.with(this)
                .load(hospital.getImageUrl())
                .into(binding.image);

        populateHospital();

        initializeMap();
    }


    private void populateHospital() {
        binding.name.setText(hospital.getName());
        binding.desc.setText(hospital.getDescription());
        binding.address.setText(hospital.getAddress());
        binding.hours.setText(hospital.getOperationHours());
    }

    private void initToolbar() {
        setSupportActionBar(binding.actionToolbar);
        binding.actionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(hospital.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }

    private GoogleMap googleMap;
    private SupportMapFragment mSupportMapFragment;
    private boolean requested = false;

    private void initializeMap() {

        mSupportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mSupportMapFragment).commit();
        }
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap1) {
                    if (googleMap1 != null) {
                        googleMap = googleMap1;
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        googleMap.getUiSettings().setCompassEnabled(false);
                        googleMap.getUiSettings().setMapToolbarEnabled(false);

                        if (ActivityCompat.checkSelfPermission(HospitalDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(HospitalDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            if (!requested) {
                                PermissionUtil.requestPermission(HospitalDetailsActivity.this, PermissionUtil.FINE_LOCATION);
                                requested = true;
                            }
                            return;
                        }

                        initLocation();

                    }
                }
            });
        }
    }

    private void initLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        final String address = hospital.getAddress();
        final double latitude = hospital.getLat();
        final double longitude = hospital.getLng();

        LatLng jobPosition = new LatLng(latitude, longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jobPosition,
                15));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(jobPosition)
                .title(hospital.getName())
                .icon(BitmapDescriptorFactory.defaultMarker());
        googleMap.addMarker(markerOptions);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().equals(hospital.getName())) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + String.valueOf(latitude) + "," + String.valueOf(longitude) + "(" + Uri.encode(address) + ")");
                    showMap(HospitalDetailsActivity.this, gmmIntentUri);
                }
                return true;
            }
        });
    }

    private void showMap(Context context, Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initLocation();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

}

