package extinctspecie.com.zantetravel.data;

import java.util.List;

import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.FavoriteBusiness;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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
    public static List<Business> getFavBusinesses()
    {
        try
        {
            Realm realm = Realm.getDefaultInstance();

            RealmQuery<Business> basketQuery = realm.where(Business.class);

            RealmResults<FavoriteBusiness> favoriteBusinesses = realm.where(FavoriteBusiness.class).findAll();


            for(int i=0; i<favoriteBusinesses.size(); i++){
                if(i==0)
                    basketQuery.equalTo("id", favoriteBusinesses.get(i).getId());
                else
                    basketQuery.or().equalTo("id", favoriteBusinesses.get(i).getId());
            }

            List<Business> businesses = realm.copyFromRealm(basketQuery.findAll());

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

