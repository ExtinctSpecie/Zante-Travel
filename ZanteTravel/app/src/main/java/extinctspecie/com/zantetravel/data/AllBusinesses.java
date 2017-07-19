package extinctspecie.com.zantetravel.data;

import java.util.List;

import extinctspecie.com.zantetravel.models.Business;
import io.realm.Realm;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by WorkSpace on 05-Jul-17.
 */

public class AllBusinesses {


    public static void addBusinessesWithGID(List<Business> businesses)
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
