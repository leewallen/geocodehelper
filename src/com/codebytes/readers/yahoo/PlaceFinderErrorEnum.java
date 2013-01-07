package com.codebytes.readers.yahoo;

/**
 * User: Lee
 * Date: 1/2/13
 * Time: 6:54 PM
 */
public enum PlaceFinderErrorEnum {

    NO_ERROR (0),
    FEATURE_NOT_SUPPORTED (1),
    NO_INPUT_PARAMETERS (100),
    ADDRESS_DATA_NOT_RECOGNIZED_AS_UTF8 (102),
    INSUFFICIENT_ADDRESS_DATA (103),
    UNKNOWN_LANGUAGE (104),
    NO_COUNTRY_DETECTED (105),
    COUNTRY_NOT_SUPPORTED (106);

    private int errorNumber;

    PlaceFinderErrorEnum(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

//
//    0: No error
//    1: Feature not supported
//    100: No input parameters
//    102: Address data not recognized as valid UTF-8
//            103: Insufficient address data
//    104: Unknown language
//    105: No country detected
//    106: Country not supported
//

}
