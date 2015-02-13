package com.star.testprovider;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TestProviderFragment extends Fragment {

    private Button mStartStopButton;
    private TextView mStatusTextView;

    private RunManager mRunManager;

    private Location mLastLocation;

    private RunManager.Listener mListener = new RunManager.Listener() {
        @Override
        public void onLocationReceived(Context context, Location location) {
            mLastLocation = location;
            updateUI();
        }

        @Override
        public void onProviderEnabledChanged(boolean enabled) {
            updateUI();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mRunManager = RunManager.getInstance(getActivity());

        mRunManager.addListener(mListener);
    }

    @Override
    public void onDestroy() {
        mRunManager.removeListener(mListener);

        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test_provider, container, false);

        mStartStopButton = (Button) v.findViewById(R.id.startStopButton);
        mStartStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRunManager.isTestProviderOn()) {
                    mRunManager.stopTestProviderLocationUpdates();
                } else {
                    mRunManager.startTestProviderLocationUpdates();
                }
            }
        });

        mStatusTextView = (TextView) v.findViewById(R.id.statusTextView);

        updateUI();

        return v;
    }

    private void updateUI() {
        boolean started = mRunManager.isTestProviderOn();

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
}
