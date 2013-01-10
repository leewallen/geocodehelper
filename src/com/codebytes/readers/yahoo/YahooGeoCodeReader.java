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

public class YahooGeoCodeReader extends AbstractGeoCodeReader implements IGeoCodeReader {

    private static String urlFormat = "http://where.yahooapis.com/geocode?appid=%s&q=%s";
    private static String appId = "00ff0ece4e3ca671a610241bc3c1ad0500c32bf7";

    private String address = null;
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String makeTargetUrl(String urlFormat, String address) {
        return String.format(urlFormat, appId, address.replaceAll(" ", "+"));
    }

    public Coordinates getGpsCoordinates(IOptions options) {

        String url = makeTargetUrl(urlFormat, options.getOpt("-a"));
        Coordinates coords = null;
        //http://where.yahooapis.com/geocode?line1=18027+81st+LN+NE&line2=Kenmore,+WA&appid=00ff0ece4e3ca671a610241bc3c1ad0500c32bf7

        String response = getResponse(url);

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(response.getBytes()));

            XPathFactory xPathFactory =  XPathFactory.newInstance();
            XPathExpression errorXpath = xPathFactory.newXPath().compile("/ResultSet/Error/text()");

            int errorCode = Integer.parseInt(((Node) errorXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue());

            if (errorCode != PlaceFinderErrorEnum.NO_ERROR.getErrorNumber()) {
                return null;
            }

            XPathExpression latXpath = xPathFactory.newXPath().compile("/ResultSet/Result/latitude/text()");
            XPathExpression lonXpath = xPathFactory.newXPath().compile("/ResultSet/Result/longitude/text()");

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
