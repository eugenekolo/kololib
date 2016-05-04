package kolobyte.android;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Util {
    // onTouchEvent function adapted from http://stackoverflow.com/questions/6645537/how-to-detect-the-swipe-left-or-right-in-android
    public static void onSwipeChangeScreen(MotionEvent event, Context ctx,
                                           final Class<? extends Activity> dstLeft,
                                           final Class<? extends Activity> dstRight) {
        Integer MIN_DISTANCE = 150;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventX1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                eventX2 = event.getX();

                float deltaX = eventX2 - eventX1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (eventX2 < eventX1) { //if left swipe, go to Activity before
                        ctx.startActivity(new Intent(ctx, dstLeft));
                    }
                    if (eventX2 > eventX1) { //if right swipe, go to Activity after
                        ctx.startActivity(new Intent(ctx, dstRight));
                    }
                }
                break;
        }
    }

    public static class Accelerometer {
        private static SensorManager sensorManager;
        private static Class<? extends Activity> nextScreen;
        private static SensorEventListener sensorEventListener;

        public Accelerometer(SensorManager sensorManager) {
            this.sensorManager = sensorManager;
        }

        // enable listening for accelerometer events
        public void enableAccelerometerListening(SensorEventListener sensorEventListener) {
            this.sensorEventListener = sensorEventListener;
            sensorManager.registerListener(sensorEventListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }


        // disable listening for accelerometer events
        public void disableAccelerometerListening() {
            sensorManager.unregisterListener(sensorEventListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        }
    }

    /**
     * Checks if Google Play services is available.
     *
     * @return true if it is.
     * @source https://github.com/googlesamples/android-Geofencing/blob/master/Application/src/main/java/com/example/android/wearable/geofencing/MainActivity.java
     */
    public static boolean isGooglePlayServicesAvailable(Context ctx) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Google Play services is available.");
            }
            return true;
        } else {
            Log.e(TAG, "Google Play services is unavailable.");
            return false;
        }
    }

    public static void toast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }


    public static void sendSimpleNotification(Context ctx, String title, String text) {
        // Get what number notification this is per activity
        SharedPreferences prefs = ctx.getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        int notificationNumber = prefs.getInt("notificationNumber", 0);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        builder.setColor(Color.DKGRAY)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_media_route_on_mono_dark)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationNumber, builder.build());

        // Update the notification number
        notificationNumber++;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("notificationNumber", notificationNumber);
        editor.commit();
    }
}
