package extinctspecie.com.zantetravel.models;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

public class Images
{
    private int businessID;
    private int position;
    private String image;

    public Images() {}

    public Images(int businessID, int position, String image) {
        this.businessID = businessID;
        this.position = position;
        this.image = image;
    }

    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
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
}
