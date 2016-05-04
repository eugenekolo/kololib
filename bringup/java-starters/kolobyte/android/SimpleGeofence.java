package kolobyte.android;

import com.google.android.gms.location.Geofence;

public class SimpleGeofence {
    private final String id;
    private final Double lat;
    private final Double lng;
    private final Float radius;
    private Long expirationDuration;
    private Integer transitionType;

    public SimpleGeofence(String id, Double lat, Double lng, Float radius,
                          Long expirationDuration, Integer transitionType) {
        this.id = id;
        this.lat = lat;
        this.lng = longitude;
        this.radius = radius;
        this.expirationDuration = expirationDuration;
        this.transitionType = transitionType;
    }

    public String getId() {
        return this.id;
    }
    public Double getLatitude() {
        return this.lat;
    }
    public Double getLongitude() {
        return this.lng;
    }
    public Float getRadius() {
        return this.radius;
    }
    public Long getExpirationDuration() {
        return this.expirationDuration;
    }
    public Integer getTransitionType() {
        return this.transitionType;
    }

    public Geofence toGeofence() {
        // Build a new Geofence object.
        return new Geofence.Builder()
                .setRequestId(id)
                .setTransitionTypes(transitionType)
                .setCircularRegion(lat, lng, radius)
                .setExpirationDuration(expirationDuration)
                .build();
    }

}
