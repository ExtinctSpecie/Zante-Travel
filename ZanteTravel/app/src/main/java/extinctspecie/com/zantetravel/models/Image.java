package extinctspecie.com.zantetravel.models;


import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

@RealmClass
public class Image implements RealmModel  {

    @PrimaryKey
    private int id;
    private int business;
    private int position;
    private String image;
    private String imageURL;


    private String imageURI;

    public Image() {

    }

    public Image(int id, int business, int position, String image, String imageURL) {
        this.id = id;
        this.business = business;
        this.position = position;
        this.image = image;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
