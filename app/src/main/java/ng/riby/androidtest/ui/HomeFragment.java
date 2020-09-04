package ng.riby.androidtest.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ng.riby.androidtest.R;
import ng.riby.androidtest.tracker.DistanceTracker;


public class HomeFragment extends Fragment implements View.OnClickListener {


    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Double latitude, longitude;

    String bestProvider;

    @BindView(R.id.startBtn)
    Button startBtn;
    @BindView(R.id.stopBtn)
    Button stopBtn;
    @BindView(R.id.showMap)
    Button showMap;

    @BindView(R.id.TextQ)
    TextView TextQ;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };

    private void updateUI() {
        float distanceCovered = DistanceTracker.distanceCovered;
        TextQ.setText(String.format("Distance covered = %f meter(s)", distanceCovered));
    }


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, rootView);

        initListeners();
        checkPermissions();

        return rootView;
    }

    private void checkPermissions() {
        updateUI();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
//        else {
//           //TODO startTrackingService();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //TODO send toast Permission successfully granted
            // startTrackingService();
        }
    }

    private void startTrackingService() {
        Intent intent = new Intent(getContext(), DistanceTracker.class);
        getContext().startService(intent);
    }

    private void stopTrackingService() {
        Intent intent = new Intent(getContext(), DistanceTracker.class);
        getContext().stopService(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(DistanceTracker.UPDATE_UI);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }



    private void initListeners() {
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        showMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View clicked) {
        switch (clicked.getId()) {
            case R.id.startBtn:
                startTrackingService();
                    stopBtn.setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                //Do something after 1000ms
                //Toast.makeText(getContext(), "Please, start moving for at least 1 meter", Toast.LENGTH_LONG).show();
                handler.postDelayed(this::updateUI, 1000);
                break;

            case R.id.stopBtn:
                stopTrackingService();
                showMap.setVisibility(View.VISIBLE);
                TextQ.setVisibility(View.GONE);
                break;

            case R.id.showMap:
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_homeFragment_to_mapsActivity);

                break;
        }

    }
}
