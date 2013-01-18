package com.codebytes.writers;

import com.codebytes.utility.Coordinates;

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

    private String fileOutputPath = null;
    public String getFileOutputPath() {
        return fileOutputPath;
    }
    private void setFileOutputPath(String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
    }

    public AddressLocationFileWriter() {

    }

    public AddressLocationFileWriter(String filePath) {
        super();
        setFileOutputPath(filePath);
    }

    public void write(List<Coordinates> coordinates) throws MissingFileException {
        write(coordinates, false);
    }

    public void write(List<Coordinates> coordinates, boolean shouldAppend) throws MissingFileException {

        if (getFileOutputPath() == null) {
            throw new MissingFileException(String.format("The output file path is null.%n"));
        }

        FileOutputStream fileOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream(getFileOutputPath(), shouldAppend);

            for (Coordinates coord : coordinates) {
                fileOutputStream.write(coord.toString().getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
