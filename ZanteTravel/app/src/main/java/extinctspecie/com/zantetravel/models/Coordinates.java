package extinctspecie.com.zantetravel.models;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by WorkSpace on 20-Jul-17.
 */
@RealmClass
public class Coordinates implements RealmModel
{
    @PrimaryKey
    private float longitude;
    @PrimaryKey
    private float latitude;

    public Coordinates()
    {

    }
    public Coordinates(float latitude, float longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
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
