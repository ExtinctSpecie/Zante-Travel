package extinctspecie.com.zantetravel.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created by WorkSpace on 02-Jul-17.
 */

//// TODO: 02-Jul-17 ADD BASE URL OF SERVER AND SO ON
public class Information
{
    public static String BASE_API_URL = "https://zante-travel.herokuapp.com/api/";
    public static String BASE_IMAGE_URL = "https://zante-travel.herokuapp.com";

    public static boolean appNeedsUpdate()
    {
        return false;
    }
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
