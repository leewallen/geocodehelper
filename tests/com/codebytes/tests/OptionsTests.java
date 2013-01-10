package com.codebytes.tests;

import com.codebytes.IOptions;
import com.codebytes.OptionsFactory;
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

        IOptions opts = null;
        try {
            opts = OptionsFactory.getInstance().loadOptions(new String[]{"booger"});
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertEquals(opts.getOpt("-a"), "booger");
    }

    @Test
    public void VerifyInputAndOutputFilePaths() {

        IOptions opts = null;
        try {
            opts = OptionsFactory.getInstance().loadOptions(new String[]{"-i", "inputfile.txt", "-o", "outputfile.txt"});
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertEquals(opts.getOpt("-i"), "inputfile.txt");
        assertEquals(opts.getOpt("-o"), "outputfile.txt");
    }

    public void VerifyInvalidOptionThrows() {

        IOptions opts = null;

    }

}
