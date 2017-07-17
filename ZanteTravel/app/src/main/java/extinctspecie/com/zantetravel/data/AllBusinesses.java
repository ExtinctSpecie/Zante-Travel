package extinctspecie.com.zantetravel.data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.Images;

/**
 * Created by WorkSpace on 05-Jul-17.
 */

public class AllBusinesses {

    private static HashMap<Integer ,List<Business>> allGroupBusinesses = new HashMap<>();

    //Empty constructor
    public AllBusinesses() {}

    public static void addBusinessesWithGID(List<Business> businesses , int groupID)
    {
        allGroupBusinesses.put(groupID,businesses);
    }
    public static List<Business> getBusinessesWithGID(int groupID)
    {
        return allGroupBusinesses.get(groupID);
    }
    public static Business getBusiness(int groupID, int position)
    {
       return allGroupBusinesses.get(groupID).get(position);
    }
}
