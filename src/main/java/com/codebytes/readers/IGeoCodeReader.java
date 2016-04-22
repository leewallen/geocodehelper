package com.codebytes.readers;

import com.codebytes.utility.Coordinates;
import com.codebytes.utility.IOptions;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/4/13
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IGeoCodeReader {
    List<Coordinates> getGpsCoordinates(IOptions options);
}
