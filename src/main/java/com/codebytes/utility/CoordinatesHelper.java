package com.codebytes.utility;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lwallen
 * Date: 1/18/13
 * Time: 8:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoordinatesHelper {

    //TODO: Add code for calculating distance.  Add code to allow command line option to know to print out distance and total distance.

    /*

     Look for calculation here: http://www.movable-type.co.uk/scripts/latlong.html

     * Distance

     This uses the ‘haversine’ formula to calculate the great-circle distance between two points – that is, the shortest distance over the earth’s surface – giving an ‘as-the-crow-flies’ distance between the points (ignoring any hills, of course!).

     Haversine
     formula:	 a = sin²(Δφ/2) + cos(φ1).cos(φ2).sin²(Δλ/2)
     c = 2.atan2(√a, √(1−a))
     d = R.c
     where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km)
     note that angles need to be in radians to pass to trig functions!
     JavaScript:
     var R = 6371; // km
     var dLat = (lat2-lat1).toRad();
     var dLon = (lon2-lon1).toRad();
     var lat1 = lat1.toRad();
     var lat2 = lat2.toRad();

     var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
     Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
     var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
     var d = R * c;
     The haversine formula1 ‘remains particularly well-conditioned for numerical computation even at small distances’ – unlike
     calculations based on the spherical law of cosines. The ‘versed sine’ is 1-cosθ, and the
     ‘half-versed-sine’ (1-cosθ)/2 = sin²(θ/2) as used above. It was published by Roger Sinnott in Sky & Telescope magazine
     in 1984 (“Virtues of the Haversine”), though known about for much longer by navigators. (For the curious, c is the angular distance in
     radians, and a is the square of half the chord length between the points). A (surprisingly marginal) performance improvement can be
     obtained, of course, by factoring out the terms which get squared.

     Spherical Law of Cosines

     In fact, when Sinnott published the haversine formula, computational precision was limited. Nowadays, JavaScript
     (and most modern computers & languages) use IEEE 754 64-bit floating-point numbers, which provide 15 significant figures of precision.
     With this precision, the simple spherical law of cosines formula (cos c = cos a cos b + sin a sin b cos C) gives well-conditioned results
     down to distances as small as around 1 metre. (Note that the geodetic form of the law of cosines is rearranged from the canonical one so
     that the latitude can be used directly, rather than the colatitude).

     This makes the simpler law of cosines a reasonable 1-line alternative to the haversine formula for many purposes.
     The choice may be driven by coding context, available trig functions (in different languages), etc.

     Spherical
     law of cosines:	d = acos( sin(φ1).sin(φ2) + cos(φ1).cos(φ2).cos(Δλ) ).R
     JavaScript:
     var R = 6371; // km
     var d = Math.acos(Math.sin(lat1)*Math.sin(lat2) +
     Math.cos(lat1)*Math.cos(lat2) *
     Math.cos(lon2-lon1)) * R;
     Excel:	=ACOS(SIN(lat1)*SIN(lat2)+COS(lat1)*COS(lat2)*COS(lon2-lon1))*6371

     */


    private final static int RadiusInKilometers = 6371;
    private final static double MilesPerKilometer = 0.621371;
    private final static double DegreesToRadians = Math.PI / 180;

    public static double calculateDistanceInKilometers(Coordinates[] coordinates, int currentPosition) {
        double distance = 0.0;
        if (coordinates.length > currentPosition + 1) {
            double lat1 = coordinates[currentPosition].getLatitude();
            double lat2 = coordinates[currentPosition + 1].getLatitude();
            double lon1 = coordinates[currentPosition].getLongitude();
            double lon2 = coordinates[currentPosition + 1].getLongitude();


            double dlon = (lon2 - lon1) * DegreesToRadians;
            double dlat = (lat2 - lat1) * DegreesToRadians;
            double a =
                    Math.pow(Math.sin(dlat / 2.0), 2)
                            + Math.cos(lat1 * DegreesToRadians)
                            * Math.cos(lat2 * DegreesToRadians)
                            * Math.pow(Math.sin(dlon / 2.0), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            distance = RadiusInKilometers * c;

            distance += calculateDistanceInKilometers(coordinates, currentPosition+ 1);
        }
        return distance;
    }

    public static double convertKilometersToMiles(double kilometers) {
        return kilometers * MilesPerKilometer;
    }
}
