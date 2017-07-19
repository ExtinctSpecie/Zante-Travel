package extinctspecie.com.zantetravel.data;

import java.util.List;

import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.Image;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by WorkSpace on 19-Jul-17.
 */

public class AllImages
{
    public static void addImages(List<Image> images)
    {
        try {

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            realm.insertOrUpdate(images);
            //realm.copyToRealmOrUpdate(images);

            realm.commitTransaction();
        }
        catch (RealmPrimaryKeyConstraintException e)
        {
            e.printStackTrace();
        }
    }
    public static List<Image> getImagesOfBusinessID(int businessID)
    {
        Realm realm = Realm.getDefaultInstance();

        try
        {
            return realm.where(Image.class).equalTo("business",businessID).findAll();
        }
        catch (RealmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static Image getImageWithID(int ID)
    {
        Realm realm = Realm.getDefaultInstance();
        try
        {
            return realm.where(Image.class).equalTo("id",ID).findFirst();
        }
        catch (RealmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
