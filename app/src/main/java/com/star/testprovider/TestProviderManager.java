package com.star.testprovider;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

public class TestProviderManager {

    private static final String TAG = "TestProviderManager";

    public static final String ACTION_LOCATION =
            "com.star.testprovider.ACTION_LOCATION";

    private static final String TEST_PROVIDER = "TEST_PROVIDER";

    private static TestProviderManager sTestProviderManager;

    private Context mAppContext;

    private LocationManager mLocationManager;

    private List<Location> mLocationList;

    private TestProviderManager(Context appContext) {
        mAppContext = appContext;
        mLocationManager = (LocationManager)
                mAppContext.getSystemService(Context.LOCATION_SERVICE);

        initLocationData();
    }

    public static TestProviderManager getInstance(Context context) {
        if (sTestProviderManager == null) {
            synchronized (TestProviderManager.class) {
                if (sTestProviderManager == null) {
                    sTestProviderManager = new TestProviderManager(context);
                }
            }
        }
        return sTestProviderManager;
    }

    private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;

        return PendingIntent.getBroadcast(mAppContext, 0, broadcast, flags);
    }

    public void startTestProviderLocationUpdates() {
        if (isTestProviderOn()) {
            mLocationManager.removeTestProvider(TEST_PROVIDER);
        }

        mLocationManager.addTestProvider(TEST_PROVIDER, false, false, false, false,
                true, false, false, 0, 0);

        mLocationManager.setTestProviderEnabled(TEST_PROVIDER, true);
        mLocationManager.setTestProviderStatus(TEST_PROVIDER,
                GpsStatus.GPS_EVENT_STARTED, null, System.currentTimeMillis());

        TestProviderService.setServiceAlarm(mAppContext, true);

        PendingIntent pi = getLocationPendingIntent(true);

        mLocationManager.requestLocationUpdates(TEST_PROVIDER, 0, 0, pi);
    }

    public void stopTestProviderLocationUpdates() {

        PendingIntent pi = getLocationPendingIntent(false);

        if (pi != null) {
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }

        mLocationManager.setTestProviderEnabled(TEST_PROVIDER, false);

        mLocationManager.removeTestProvider(TEST_PROVIDER);

        TestProviderService.setServiceAlarm(mAppContext, false);
    }

    public boolean isTestProviderOn() {
        return mLocationManager.getProvider(TEST_PROVIDER) != null;
    }

    public void publishLocation(int i) {
        Location location = mLocationList.get(i % mLocationList.size());
        location.setTime(System.currentTimeMillis());
        mLocationManager.setTestProviderLocation(TEST_PROVIDER, location);
    }

    private Location buildLocation(double latitude, double longitude, double altitude) {
        Location location = new Location(TEST_PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(altitude);

        return location;
    }

    private void initLocationData() {
        mLocationList = new ArrayList<>();

        mLocationList.add(buildLocation(33.751459, -84.3238770, 309));
        mLocationList.add(buildLocation(33.7514752, -84.3238663, 304));
        mLocationList.add(buildLocation(33.7514859, -84.3238610, 303));
        mLocationList.add(buildLocation(33.7514752, -84.32385, 303));
        mLocationList.add(buildLocation(33.7514859, -84.3237590, 287));
        mLocationList.add(buildLocation(33.7514859, -84.3237376, 286));
        mLocationList.add(buildLocation(33.7514805, -84.3237537, 286));
        mLocationList.add(buildLocation(33.7514430, -84.3237537, 283));
        mLocationList.add(buildLocation(33.7514108, -84.3237483, 286));
        mLocationList.add(buildLocation(33.7514162, -84.3237590, 287));
        mLocationList.add(buildLocation(33.7514269, -84.3237751, 287));
        mLocationList.add(buildLocation(33.7514376, -84.3237859, 287));
        mLocationList.add(buildLocation(33.7514698, -84.3238180, 283));
        mLocationList.add(buildLocation(33.7514805, -84.3238288, 282));
        mLocationList.add(buildLocation(33.7515181, -84.323887, 280));
        mLocationList.add(buildLocation(33.7515234, -84.3238985, 279));
        mLocationList.add(buildLocation(33.7515342, -84.3239146, 279));
        mLocationList.add(buildLocation(33.7515449, -84.3239307, 279));
        mLocationList.add(buildLocation(33.7515503, -84.3239468, 280));
        mLocationList.add(buildLocation(33.7515556, -84.323957, 280));
        mLocationList.add(buildLocation(33.751566, -84.3239682, 280));
        mLocationList.add(buildLocation(33.7515717, -84.3239790, 280));
        mLocationList.add(buildLocation(33.7515825, -84.3239897, 280));
        mLocationList.add(buildLocation(33.75159323, -84.3240058, 280));
        mLocationList.add(buildLocation(33.75160932, -84.3240165, 280));
        mLocationList.add(buildLocation(33.75161468, -84.3240272, 280));
        mLocationList.add(buildLocation(33.7510514, -84.3247300, 277));
        mLocationList.add(buildLocation(33.751062, -84.3247246, 276));
        mLocationList.add(buildLocation(33.75108897, -84.3247032, 276));
        mLocationList.add(buildLocation(33.7510782, -84.3246924, 280));
        mLocationList.add(buildLocation(33.7510836, -84.3247032, 282));
        mLocationList.add(buildLocation(33.75109970, -84.324697, 281));
        mLocationList.add(buildLocation(33.75112652, -84.324697, 283));
        mLocationList.add(buildLocation(33.75115334, -84.3246924, 281));
        mLocationList.add(buildLocation(33.7511640, -84.3246871, 284));
        mLocationList.add(buildLocation(33.7511748, -84.3246871, 284));
        mLocationList.add(buildLocation(33.7511855, -84.3246871, 284));
        mLocationList.add(buildLocation(33.7511962, -84.3246871, 284));
        mLocationList.add(buildLocation(33.751206, -84.3246871, 285));
        mLocationList.add(buildLocation(33.7512177, -84.3246763, 286));
        mLocationList.add(buildLocation(33.7512284, -84.3246763, 287));
        mLocationList.add(buildLocation(33.7512391, -84.3246763, 288));
        mLocationList.add(buildLocation(33.7512499, -84.3246817, 287));
        mLocationList.add(buildLocation(33.75126, -84.3246763, 287));
        mLocationList.add(buildLocation(33.7512713, -84.3246763, 287));
        mLocationList.add(buildLocation(33.7512820, -84.3246710, 287));
        mLocationList.add(buildLocation(33.7512981, -84.3246710, 287));
        mLocationList.add(buildLocation(33.751314, -84.3246763, 286));
        mLocationList.add(buildLocation(33.7513303, -84.3246710, 286));
        mLocationList.add(buildLocation(33.75135183, -84.324660, 287));
        mLocationList.add(buildLocation(33.75136792, -84.3246495, 287));
        mLocationList.add(buildLocation(33.75137865, -84.3246495, 287));
        mLocationList.add(buildLocation(33.75140011, -84.3246388, 286));
        mLocationList.add(buildLocation(33.7514108, -84.3246281, 286));
        mLocationList.add(buildLocation(33.7514269, -84.3246227, 286));
        mLocationList.add(buildLocation(33.7514376, -84.3246120, 285));
        mLocationList.add(buildLocation(33.7514537, -84.324606, 285));
        mLocationList.add(buildLocation(33.7514644, -84.3246012, 285));
        mLocationList.add(buildLocation(33.7514752, -84.3245959, 285));
        mLocationList.add(buildLocation(33.7514805, -84.3245851, 285));
        mLocationList.add(buildLocation(33.7515020, -84.3245798, 286));
        mLocationList.add(buildLocation(33.751512, -84.3245744, 286));
        mLocationList.add(buildLocation(33.7515234, -84.3245637, 286));
        mLocationList.add(buildLocation(33.7515288, -84.324553, 287));
        mLocationList.add(buildLocation(33.7515395, -84.3245476, 287));
        mLocationList.add(buildLocation(33.7515503, -84.3245422, 287));
        mLocationList.add(buildLocation(33.751566, -84.3245315, 288));
        mLocationList.add(buildLocation(33.7515717, -84.3245208, 288));
        mLocationList.add(buildLocation(33.7515825, -84.3245154, 288));
        mLocationList.add(buildLocation(33.75158786, -84.324499, 290));
        mLocationList.add(buildLocation(33.75160396, -84.3244940, 290));
        mLocationList.add(buildLocation(33.75161468, -84.3244832, 291));
        mLocationList.add(buildLocation(33.75162541, -84.3244725, 292));
        mLocationList.add(buildLocation(33.75164151, -84.324461, 292));
        mLocationList.add(buildLocation(33.75165224, -84.324445, 293));
        mLocationList.add(buildLocation(33.7516629, -84.3244349, 293));
        mLocationList.add(buildLocation(33.7516736, -84.3244242, 294));
        mLocationList.add(buildLocation(33.7516790, -84.3244135, 294));
        mLocationList.add(buildLocation(33.7516897, -84.3244028, 294));
        mLocationList.add(buildLocation(33.7516897, -84.3243813, 294));
        mLocationList.add(buildLocation(33.7516844, -84.3243598, 295));
        mLocationList.add(buildLocation(33.7516790, -84.3243438, 295));
        mLocationList.add(buildLocation(33.7516844, -84.3243277, 295));
        mLocationList.add(buildLocation(33.7516790, -84.3243062, 295));
        mLocationList.add(buildLocation(33.7516736, -84.3242955, 296));
        mLocationList.add(buildLocation(33.7516683, -84.3242794, 297));
        mLocationList.add(buildLocation(33.7516629, -84.3242686, 296));
        mLocationList.add(buildLocation(33.75165224, -84.3242526, 297));
        mLocationList.add(buildLocation(33.75164151, -84.3242365, 297));
        mLocationList.add(buildLocation(33.75162541, -84.3242204, 297));
        mLocationList.add(buildLocation(33.75162005, -84.3242043, 297));
        mLocationList.add(buildLocation(33.75161468, -84.3241882, 298));
        mLocationList.add(buildLocation(33.75160396, -84.3241775, 299));
        mLocationList.add(buildLocation(33.75159859, -84.3241667, 298));
        mLocationList.add(buildLocation(33.75159323, -84.324156, 299));
        mLocationList.add(buildLocation(33.7515771, -84.3241453, 299));
        mLocationList.add(buildLocation(33.751566, -84.3241345, 298));
        mLocationList.add(buildLocation(33.7515556, -84.3241184, 298));
        mLocationList.add(buildLocation(33.751566, -84.3241077, 297));
        mLocationList.add(buildLocation(33.7515556, -84.3240863, 296));
        mLocationList.add(buildLocation(33.7515503, -84.3240702, 296));
        mLocationList.add(buildLocation(33.7515449, -84.3240541, 296));
        mLocationList.add(buildLocation(33.7515288, -84.3240380, 297));
        mLocationList.add(buildLocation(33.7515234, -84.3240272, 297));
        mLocationList.add(buildLocation(33.7515181, -84.3240058, 297));
        mLocationList.add(buildLocation(33.7515074, -84.323995, 297));
        mLocationList.add(buildLocation(33.7515020, -84.3239790, 296));
        mLocationList.add(buildLocation(33.7514913, -84.3239629, 295));
        mLocationList.add(buildLocation(33.7514805, -84.3239521, 294));
        mLocationList.add(buildLocation(33.7514644, -84.323941, 294));
        mLocationList.add(buildLocation(33.7514537, -84.3239307, 293));
        mLocationList.add(buildLocation(33.7514483, -84.3239146, 294));
        mLocationList.add(buildLocation(33.7514483, -84.3238985, 293));
        mLocationList.add(buildLocation(33.7514483, -84.3238770, 293));
        mLocationList.add(buildLocation(33.7514483, -84.32385, 292));
        mLocationList.add(buildLocation(33.7514537, -84.3238395, 291));
        mLocationList.add(buildLocation(33.751459, -84.3238234, 291));
        mLocationList.add(buildLocation(33.7514698, -84.3238019, 291));
        mLocationList.add(buildLocation(33.7514805, -84.3237859, 291));
        mLocationList.add(buildLocation(33.7514859, -84.3237751, 291));
        mLocationList.add(buildLocation(33.7514913, -84.3237644, 291));
        mLocationList.add(buildLocation(33.7514913, -84.3237483, 290));
        mLocationList.add(buildLocation(33.7515020, -84.323742, 290));
        mLocationList.add(buildLocation(33.7515074, -84.3237322, 290));
        mLocationList.add(buildLocation(33.7515181, -84.3237268, 290));
        mLocationList.add(buildLocation(33.7515288, -84.3237161, 290));
        mLocationList.add(buildLocation(33.7515342, -84.323705, 290));
        mLocationList.add(buildLocation(33.7515181, -84.3237000, 290));
        mLocationList.add(buildLocation(33.7515074, -84.3237161, 290));
        mLocationList.add(buildLocation(33.7514966, -84.3237376, 290));
        mLocationList.add(buildLocation(33.7514913, -84.3237537, 290));
        mLocationList.add(buildLocation(33.7514859, -84.3237751, 290));
        mLocationList.add(buildLocation(33.7514966, -84.3237751, 290));
        mLocationList.add(buildLocation(33.7515020, -84.323796, 290));
        mLocationList.add(buildLocation(33.751512, -84.323796, 290));
        mLocationList.add(buildLocation(33.7515234, -84.3238127, 289));
        mLocationList.add(buildLocation(33.7514966, -84.3237912, 289));
        mLocationList.add(buildLocation(33.7514859, -84.3237805, 289));
        mLocationList.add(buildLocation(33.751459, -84.3237698, 289));
        mLocationList.add(buildLocation(33.7514537, -84.3237805, 289));
        mLocationList.add(buildLocation(33.7514430, -84.3237751, 290));
    }

}
