package extinctspecie.com.zantetravel.models;

import extinctspecie.com.zantetravel.activities.AllBusinessesActivity;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

public class Business
{

    private int id;
    private int position;
    private String name;
    private String location;
    private String shortDescription;
    private String longDescription;
    private String phoneNumber;
    private String email;
    private String website;
    private String mapCoordinates;
    private String address;
    private String group;
    private int groupID;
    private String category;
    private String type;
    private String workingHours;
    private String price;
    private String usefulTip;
    private boolean isPremium;
    private boolean creditCards;
    private boolean summerOnly;
    private boolean isRecommended;
    private String thumbnail;
    private String date;




    private AllBusinessesActivity.Coordinates coordinates;
    private float distanceToUser = 0f;


    //Empty constructor
    public Business() {
        super();
    }

    public Business(int id , int position, String name, String location, String shortDescription,
                    String longDescription, String phoneNumber, String email, String website,
                    String mapCoordinates, String address, String group, int groupID, String category,
                    String type, String workingHours, String price, String usefulTip,
                    boolean isPremium, boolean creditCards, boolean summerOnly, boolean isRecommended,
                    String thumbnail, String date,
                    AllBusinessesActivity.Coordinates coordinates, Float distanceToUser) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.location = location;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.mapCoordinates = mapCoordinates;
        this.address = address;
        this.group = group;
        this.groupID = groupID;
        this.category = category;
        this.type = type;
        this.workingHours = workingHours;
        this.price = price;
        this.usefulTip = usefulTip;
        this.isPremium = isPremium;
        this.creditCards = creditCards;
        this.summerOnly = summerOnly;
        this.isRecommended = isRecommended;
        this.thumbnail = thumbnail;
        this.date = date;
        this.coordinates = coordinates;
        this.distanceToUser = distanceToUser;
    }
    public Business(int id, int position, String name, String location, String shortDescription,
                    String longDescription, String phoneNumber, String email, String website,
                    String mapCoordinates, String address, String group, int groupID, String category,
                    String type, String workingHours, String price, String usefulTip,
                    boolean isPremium, boolean creditCards, boolean summerOnly, boolean isRecommended,
                    String thumbnail, String date) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.location = location;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.mapCoordinates = mapCoordinates;
        this.address = address;
        this.group = group;
        this.groupID = groupID;
        this.category = category;
        this.type = type;
        this.workingHours = workingHours;
        this.price = price;
        this.usefulTip = usefulTip;
        this.isPremium = isPremium;
        this.creditCards = creditCards;
        this.summerOnly = summerOnly;
        this.isRecommended = isRecommended;
        this.thumbnail = thumbnail;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMapCoordinates() {
        return mapCoordinates;
    }

    public void setMapCoordinates(String mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsefulTip() {
        return usefulTip;
    }

    public void setUsefulTip(String usefulTip) {
        this.usefulTip = usefulTip;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public boolean isCreditCards() {
        return creditCards;
    }

    public void setCreditCards(boolean creditCards) {
        this.creditCards = creditCards;
    }

    public boolean isSummerOnly() {
        return summerOnly;
    }

    public void setSummerOnly(boolean summerOnly) {
        this.summerOnly = summerOnly;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(float distanceToUser) {
        this.distanceToUser = distanceToUser;
    }

    public AllBusinessesActivity.Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(AllBusinessesActivity.Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

