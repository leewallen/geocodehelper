package com.codebytes.readers.google;

import com.codebytes.Coordinates;
import com.codebytes.IOptions;
import com.codebytes.readers.AbstractGeoCodeReader;
import com.codebytes.readers.IGeoCodeReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/4/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleGeoCodeReader extends AbstractGeoCodeReader implements IGeoCodeReader {

    private static String urlFormat = "http://maps.googleapis.com/maps/api/geocode/xml?address=%s&sensor=false";



//    http://maps.googleapis.com/maps/api/geocode/xml?address=space+needle&sensor=false
//
//    /GeocodeResponse/result/geometry/location
//    /GeocodeResponse/result/geometry/location/lat
//    /GeocodeResponse/result/geometry/location/lng
//    /GeocodeResponse/result/formatted_address

    public List<Coordinates> getGpsCoordinates(IOptions options) {
        Coordinates coords = null;

        if (options.hasOpt("-a")) {

            coords = getGpsCoordinatesForAddress(options.getOpt("-a"), urlFormat);

        } else if (options.hasOpt("-i") && options.hasOpt("-o")) {

        }


        return coords;
    }

}
