package com.codebytes;

/**
 * User: Lee
 * Date: 1/6/13
 * Time: 9:17 PM
 */
public class UnknownOptionException extends Exception {

    public UnknownOptionException() {
        super();
    }

    public UnknownOptionException(String message) {
        super(message);
    }
}
