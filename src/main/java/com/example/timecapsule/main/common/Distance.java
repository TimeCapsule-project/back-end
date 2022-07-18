package com.example.timecapsule.main.common;

import com.example.timecapsule.capsule.dto.request.LocationRequest;
import com.example.timecapsule.capsule.entity.CapsuleInfo;

public class Distance {
    private static final double METER = 1609.344;
    private static final double OPENABLE_DISTANCE = 300.0;

    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        return (dist * 60 * 1.1515 * METER);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static boolean isOpenable(LocationRequest locationRequest, CapsuleInfo capsuleInfo) {
        double distance = distance(
                locationRequest.getLongitude(), locationRequest.getLatitude(),
                capsuleInfo.getLocation().x, capsuleInfo.getLocation().y
        );
        return distance <= OPENABLE_DISTANCE ? true : false;
    }
}
