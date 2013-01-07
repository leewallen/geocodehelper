package com.codebytes.tests;

import com.codebytes.Options;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * User: Lee
 * Date: 1/6/13
 * Time: 7:45 PM
 */


public class OptionsTests {

    @Test
    public void VerifyAddressOptIsFound() {

        Options opts = null;
        try {
            opts = new Options(new String[] {"booger"});
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertEquals(opts.getOpt("-a"), "booger");
    }
}
