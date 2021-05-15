package com.example.reclaimportugal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,GoogleMap.InfoWindowAdapter,
LocationListener{


    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    private boolean getPlaces = true;
    String regionId;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    public void addGameLocations(){
        String nomes[];
        double capitalCities[][];
        capitalCities = new double[29][2];
        nomes = new String[29];
        nomes[0] = "Ponte de Lima";
        nomes[1] = "Livraria Lello";
        nomes[2] = "Ponte D.Luis";
        nomes[3] = "Templo de Diana";
        nomes[4] = "Capela dos Ossos";
        nomes[5] = "Barragem do Alqueva";
        nomes[6] = "Praia da Rocha";
        nomes[7] = "Igreja de São Lourenço";
        nomes[8] = "Praça do Rossio";
        nomes[9] = "Terreiro do Paço";
        nomes[10] = "Castelo de S. Jorge";
        nomes[11] = "Floresta Laurissilva";
        nomes[12] = "Museu CR7";
        nomes[13] = "Santana";
        nomes[14] = "Parque Arq. do Vale do Côa";
        nomes[15] = "Capela do Bonfim";
        nomes[16] = "Museu do Pão";
        nomes[17] = "Lagoa das Sete Cidades";
        nomes[18] = "Montanha do Pico";
        nomes[19] = "Igreja de Santo Cristo";
        nomes[20] = "Pateira dos Fermentelos";
        nomes[21] = "Mosteiro de Lorvão";
        nomes[22] = "Torre de Almedina";
        nomes[23] = "Sé de Faro";
        nomes[24] = "Torre de Belém";
        nomes[25] = "Palácio da Pena";
        nomes[26] = "Castelo de Bragança";
        nomes[27] = "Igreja da Senhora dos Remédios";
        nomes[28] = "Estação do Pinhão";

        capitalCities[0][0] = 41.7637524931393;//
        capitalCities[0][1] = (-8.589993813859234);

        capitalCities[1][0] = 41.14703474446451;
        capitalCities[1][1] = -8.61477460180499;

        capitalCities[2][0] = 41.14014471758315;
        capitalCities[2][1] = -8.60943777296951;

        capitalCities[3][0] = 38.5728042734598;
        capitalCities[3][1] = -7.907433876722188;

        capitalCities[4][0] = 38.568579560813774;
        capitalCities[4][1] = -7.908645828844928;

        capitalCities[5][0] = 38.197554785191514;
        capitalCities[5][1] = -7.496410459537539;

        capitalCities[6][0] = 37.11673007884954;
        capitalCities[6][1] = -8.535344320119568;

        capitalCities[7][0] = 37.082312938082225;
        capitalCities[7][1] =  -8.0088932;

        capitalCities[8][0] = 38.714081094946344;
        capitalCities[8][1] = -9.139072459527188;

        capitalCities[9][0] = 38.708428697918784;
        capitalCities[9][1] = -9.136842540690846;

        capitalCities[10][0] = 38.7141100948647;
        capitalCities[10][1] = -9.133476201855908;

        capitalCities[11][0] = 32.75913486940427;
        capitalCities[11][1] = -16.995696963337092;

        capitalCities[12][0] = 32.644307766899324;
        capitalCities[12][1] =  -16.91381144284069;

        capitalCities[13][0] = 32.80554976700321;
        capitalCities[13][1] = -16.882202318439322;

        capitalCities[14][0] = 41.080178292199214;
        capitalCities[14][1] = -7.111765468987108;

        capitalCities[15][0] = 40.53387742079487;
        capitalCities[15][1] = -7.2637344711377185;

        capitalCities[16][0] = 40.417870130178706;
        capitalCities[16][1] = -7.694508722234035;

        capitalCities[17][0] = 37.85500349093831;
        capitalCities[17][1] = -25.78639168242758;

        capitalCities[18][0] = 38.47005435579639;
        capitalCities[18][1] = -28.400446300396716;

        capitalCities[19][0] = 37.738125272215946;
        capitalCities[19][1] = -25.672312566548776;

        capitalCities[20][0] = 40.57562644316587;
        capitalCities[20][1] = -8.516297485280292;

        capitalCities[21][0] = 40.25957240314976;
        capitalCities[21][1] = -8.317455046017383;

        capitalCities[22][0] = 40.208877844034944;
        capitalCities[22][1] = -8.428692222434636;

        capitalCities[23][0] = 37.013410682991584;
        capitalCities[23][1] = -7.934959659560814;

        capitalCities[24][0] = 38.691709290917856;
        capitalCities[24][1] = -9.215934386932748;

        capitalCities[25][0] = 38.78772724782098;
        capitalCities[25][1] = -9.390565986929763;

        capitalCities[26][0] = 41.80442424240158;
        capitalCities[26][1] = -6.749262417519032;

        capitalCities[27][0] = 41.099261626499136;
        capitalCities[27][1] = -7.779031598939698;

        capitalCities[28][0] = 41.19066275323418;
        capitalCities[28][1] = -7.544881911985705;


        for(int i = 0;i<29;i++) {
            LatLng latLng = new LatLng(capitalCities[i][0], capitalCities[i][1]);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

            markerOptions.title(nomes[i]);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.portugalm));
            mMap.addMarker(markerOptions);

            getNearbyLocations(capitalCities[i][0], capitalCities[i][1]);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        addGameLocations();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i  = new Intent(com.example.reclaimportugal.MapsActivity.this, CustomWindowInfo.class);
                i.putExtra("Title", marker.getTitle());
                i.putExtra("Snippet", marker.getSnippet());
                startActivity(i);
                return true;
            }
        });


        String regionIDGame = getIntent().getStringExtra("regionIDGame");
        String placeIDGame = getIntent().getStringExtra("placeIDGame");
        //Toast.makeText(getApplicationContext(), String.valueOf(id) + String.valueOf(i), Toast.LENGTH_LONG).show();
        regionId = regionIDGame;
        selectLocation(regionIDGame+placeIDGame);
    }

    public void selectLocation(String nome){
        switch (nome)
        {
            case "00":
                goToLocation(41.7637524931393, -8.589993813859234);
                break;
            case "01":
                goToLocation(41.14703474446451, -8.61477460180499);
                break;
            case "02":
                goToLocation(41.14014471758315, -8.60943777296951);
                break;
            case "10":
                goToLocation(38.5728042734598, -7.907433876722188);
                break;
            case "11":
                goToLocation(38.568579560813774, -7.908645828844928);
                break;
            case "12":
                goToLocation(38.197554785191514, -7.496410459537539);
                break;
            case "20":
                goToLocation(37.11673007884954, -8.535344320119568);
                break;
            case "21":
                goToLocation(37.013410682991584, -7.934959659560814);
                break;
            case "22":
                goToLocation(37.082312938082225,  -8.0088932);
                break;
            case "40":
                goToLocation(38.714081094946344, -9.139072459527188);
                break;
            case "41":
                goToLocation(38.708428697918784, -9.136842540690846);
                break;
            case "42":
                goToLocation(38.7141100948647, -9.133476201855908);
                break;
            case "70":
                goToLocation(32.75913486940427, -16.995696963337092);
                break;
            case "71":
                goToLocation(32.644307766899324, -16.91381144284069);
                break;
            case "72":
                goToLocation(32.80554976700321, -16.882202318439322);
                break;
            case "32":
                goToLocation(41.080178292199214, -7.111765468987108);
                break;
            case "30":
                goToLocation(40.53387742079487, -7.2637344711377185);
                break;
            case "31":
                goToLocation(40.417870130178706, -7.694508722234035);
                break;
            case "61":
                goToLocation(37.85500349093831, -25.78639168242758);
                break;
            case "62":
                goToLocation(38.47005435579639, -28.400446300396716);
                break;
            case "60":
                goToLocation(37.73811969065019, -25.672538421867);
                break;
            case "80":
                goToLocation(40.57562644316587, -8.516297485280292);
                break;
            case "82":
                goToLocation(40.25957240314976, -8.317455046017383);
                break;
            case "81":
                goToLocation(40.208877844034944, -8.428692222434636);
                break;
            case "50":
                goToLocation(38.691709290917856, -9.215945115768635);
                break;
            case "51":
                goToLocation(38.78773561064938, -9.390565986929763);
                break;
            case "52":
                goToLocation(38.7141100948647, -9.133476201855908);
                break;
            case "90":
                goToLocation(41.804432239938485, -6.749004925457739);
                break;
            case "91":
                goToLocation(41.099261626499136, -7.779031598939698);
                break;
            case "92":
                goToLocation(41.190614311090584, -7.5446673352679605);
                break;
            default:
               goToLocation(38.57285310168407, -7.907405607131212);
                break;
        }
    }




    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();
    }

    public void updateLocation(Location location){
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_rapazm));
        currentLocationmMarker = mMap.addMarker(markerOptions);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;


        if(currentLocationmMarker != null)
        {
           currentLocationmMarker.remove();
        }

        updateLocation(location);

        if(getPlaces)
        {
            getNearbyLocations(latitude, longitude);
            getPlaces=false;
        }


    }
    public void goToLocation(double Latitude,double Longitude)
    {
        LatLng latLng = new LatLng(Latitude,Longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
    }

    public void getNearbyLocations(double Latitude,double Longitude)
    {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        String url = getUrl(Latitude, Longitude, "tourist_attraction");
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

        getNearbyPlacesData.execute(dataTransfer);
        //Toast.makeText(MapsActivity.this, "Showing Custom Nearby", Toast.LENGTH_SHORT).show();
    }



    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getString(R.string.google_maps_key));

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else {
            return true;
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void goToMenu(View v){
        Intent intent = new Intent(com.example.reclaimportugal.MapsActivity.this, RegionDetails.class);
        intent.putExtra("regionID", Integer.parseInt(regionId));
        startActivity(intent);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    //private View prepareInfoView(Marker marker) {

    //}
}
