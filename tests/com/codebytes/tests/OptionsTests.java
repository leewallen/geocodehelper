package com.codebytes.tests;

import com.codebytes.IOptions;
import com.codebytes.OptionsFactory;
import com.codebytes.UnknownOptionException;
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
            opts = OptionsFactory.getInstance().loadOptions(new String[]{"-a", "booger"});
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(opts.getOpt("-a"), "booger");
    }

    @Test(expected = UnknownOptionException.class)
    public void VerifyUnknownOptThrows() throws UnknownOptionException {

        IOptions opts = null;
        opts = OptionsFactory.getInstance().loadOptions(new String[]{"-b", "booger"});
    }

    @Test
    public void VerifyInputAndOutputFilePaths() {

        IOptions opts = null;
        try {
            opts = OptionsFactory.getInstance().loadOptions(new String[]{"-i", "inputfile.txt", "-o", "outputfile.txt"});
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(opts.getOpt("-i"), "inputfile.txt");
        assertEquals(opts.getOpt("-o"), "outputfile.txt");
    }

    public void VerifyInvalidOptionThrows() {

        IOptions opts = null;

    }

}
