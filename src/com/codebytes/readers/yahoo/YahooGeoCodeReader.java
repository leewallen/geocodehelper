package com.codebytes.readers.yahoo;

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
import java.io.*;
import java.util.List;

public class YahooGeoCodeReader extends AbstractGeoCodeReader implements IGeoCodeReader {

    private final static String urlFormat = "http://where.yahooapis.com/geocode?appid=%s&q=%s";
    private final static String appId = "00ff0ece4e3ca671a610241bc3c1ad0500c32bf7";

    private final static String statusXpathString = "/ResultSet/Error/text()";
    private final static String latitudeXpathString = "/ResultSet/Result/latitude/text()";
    private final static String longitudeXpathString = "/ResultSet/Result/longitude/text()";

    private String address = null;
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String makeTargetUrl(String urlFormat, String address) {
        return String.format(urlFormat, appId, address.replaceAll(" ", "+"));
    }

    public List<Coordinates> getGpsCoordinates(IOptions options) {

        if (options.hasOpt("-a")) {
            return getGpsCoordinatesFromAddress(options);
        } else if (options.hasOpt("-i") && options.hasOpt("-o")) {
            return getGpsCoordinatesFromFile(options);
        }
        return null;

    }


    public List<Coordinates> getGpsCoordinatesFromAddress(IOptions options) {
        String url = makeTargetUrl(urlFormat, options.getOpt("-a"));
        List<Coordinates> coords = null;
        //http://where.yahooapis.com/geocode?line1=18027+81st+LN+NE&line2=Kenmore,+WA&appid=00ff0ece4e3ca671a610241bc3c1ad0500c32bf7

        String response = getResponse(url);

        try {

            Coordinates coord = getCoordinatesFromResponse(statusXpathString, latitudeXpathString, longitudeXpathString, response);
            if (coord != null) {
                coords.add(coord);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return coords;
    }

    public List<Coordinates> getGpsCoordinatesFromFile(IOptions options) {
        String url = makeTargetUrl(urlFormat, options.getOpt("-a"));
        List<Coordinates> coords = null;
        //http://where.yahooapis.com/geocode?line1=18027+81st+LN+NE&line2=Kenmore,+WA&appid=00ff0ece4e3ca671a610241bc3c1ad0500c32bf7

        String response = getResponse(url);

        try {

            Coordinates coord = getCoordinatesFromResponse(statusXpathString, latitudeXpathString, longitudeXpathString, response);
            if (coord != null) {
                coords.add(coord);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return coords;
    }
}
