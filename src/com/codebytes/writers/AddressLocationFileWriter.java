package com.codebytes.writers;

import com.codebytes.Coordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * User: Lee
 * Date: 1/17/13
 * Time: 9:20 PM
 */
public class AddressLocationFileWriter implements IGeocodeFileWriter {

    public void write(List<Coordinates> coordinates, String filePath) {
        write(coordinates, filePath, false);
    }

    public void write(List<Coordinates> coordinates, String filePath, boolean shouldAppend) {
        FileOutputStream fileOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream(filePath, shouldAppend);

            for (Coordinates coord : coordinates) {
                fileOutputStream.write(coord.toString().getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
