package com.codebytes.readers.google;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/5/13
 * Time: 12:09 AM
 * To change this template use File | Settings | File Templates.
 */
public enum GoogleErrorEnum {
    OK ("OK"),
    ZERO_RESULTS ("ZERO_RESULTS"),
    OVER_QUERY_LIMIT ("OVER_QUERY_LIMIT"),
    REQUEST_DENIED ("REQUEST_DENIED"),
    INVALID_REQUEST ("INVALID_REQUEST");

    private String status;

    GoogleErrorEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
//
//    "OK" indicates that no errors occurred; the address was successfully parsed and at least one geocode was returned.
//            "ZERO_RESULTS" indicates that the geocode was successful but returned no results. This may occur if the geocode was passed a non-existent address or a latlng in a remote location.
//    "OVER_QUERY_LIMIT" indicates that you are over your quota.
//    "REQUEST_DENIED" indicates that your request was denied, generally because of lack of a sensor parameter.
//    "INVALID_REQUEST" generally indicates that the query (address or latlng) is missing.
}
