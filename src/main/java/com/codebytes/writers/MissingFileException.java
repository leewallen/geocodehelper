package com.codebytes.writers;

/**
 * Created with IntelliJ IDEA.
 * User: lwallen
 * Date: 1/18/13
 * Time: 7:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class MissingFileException extends Exception {
    public MissingFileException() {
        super();
    }

    public MissingFileException(String message) {
        super(message);
    }
}
