import com.codebytes.Coordinates;
import com.codebytes.readers.GeoCodeApiEnum;
import com.codebytes.readers.GeoCodeReaderFactory;
import com.codebytes.readers.IGeoCodeReader;

/**
 * User: Lee
 * Date: 1/4/13
 * Time: 10:35 PM
 */
public class GeoCoder {

    public static void main(String[] args) {
        // write your code
        // here

        String address = args[0];

        IGeoCodeReader yahooGeoCodeReader = GeoCodeReaderFactory.getInstance().makeReader(GeoCodeApiEnum.YAHOO);
        Coordinates coords = yahooGeoCodeReader.getGpsCoordinates(address);

        System.out.printf("Latitude and longitude : %s, %s%n", coords.getLatitude(), coords.getLongitude());
        System.out.printf("Url : https://maps.google.com/maps?q=%s,+%s%n", coords.getLatitude(), coords.getLongitude());

        IGeoCodeReader geoCodeReader = GeoCodeReaderFactory.getInstance().makeReader(GeoCodeApiEnum.GOOGLE);
        coords = geoCodeReader.getGpsCoordinates(address);

        System.out.printf("Latitude and longitude : %s, %s%n", coords.getLatitude(), coords.getLongitude());
        System.out.printf("Url : https://maps.google.com/maps?q=%s,+%s%n", coords.getLatitude(), coords.getLongitude());


    }


}
