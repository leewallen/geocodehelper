package com.codebytes.readers.yahoo;

import com.codebytes.Coordinates;
import com.codebytes.IOptions;
import com.codebytes.readers.AbstractGeoCodeReader;
import com.codebytes.readers.AddressFileReader;
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
        List<Coordinates> coords = null;

        if (options.hasOpt("-a")) {

            Coordinates coord = getGpsCoordinatesForAddress(options.getOpt("-a"), urlFormat, latitudeXpathString, longitudeXpathString, statusXpathString);
            coords.add(coord);

        } else if (options.hasOpt("-i")) {

            String inputFilePath = options.getOpt("-i");

            AddressFileReader addressFileReader = new AddressFileReader();
            List<String> addresses = addressFileReader.read(inputFilePath);

            for(String address : addresses) {
                coords.add(getGpsCoordinatesForAddress(address, urlFormat, latitudeXpathString, longitudeXpathString, statusXpathString));
            }
        }

        return coords;
    }
}
