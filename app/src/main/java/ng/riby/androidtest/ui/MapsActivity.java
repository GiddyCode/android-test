package ng.riby.androidtest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import ng.riby.androidtest.R;
import ng.riby.androidtest.directionHelpers.FetchURL;
import ng.riby.androidtest.directionHelpers.TaskLoadedCallback;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getDirection = findViewById(R.id.getDirections);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(getApplicationContext()).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });


        String oLat = getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE)
                .getString("mLatitude", "");
        double oldLatitude =Double.parseDouble(oLat);

        String oLng = getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE)
                .getString("mLongitude", "");
        double oldLongitude =Double.parseDouble(oLng);

        String newLat = getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE)
                .getString("Latitude", "");
        double newLatitude =Double.parseDouble(newLat);

        String newLng = getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE)
                .getString("Longitude", "");
        double newLongitude =Double.parseDouble(newLng);

        place1 = new MarkerOptions().position(new LatLng(oldLatitude, oldLongitude)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(newLatitude, newLongitude)).title("Location 2");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(place1);
        mMap.addMarker(place2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
