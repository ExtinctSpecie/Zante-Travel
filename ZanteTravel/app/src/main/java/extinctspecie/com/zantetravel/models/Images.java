package extinctspecie.com.zantetravel.models;

import com.orm.SugarRecord;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

public class Images extends SugarRecord<Business>
{
    private int business;
    private int position;
    private String image;

    public Images() {}

    public Images(int business, int position, String image) {
        this.business = business;
        this.position = position;
        this.image = image;
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
}
