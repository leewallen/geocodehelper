package com.codebytes.readers;

import com.codebytes.Coordinates;
import com.codebytes.IOptions;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/4/13
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IGeoCodeReader {
    Coordinates getGpsCoordinates(IOptions options);
}
