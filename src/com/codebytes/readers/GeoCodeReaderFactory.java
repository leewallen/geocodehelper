package com.codebytes.readers;

import com.codebytes.IOptions;
import com.codebytes.Options;
import com.codebytes.readers.google.GoogleGeoCodeReader;
import com.codebytes.readers.yahoo.YahooGeoCodeReader;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/4/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeoCodeReaderFactory implements IGeoCodeReaderFactory {

    private final static String GOOGLE = "developers.google.com";
    private final static String YAHOO = "where.yahooapis.com";

    private static IGeoCodeReaderFactory _instance = null;
    public static IGeoCodeReaderFactory getInstance() {
        if (_instance == null){
            _instance = new GeoCodeReaderFactory();
        }
        return _instance;
    }

    public IGeoCodeReader makeReader(GeoCodeApiEnum geoCodeApiEnum, IOptions options) {

        IGeoCodeReader geoCodeReader = null;

        if (geoCodeApiEnum == GeoCodeApiEnum.GOOGLE) {
            geoCodeReader = new GoogleGeoCodeReader();
        }
        else if (geoCodeApiEnum == GeoCodeApiEnum.YAHOO) {
            geoCodeReader = new YahooGeoCodeReader();
        }
        else {
            geoCodeReader = new GoogleGeoCodeReader();
        }

        return geoCodeReader;
    }
}
