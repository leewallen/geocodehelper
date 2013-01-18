package com.codebytes.writers;

import com.codebytes.utility.Coordinates;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lwallen
 * Date: 1/17/13
 * Time: 8:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGeocodeFileWriter {
    void write(List<Coordinates> coordinates) throws MissingFileException;
    void write(List<Coordinates> coordinates, boolean shouldAppend) throws MissingFileException;
}
