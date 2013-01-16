package com.codebytes.readers;

import com.codebytes.Options;
import com.codebytes.readers.IGeoCodeReader;

/**
 * User: Lee
 * Date: 1/4/13
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IGeoCodeReaderFactory {
    IGeoCodeReader makeReader(GeoCodeApiEnum geoCodeApiEnum, Options options);
}
