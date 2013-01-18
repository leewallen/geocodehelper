package com.codebytes.utility;

import java.util.Map;

/**
 * User: Lee
 * Date: 1/6/13
 * Time: 7:32 PM
 */
public class Options implements IOptions {


    private Map<String, String> options = null;

    @Override
    public boolean hasOpt(String optKey) {
        return options.containsKey(optKey);
    }

    @Override
    public String getOpt(String optKey) {
        return options.get(optKey);
    }

    public Options(Map<String, String> options) {
        this.options = options;
    }
}
