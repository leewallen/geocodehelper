package com.codebytes.readers;

import java.io.*;
import java.util.List;

/**
 * User: Lee
 * Date: 1/10/13
 * Time: 5:26 PM
 */
public class AddressFileReader implements IAddressFileReader {

    public List<String> read(String inputFile) {
        List<String> addresses = null;

        if (inputFile.isEmpty())
            return addresses;

        try {
            BufferedReader ifr = new BufferedReader(new FileReader(inputFile));
            String line = null;

            while ((line = ifr.readLine()) != null) {
                if (!line.isEmpty()) {
                    addresses.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return addresses;
    }
}
