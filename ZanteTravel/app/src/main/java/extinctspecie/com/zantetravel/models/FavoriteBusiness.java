package extinctspecie.com.zantetravel.models;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by WorkSpace on 27-Jul-17.
 */

@RealmClass
public class FavoriteBusiness implements RealmModel
{
    @PrimaryKey
    int id;

    public FavoriteBusiness() {
    }

    public FavoriteBusiness(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
