package extinctspecie.com.zantetravel.data;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import extinctspecie.com.zantetravel.models.Business;

/**
 * Created by WorkSpace on 05-Jul-17.
 */

public class AllBusinesses {
    private static List<Business> allBusinesses = new ArrayList<>();

    public AllBusinesses() {

    }

    @Nullable
    public static List<Business> getAllBusinesses() {
        if(!allBusinesses.isEmpty())
            return allBusinesses;
        else
            return null;
    }

    public static void setAllBusinesses(List<Business> allBusinesses) {
        AllBusinesses.allBusinesses = allBusinesses;
    }
}
