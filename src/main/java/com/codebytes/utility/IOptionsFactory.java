package com.codebytes.utility;

/**
 * User: Lee
 * Date: 1/7/13
 * Time: 8:32 PM
 */
public interface IOptionsFactory {

    IOptions loadOptions(String[] args) throws UnknownOptionException;

}
