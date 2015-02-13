package com.star.testprovider;


import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TestProviderService extends IntentService {

    private static final String TAG = "TestProviderService";

    private static final int UPDATE_INTERVAL = 1000;

    private static int sRunIndex = 0;

    public TestProviderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        TestProviderManager testProviderManager = TestProviderManager.getInstance(TestProviderService.this);
        testProviderManager.publishLocation(sRunIndex++);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = new Intent(context, TestProviderService.class);

        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
                    UPDATE_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
}
