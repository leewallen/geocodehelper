package com.codebytes.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
}
