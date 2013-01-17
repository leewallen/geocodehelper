package com.codebytes.readers.google;

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
    private static String latXpath = "/GeocodeResponse/result/geometry/location/lat/text()";
    private static String lonXpath = "/GeocodeResponse/result/geometry/location/lon/text()";
    private static String statusXpath = "/GeocodeResponse/status/text()";

//    http://maps.googleapis.com/maps/api/geocode/xml?address=space+needle&sensor=false
//
//    /GeocodeResponse/result/geometry/location
//    /GeocodeResponse/result/geometry/location/lat
//    /GeocodeResponse/result/geometry/location/lng
//    /GeocodeResponse/result/formatted_address

    private boolean wasCallSuccessful(String response) throws IOException, SAXException, XPathExpressionException {
        Document doc = getDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes()));

        XPathFactory xPathFactory =  XPathFactory.newInstance();
        XPathExpression statusXpathExpression = xPathFactory.newXPath().compile(statusXpath);

        String status = ((Node) statusXpathExpression.evaluate(doc, XPathConstants.NODE)).getNodeValue();

        if (GoogleErrorEnum.valueOf(status) != GoogleErrorEnum.OK) {
            return false;
        }
        return true;
    }

    public List<Coordinates> getGpsCoordinates(IOptions options) {
        List<Coordinates> coords = null;

        if (options.hasOpt("-a")) {
            String url = makeTargetUrl(urlFormat, options.getOpt("-a"));

            String response = getResponse(url);
            try {
                if (wasCallSuccessful(response)) {
                    Coordinates coord = getGpsCoordinatesForAddress(options.getOpt("-a"), urlFormat, latXpath, lonXpath);
                    coords.add(coord);
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (SAXException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (XPathExpressionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else if (options.hasOpt("-i")) {

            String inputFilePath = options.getOpt("-i");

            AddressFileReader addressFileReader = new AddressFileReader();
            List<String> addresses = addressFileReader.read(inputFilePath);

            for(String address : addresses) {
                String url = makeTargetUrl(urlFormat, address);
                String response = getResponse(url);

                try {
                    if (wasCallSuccessful(response)) {
                        Coordinates coord = getGpsCoordinatesForAddress(options.getOpt("-a"), urlFormat, latXpath, lonXpath);
                        coords.add(getGpsCoordinatesForAddress(response, urlFormat, latXpath, lonXpath));

                        coords.add(coord);
                    }
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SAXException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (XPathExpressionException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        return coords;
    }

}
