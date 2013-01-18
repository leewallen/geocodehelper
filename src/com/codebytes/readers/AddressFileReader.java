package com.codebytes.readers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Lee
 * Date: 1/10/13
 * Time: 5:26 PM
 */
public class AddressFileReader implements IAddressFileReader {

    public List<String> read(String inputFilePath) {
        List<String> addresses = new ArrayList<String>();

        File inputFile = new File(inputFilePath);


        if (inputFilePath.isEmpty() || !inputFile.exists())
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addresses;
    }
}
