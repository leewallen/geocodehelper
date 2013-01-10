package com.codebytes;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Lee
 * Date: 1/7/13
 * Time: 8:41 PM
 */
public class OptionsFactory implements IOptionsFactory {

    private static IOptionsFactory _instance = null;

    public static IOptionsFactory getInstance () {
        if (_instance == null) {
            _instance = (IOptionsFactory) new OptionsFactory();
        }
        return _instance;
    }

    public IOptions loadOptions(String[] args) throws UnknownOptionException {
        Map<String, String> options = new HashMap<String, String>();
        String key = null;
        boolean hasFlag = false;

        if (args.length == 0) {
            new Options(options);
        }

        for(String arg : args) {
            if (arg.trim().startsWith("-")) {
                // this tells us which option it is
                if (arg.trim().equals("-i") || arg.trim().equals("-o")) {
                    hasFlag = true;
                    key = arg;
                } else {
                    throw new UnknownOptionException(String.format("Unknown option : %s", arg) );
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

        return new Options(options);
    }
}
