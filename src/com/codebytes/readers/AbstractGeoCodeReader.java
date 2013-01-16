package com.codebytes.readers;

import com.codebytes.Coordinates;
import com.codebytes.readers.google.GoogleErrorEnum;
import com.codebytes.readers.yahoo.PlaceFinderErrorEnum;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/4/13
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGeoCodeReader {

    private DocumentBuilder db = null;
    protected DocumentBuilder getDocumentBuilder() {
        return db;
    }
    protected void setDocumentBuilder(DocumentBuilder db) {
        this.db = db;
    }


    private DocumentBuilderFactory dbf = null;
    protected DocumentBuilderFactory getDocumentBuilderFactory() {
        return dbf;
    }
    protected void setDocumentBuilderFactory(DocumentBuilderFactory dbf) {
        this.dbf = dbf;
    }

    public String makeTargetUrl(String urlFormat, String address) {
        return String.format(urlFormat, address.replaceAll(" ", "+"));
    }

    public String getResponse(String url) {
        HttpURLConnection connection = null;
        OutputStreamWriter wr = null;
        BufferedReader rd  = null;
        StringBuilder sb = null;
        String line = null;

        URL geocodingUrl = null;

        try {
            //set up out communications stuff
            connection = null;
            geocodingUrl = new URL(url);

            //Set up the initial connection
            connection = (HttpURLConnection)geocodingUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            connection.connect();

            //read the result from the server
            rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            //close the connection, set all objects to null
            connection.disconnect();
            rd = null;
            wr = null;
            connection = null;
        }

        return sb.toString();
    }


    protected Coordinates getCoordinatesFromResponse(String statusXpathString, String latitudeXpathString, String longitudeXpathString, String response) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {
        Document doc = this.getDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes()));

        if (getDocumentBuilderFactory() == null) setDocumentBuilderFactory(DocumentBuilderFactory.newInstance());
        if (getDocumentBuilder() == null) setDocumentBuilder(getDocumentBuilderFactory().newDocumentBuilder());

        XPathFactory xPathFactory =  XPathFactory.newInstance();
        XPathExpression errorXpath = xPathFactory.newXPath().compile(statusXpathString);

        int errorCode = Integer.parseInt(((Node) errorXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue());

        if (errorCode != PlaceFinderErrorEnum.NO_ERROR.getErrorNumber()) {
            return null;
        }

        XPathExpression latXpath = xPathFactory.newXPath().compile(latitudeXpathString);
        XPathExpression lonXpath = xPathFactory.newXPath().compile(longitudeXpathString);

        String lat = ((Node)latXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue();
        String lon = ((Node)lonXpath.evaluate(doc, XPathConstants.NODE)).getNodeValue();

        Coordinates coord = new Coordinates();
        coord.parse(String.format("%s, %s", lat, lon));
        return coord;
    }


    protected Coordinates getGpsCoordinatesForAddress(String address, String urlFormat) {
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
