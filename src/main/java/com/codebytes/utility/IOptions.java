package com.codebytes.utility;

/**
 * User: Lee
 * Date: 1/9/13
 * Time: 7:45 PM
 */
public interface IOptions {
    boolean hasOpt(String optKey);

    String getOpt(String optKey);
}
