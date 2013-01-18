package com.codebytes.writers;

import com.codebytes.Coordinates;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lwallen
 * Date: 1/17/13
 * Time: 8:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGeocodeFileWriter {
    void write(List<Coordinates> coordinates, String filePath);
    void write(List<Coordinates> coordinates, String filePath, boolean shouldAppend);
}
