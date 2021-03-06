package com.codebytes.readers.yahoo;

import com.codebytes.utility.Coordinates;
import com.codebytes.utility.IOptions;
import com.codebytes.readers.AbstractGeoCodeReader;
import com.codebytes.readers.AddressFileReader;
import com.codebytes.readers.IGeoCodeReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/4/13
 * Time: 10:36 PM
 */

public class YahooGeoCodeReader extends AbstractGeoCodeReader implements IGeoCodeReader {

    private final static String urlFormat = "http://where.yahooapis.com/geocode?appid=%s&q=%s";
    private final static String appId = "00ff0ece4e3ca671a610241bc3c1ad0500c32bf7";
    private final static String statusXpath = "/ResultSet/Error/text()";
    private final static String latXpath = "/ResultSet/Result/latitude/text()";
    private final static String lonXpath = "/ResultSet/Result/longitude/text()";

    private boolean wasCallSuccessful(String response) throws IOException, SAXException, XPathExpressionException {
        Document doc = getDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes()));

        XPathFactory xPathFactory =  XPathFactory.newInstance();
        XPathExpression statusXpathExpression = xPathFactory.newXPath().compile(statusXpath);

        String status = ((Node) statusXpathExpression.evaluate(doc, XPathConstants.NODE)).getNodeValue();

        if (Integer.parseInt(status) != PlaceFinderErrorEnum.NO_ERROR.getErrorNumber()) {
            return false;
        }
        return true;
    }

    @Override
    public String makeTargetUrl(String urlFormat, String address) {
        return String.format(urlFormat, appId, address.replaceAll(" ", "+"));
    }


    public List<Coordinates> getGpsCoordinates(IOptions options) {
        List<Coordinates> coords = new ArrayList<Coordinates>();

        if (options.hasOpt("-a")) {
            String address = options.getOpt("-a");
            String url = makeTargetUrl(urlFormat, address);

            String response = getResponse(url);
            try {
                if (wasCallSuccessful(response)) {
                    Coordinates coord = getGpsCoordinatesForAddress(response, latXpath, lonXpath);
                    coord.setLocationName(address);
                    coords.add(coord);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
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
                        Coordinates coord = getGpsCoordinatesForAddress(response, latXpath, lonXpath);
                        coord.setLocationName(address);
                        coords.add(coord);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
            }
        }

        return coords;
    }
}
