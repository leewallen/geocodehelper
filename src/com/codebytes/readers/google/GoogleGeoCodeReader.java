package com.codebytes.readers.google;

import com.codebytes.Coordinates;
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
//            /GeocodeResponse/result/geometry/location
//    /GeocodeResponse/result/geometry/location/lat
//    /GeocodeResponse/result/geometry/location/lng
//
//    /GeocodeResponse/result/formatted_address


    private String address = null;
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }


    public Coordinates getGpsCoordinates(String address) {

        String url = makeTargetUrl(urlFormat, address);
        Coordinates coords = null;

        String response = getResponse(url);

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(response.getBytes()));

            XPathFactory xPathFactory =  XPathFactory.newInstance();
            XPathExpression statusXpath = xPathFactory.newXPath().compile("/GeocodeResponse/status/text()");

            String status = ((Node) statusXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue();

            if (GoogleErrorEnum.valueOf(status) != GoogleErrorEnum.OK) {
                return null;
            }

            XPathExpression latXpath = xPathFactory.newXPath().compile("/GeocodeResponse/result/geometry/location/lat/text()");
            XPathExpression lonXpath = xPathFactory.newXPath().compile("/GeocodeResponse/result/geometry/location/lng/text()");

            String lat = ((Node)latXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue();
            String lon = ((Node)lonXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue();

            coords = new Coordinates();
            coords.parse(String.format("%s, %s", lat, lon));

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
