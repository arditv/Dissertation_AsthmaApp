package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se16.qmul.ac.uk.finalapplication.Model.MyPlaces;
import se16.qmul.ac.uk.finalapplication.Model.Results;
import se16.qmul.ac.uk.finalapplication.Remote.IGoogleAPIService;


//Inspiration from source https://www.youtube.com/watch?v=wKrYU97Wwg4


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final int MY_PERMISSION_CODE = 1000 ;
    private GoogleMap mMap;

    private double latitude, longitude;
    private Location mLastLocation;
    private Marker mMarker;

    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    private LocationRequest mLocationRequest;

    IGoogleAPIService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Init service

        mService = Common.getGoogleAPIService();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Here when you click, it will go to the nearByPlace method
                switch (menuItem.getItemId())
                {

                    case R.id.action_hospital:
                         nearByPlace("hospital");
                         break;

                    default:
                        break;

                }
                return true;
            }
        });

        //get your current location
        buildLocationCallBack();
        buildLocationRequest();

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this);




        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback,  Looper.myLooper());
    }




    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    private void buildLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(10f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLastLocation = locationResult.getLastLocation();

                if(mMarker != null)
                {
                    mMarker.remove();

                }

                longitude = mLastLocation.getLongitude();
                latitude = mLastLocation.getLatitude();

                LatLng latLng = new LatLng(latitude,longitude);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("Your position")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                mMarker = mMap.addMarker(markerOptions);

                //Move camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        };


    }


    //placeType is the parameter
    private void nearByPlace(String placeType) {
        mMap.clear();

        //Get the URL for your established latitude, longitude and placeType
        String url = getUrl(latitude,longitude,placeType);



        //Model was created to form the structure of the JSON response, when we recieve the results we run a for loop for the amount of results
        // and fetch the relevant details
        //Response is in the form of the model where the JSON objects have been turned into Java objects to allow for more familiar parsing.

        mService.getNearbyPlaces(url)
                .enqueue(new Callback<MyPlaces>() {
                    @Override
                    public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                        if (response.isSuccessful())
                        {
                            for (int i = 0; i<response.body().getResults().length; i++)
                            {
                                MarkerOptions markerOptions = new MarkerOptions();
                                Results googlePlace = response.body().getResults()[i];

                                //Fetch the lat and lng of the place
                                double lat = googlePlace.getGeometry().getLocation().getLat();
                                double lng = googlePlace.getGeometry().getLocation().getLng();


                                //Add to the map
                                String placeName = googlePlace.getName();
                                LatLng latLng = new LatLng(lat,lng);
                                markerOptions.position(latLng);


                                markerOptions.title(placeName);
                                if (placeName.equals("hospital"))
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                                else
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                                mMap.addMarker(markerOptions);
                                //Move Camera
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlaces> call, Throwable t) {

                    }
                });
    }

    private String getUrl(double latitude, double longitude, String placeType) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location="+latitude+","+longitude);
        googlePlacesUrl.append("&radius=20000");
        googlePlacesUrl.append("&type="+placeType);
        googlePlacesUrl.append("&key="+getResources().getString(R.string.browser_key));

        return googlePlacesUrl.toString();


    }

    private boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSION_CODE);
            else
                ActivityCompat.requestPermissions(this, new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSION_CODE);

            return false;

        }

        else
            return true;


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode)
        {
            case MY_PERMISSION_CODE:
            {
                if(grantResults.length >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {

                        mMap.setMyLocationEnabled(true);
                        buildLocationCallBack();
                        buildLocationRequest();

                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



                        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback,  Looper.myLooper());
                    }
                }

            }
            break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Init Google play service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {

                mMap.setMyLocationEnabled(true);

            }

        }

        else
            {

            mMap.setMyLocationEnabled(true);

        }

    }



}
