package extinctspecie.com.zantetravel.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.PABusinessGallery;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.data.AllImages;
import extinctspecie.com.zantetravel.models.Coordinates;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.Image;
import extinctspecie.com.zantetravel.services.API;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessActivity extends AppCompatActivity {

    private int businessID;
    private Business business ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        initVariables();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(business.getName());

        populateViewsWithData();
        getGalleryFromAPI();
        initButtons();
    }

    private void initButtons() {
        (findViewById(R.id.btnBusinessCall)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCallBusiness();
            }
        });
        (findViewById(R.id.btnBusinessEmail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailToBusiness();
            }
        });
        (findViewById(R.id.btnBusinessWebsite)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBusinessWebsite();
            }
        });
        (findViewById(R.id.btnBusinessLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBusinessLocation();
            }
        });
    }

    private void openBusinessLocation() {
        float latitude = -1;
        float longitude = -1;
        if(!business.isPremium())
        {
            if(business.getCoordinates() != null)
            {
                Log.v("hello","location is saved");
                latitude = business.getCoordinates().getLatitude();
                longitude = business.getCoordinates().getLongitude();
            }
            else
            {
                //Split string into longitude and altitude
                String strCoordinates[] = business.getMapCoordinates().split(",");

                //if not exactly 2 strings throw error
                //that means the data provided from the api is wrong
                if (strCoordinates.length != 2) {
                    throw new NullPointerException("Coordinates does not have 2 properties ( WRONG COORDINATES CHECK API )");
                }

                String strLongitude = strCoordinates[1].trim();
                String strLatitude = strCoordinates[0].trim();

                longitude = Float.parseFloat(strLongitude);
                latitude = Float.parseFloat(strLatitude);
            }

            String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=17&q=%f,%f", latitude, longitude, latitude, longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
        else
        {
            (findViewById(R.id.btnBusinessLocation)).setActivated(false);
        }
        business.setCoordinates(new Coordinates(latitude , longitude));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.insertOrUpdate(business);

        realm.commitTransaction();
    }

    private void sendEmailToBusiness() {
        String subject = "Customer Email";
        String message = "Greetings , \n";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{((TextView)findViewById(R.id.tvContactEmail)).getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }

    private void openBusinessWebsite() {
        String url = ((TextView)findViewById(R.id.tvContactWebsite)).getText().toString();

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void phoneCallBusiness() {
        String phoneNumber = ((TextView)findViewById(R.id.tvContactPhoneNumber)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void populateViewsWithData() {
        if(business != null)
        {
            ((TextView) findViewById(R.id.tvDescription)).setText(business.getLongDescription());
            ((TextView) findViewById(R.id.tvLocation)).setText(business.getLocation());
            ((TextView) findViewById(R.id.tvAddress)).setText(business.getAddress());
            ((TextView) findViewById(R.id.tvWorkingHours)).setText(business.getWorkingHours());
            ((TextView) findViewById(R.id.tvContactPhoneNumber)).setText(business.getPhoneNumber());
            ((TextView) findViewById(R.id.tvContactEmail)).setText(business.getEmail());
            ((TextView) findViewById(R.id.tvContactWebsite)).setText(business.getWebsite());
            ((CheckBox)findViewById(R.id.cbCreditCard)).setChecked(business.isCreditCards());
            ((CheckBox)findViewById(R.id.cbSummerOnly)).setChecked(business.isSummerOnly());
        }

    }

    private void getGalleryFromAPI() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbBusinessGallery);


        API.Factory.getInstance().getImagesOfBusinessWithID(businessID).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {

                if(!response.body().isEmpty())
                {
                    AllImages.addImages(response.body());

                    populateViewPagerWithImages();

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void populateViewPagerWithImages() {

        List<Image> images = AllImages.getImagesOfBusinessID(businessID);

        List<String> gallery = new ArrayList<>();

        for(int i = 0; i < images.size(); i++) {
            gallery.add(images.get(i).getImageURL());
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpGallery);
        viewPager.setAdapter(new PABusinessGallery(getApplicationContext(),gallery));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tlHelperForVP);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void initVariables() {

        business = AllBusinesses.getBusinessWithID(getIntent().getIntExtra("businessID",0));
        businessID = business.getId();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
    }
}
