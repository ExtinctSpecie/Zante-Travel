package extinctspecie.com.zantetravel.models;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

public class Images
{
    private Business business;
    private int position;
    private String image;

    public Images() {}

    public Images(Business business, int position, String image) {
        this.business = business;
        this.position = position;
        this.image = image;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
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
}
