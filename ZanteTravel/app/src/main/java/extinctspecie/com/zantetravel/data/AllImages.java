package extinctspecie.com.zantetravel.data;

import java.util.ArrayList;
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

            realm.close();
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
            List<Image> images = new ArrayList<>(realm.copyFromRealm(realm.where(Image.class).equalTo("business",businessID).findAll()));
            realm.close();
            return images;
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
            Image image = realm.copyFromRealm(realm.where(Image.class).equalTo("id",ID).findFirst());

            realm.close();

            return image;
        }
        catch (RealmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
