import com.codebytes.Coordinates;
import com.codebytes.Options;
import com.codebytes.readers.GeoCodeApiEnum;
import com.codebytes.readers.GeoCodeReaderFactory;
import com.codebytes.readers.IGeoCodeReader;

import java.util.HashMap;

/**
 * User: Lee
 * Date: 1/4/13
 * Time: 10:35 PM
 */
public class GeoCoder {


    public static void main(String[] args) {
        // write your code
        // here
        if (args.length < 1) {
            System.out.printf("Usage: %n\tGeoCode <address to geocode>%n%n");
            System.out.printf("\tGeoCode [-i <input file containing addresses to geocode> [-o <output file>]]%n%n");
            System.exit(1);
        }
        Options options = null;



        try {
            options = new Options(args);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return;
        }

        IGeoCodeReader yahooGeoCodeReader = GeoCodeReaderFactory.getInstance().makeReader(GeoCodeApiEnum.YAHOO);
        Coordinates coords1 = yahooGeoCodeReader.getGpsCoordinates(options);

        IGeoCodeReader geoCodeReader = GeoCodeReaderFactory.getInstance().makeReader(GeoCodeApiEnum.GOOGLE);
        Coordinates coords2 = geoCodeReader.getGpsCoordinates(options);

        if (options.hasOpt("-a")) {
            System.out.printf("Latitude and longitude : %s, %s%n", coords1.getLatitude(), coords1.getLongitude());
            System.out.printf("Url : https://maps.google.com/maps?q=%s,+%s%n", coords1.getLatitude(), coords1.getLongitude());
            System.out.printf("Latitude and longitude : %s, %s%n", coords2.getLatitude(), coords2.getLongitude());
            System.out.printf("Url : https://maps.google.com/maps?q=%s,+%s%n", coords2.getLatitude(), coords2.getLongitude());
        }


    }

    private static HashMap<String, String> loadOptionsFromCommandLine(String[] args) throws Exception {
        HashMap<String, String> options = null;
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
