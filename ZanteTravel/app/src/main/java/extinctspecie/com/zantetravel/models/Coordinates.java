package extinctspecie.com.zantetravel.models;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by WorkSpace on 20-Jul-17.
 */

public class Coordinates
{

    private float longitude;

    private float latitude;

    public Coordinates()
    {

    }
    public Coordinates(float latitude, float longitude) {

        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return String.valueOf(latitude + " , " + String.valueOf(longitude));
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
