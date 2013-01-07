package com.codebytes;

import java.util.HashMap;

/**
 * User: Lee
 * Date: 1/6/13
 * Time: 7:32 PM
 */
public class Options {


    private HashMap<String, String> options = null;

    public boolean hasOpt(String optKey) {
        return options.containsKey(optKey);
    }

    public String getOpt(String optKey) {
        return options.get(optKey);
    }

    public Options(String[] args) throws Exception {
        this.options = loadOptionsFromCommandLine(args);
    }

    private static HashMap<String, String> loadOptionsFromCommandLine(String[] args) throws Exception {
        HashMap<String, String> options = new HashMap<String, String>();
        String key = null;
        boolean hasFlag = false;

        if (args.length == 0) {
            return options;
        }

        for(String arg : args) {
            if (arg.trim().startsWith("-")) {
                // this tells us which option it is
                if (arg.trim().equals("-i") || arg.trim().equals("-o")) {
                    hasFlag = true;
                    key = arg;
                } else {
                    throw new Exception (String.format("Unknown option : %s", arg) );
                }
            } else {
                if (hasFlag) {
                    options.put(key, arg);
                    hasFlag = false;
                } else {
                    // assuming that this is an address
                    options.put("-a", arg);
                }
            }
        }

        return options;
    }

}
