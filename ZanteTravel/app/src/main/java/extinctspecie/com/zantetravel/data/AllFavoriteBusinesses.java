package extinctspecie.com.zantetravel.data;

import android.util.Log;

import java.util.List;

import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.FavoriteBusiness;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.RealmClass;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by WorkSpace on 28-Jul-17.
 */

public class AllFavoriteBusinesses
{
    public static void addFavorite(FavoriteBusiness favoriteBusiness)
    {
        try {

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(favoriteBusiness);
            realm.commitTransaction();
        }
        catch (RealmPrimaryKeyConstraintException e)
        {
            e.printStackTrace();
        }
    }
    public static void removeFavorite(int id)
    {
        try {

            Realm realm = Realm.getDefaultInstance();
            RealmResults<FavoriteBusiness> realmResults = realm.where(FavoriteBusiness.class).equalTo("id",id).findAll();

            realm.beginTransaction();

            if(realmResults.size() > 0)
            {
                realmResults.deleteFirstFromRealm();
            }

            realm.commitTransaction();

            realm.close();
        }
        catch (RealmPrimaryKeyConstraintException e)
        {
            e.printStackTrace();
        }
    }
    public static boolean businessAlreadySaved(int id)
    {
        try {

            Realm realm = Realm.getDefaultInstance();

            //realm.beginTransaction();

            FavoriteBusiness myFavoriteBusiness = realm.where(FavoriteBusiness.class).equalTo("id",id).findFirst();

            realm.close();

            if(myFavoriteBusiness != null)
                return true;


            //realm.commitTransaction();
        }
        catch (RealmPrimaryKeyConstraintException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Business> getFavBusinesses()
    {
        try
        {
            List<Business> businesses;
            Realm realm = Realm.getDefaultInstance();

            RealmQuery<Business> basketQuery = realm.where(Business.class);

            RealmResults<FavoriteBusiness> favoriteBusinesses = realm.where(FavoriteBusiness.class).findAll();

            for(int i=0; i<favoriteBusinesses.size(); i++){
                if(i==0)
                    basketQuery.equalTo("id", favoriteBusinesses.get(i).getId());
                else
                    basketQuery.or().equalTo("id", favoriteBusinesses.get(i).getId());
            }
            if(favoriteBusinesses.size() > 0)
                businesses = realm.copyFromRealm(basketQuery.findAll());
            else
                businesses = null;

            realm.close();

            return businesses;
        }
        catch (RealmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

