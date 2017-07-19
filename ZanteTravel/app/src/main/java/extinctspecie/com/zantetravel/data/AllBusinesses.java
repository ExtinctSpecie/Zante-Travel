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
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by WorkSpace on 05-Jul-17.
 */

public class AllBusinesses {

    //Empty constructor
    public AllBusinesses() {}

    public static void addBusinessesWithGID(List<Business> businesses , int groupID)
    {
        try {

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(businesses);
            realm.commitTransaction();
        }
        catch (RealmPrimaryKeyConstraintException e)
        {
            e.printStackTrace();
        }

    }
    public static List<Business> getBusinessesWithGID(int groupID)
    {
        Realm realm = Realm.getDefaultInstance();

        try
        {
            return realm.where(Business.class).equalTo("groupID",groupID).findAll();
        }
        catch (RealmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static Business getBusinessWithID(int ID)
    {
        Realm realm = Realm.getDefaultInstance();
        try
        {
            return realm.where(Business.class).equalTo("id",ID).findFirst();
        }
        catch (RealmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
