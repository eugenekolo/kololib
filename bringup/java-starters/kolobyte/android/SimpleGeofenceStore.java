package kolobyte.android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Storage for geofence values, implemented in SharedPreferences.
 */
public class SimpleGeofenceStore {
    // Keys for flattened geofences stored in SharedPreferences.
    public static final String KEY_PREFIX = "GEOFENCE_KEY";
    public static final String KEY_LATITUDE = "GEOFENCE_KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "GEOFENCE_KEY_LONGITUDE";
    public static final String KEY_RADIUS = "GEOFENCE_KEY_RADIUS";
    public static final String KEY_EXPIRATION_DURATION = "GEOFENCE_KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION_TYPE = "GEOFENCE_KEY_TRANSITION_TYPE";

    // Invalid values, used to test geofence storage when retrieving geofences.
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

    private final SharedPreferences mPrefs;
    private static final String SHARED_PREFERENCES = "SimpleGeofences";

    public SimpleGeofenceStore(Context context) {
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Returns a stored geofence by its id, or returns null if it's not found.
     * @param id The ID of a stored geofence.
     * @return A SimpleGeofence defined by its center and radius, or null if the ID is invalid.
     */
    public SimpleGeofence getGeofence(String id) {
        // Get the latitude for the geofence identified by id, or INVALID_FLOAT_VALUE if it doesn't
        // exist (similarly for the other values that follow).
        double lat = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_LATITUDE), INVALID_FLOAT_VALUE);
        double lng = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_LONGITUDE), INVALID_FLOAT_VALUE);
        float radius = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_RADIUS), INVALID_FLOAT_VALUE);
        long expirationDuration = mPrefs.getLong(getGeofenceFieldKey(id, KEY_EXPIRATION_DURATION), INVALID_LONG_VALUE);
        int transitionType = mPrefs.getInt(getGeofenceFieldKey(id, KEY_TRANSITION_TYPE), INVALID_INT_VALUE);

        // If none of the values is incorrect, return the object.
        if (lat != INVALID_FLOAT_VALUE
                && lng != INVALID_FLOAT_VALUE
                && radius != INVALID_FLOAT_VALUE
                && expirationDuration != INVALID_LONG_VALUE
                && transitionType != INVALID_INT_VALUE) {
            return new SimpleGeofence(id, lat, lng, radius, expirationDuration, transitionType);
        }

        return null;
    }

    public void setGeofence(String id, SimpleGeofence geofence) {
        SharedPreferences.Editor prefs = mPrefs.edit();

        prefs.putFloat(getGeofenceFieldKey(id, KEY_LATITUDE), (float) geofence.getLatitude());
        prefs.putFloat(getGeofenceFieldKey(id, KEY_LONGITUDE), (float) geofence.getLongitude());
        prefs.putFloat(getGeofenceFieldKey(id, KEY_RADIUS), geofence.getRadius());
        prefs.putLong(getGeofenceFieldKey(id, KEY_EXPIRATION_DURATION), geofence.getExpirationDuration());
        prefs.putInt(getGeofenceFieldKey(id, KEY_TRANSITION_TYPE), geofence.getTransitionType());
        // Commit the changes.
        prefs.commit();
    }

    public void clearGeofence(String id) {
        SharedPreferences.Editor prefs = mPrefs.edit();
        prefs.remove(getGeofenceFieldKey(id, KEY_LATITUDE));
        prefs.remove(getGeofenceFieldKey(id, KEY_LONGITUDE));
        prefs.remove(getGeofenceFieldKey(id, KEY_RADIUS));
        prefs.remove(getGeofenceFieldKey(id, KEY_EXPIRATION_DURATION));
        prefs.remove(getGeofenceFieldKey(id, KEY_TRANSITION_TYPE));
        prefs.commit();
    }

    private String getGeofenceFieldKey(String id, String fieldName) {
        return KEY_PREFIX + "_" + id + "_" + fieldName;
    }

}
