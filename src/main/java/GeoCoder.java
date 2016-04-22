import com.codebytes.readers.GeoCodeApiEnum;
import com.codebytes.readers.GeoCodeReaderFactory;
import com.codebytes.readers.IGeoCodeReader;
import com.codebytes.utility.Coordinates;
import com.codebytes.utility.CoordinatesHelper;
import com.codebytes.utility.IOptions;
import com.codebytes.utility.OptionsFactory;
import com.codebytes.writers.AddressLocationFileWriter;
import com.codebytes.writers.MissingFileException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

        IOptions options = null;

        try {
            options = OptionsFactory.getInstance().loadOptions(args);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return;
        }

        IGeoCodeReader yahooGeoCodeReader = GeoCodeReaderFactory.getInstance().makeReader(GeoCodeApiEnum.YAHOO, options);
        List<Coordinates> coords1 = yahooGeoCodeReader.getGpsCoordinates(options);

        IGeoCodeReader geoCodeReader = GeoCodeReaderFactory.getInstance().makeReader(GeoCodeApiEnum.GOOGLE, options);
        List<Coordinates> coords2 = geoCodeReader.getGpsCoordinates(options);


        if (options.hasOpt("-o") && !options.getOpt("-o").isEmpty()) {
            String outputFile = options.getOpt("-o");
            try {
                FileWriter fileWriter = new FileWriter(outputFile);
                fileWriter.write(String.format("Address file created : %s%n", new Date()));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AddressLocationFileWriter locationFileWriter = new AddressLocationFileWriter(outputFile);
            try {
                locationFileWriter.write(coords1, true);
                locationFileWriter.write(coords2, true);
            } catch (MissingFileException e) {
                e.printStackTrace();
            }
        }

        if ((options.hasOpt("-v") && options.hasOpt("-o")) || !options.hasOpt("-o")) {
            for(Coordinates coord : coords1) {
                System.out.printf("Yahoo Url for \"%s\" :%n\thttps://maps.google.com/maps?q=%s,+%s%n", coord.getLocationName(), coord.getLatitude(), coord.getLongitude());
            }

            for(Coordinates coord : coords2) {
                System.out.printf("Google Url for \"%s\" :%n\thttps://maps.google.com/maps?q=%s,+%s%n", coord.getLocationName(), coord.getLatitude(), coord.getLongitude());
            }
        }

        double totalDistance = CoordinatesHelper.calculateDistanceInKilometers(coords2.toArray(new Coordinates[0]), 0);
        System.out.printf("Total distance (km): %.2f%n", totalDistance);
        totalDistance = CoordinatesHelper.convertKilometersToMiles(totalDistance);
        System.out.printf("Total distance (mi): %.2f%n", totalDistance);
    }
}
