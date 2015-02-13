package com.star.testprovider;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestProviderFragment extends Fragment {

    private Button mStartStopButton;
    private TextView mStatusTextView;

    private TestProviderManager mTestProviderManager;

    private Location mLastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mTestProviderManager = TestProviderManager.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test_provider, container, false);

        mStartStopButton = (Button) v.findViewById(R.id.startStopButton);
        mStartStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTestProviderManager.isTestProviderOn()) {
                    mTestProviderManager.stopTestProviderLocationUpdates();
                } else {
                    mTestProviderManager.startTestProviderLocationUpdates();
                }

                updateUI();
            }
        });

        mStatusTextView = (TextView) v.findViewById(R.id.statusTextView);

        return v;
    }

    private void updateUI() {
        boolean started = mTestProviderManager.isTestProviderOn();

        if (started) {
            mStartStopButton.setText(R.string.stop);
        } else {
            mStartStopButton.setText(R.string.start);
        }

        if (started && mLastLocation != null) {
            String statusText = "Latitude: " + mLastLocation.getLatitude() + "\n" +
                            "Longitude: " + mLastLocation.getLongitude() + "\n" +
                            "Altitude: " + mLastLocation.getAltitude();
            mStatusTextView.setText(statusText);
        } else {
            mStatusTextView.setText("");
        }
    }

    private BroadcastReceiver mLocationReceiver = new LocationReceiver() {
        @Override
        protected void onLocationReceived(Context context, Location location) {
            super.onLocationReceived(context, location);

            mLastLocation = location;
            if (isVisible()) {
                updateUI();
            }
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            super.onProviderEnabledChanged(enabled);

            int toastText = enabled ? R.string.testProvider_enabled : R.string.testProvider_disabled;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mLocationReceiver, new IntentFilter(TestProviderManager.ACTION_LOCATION));
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
    }
}
