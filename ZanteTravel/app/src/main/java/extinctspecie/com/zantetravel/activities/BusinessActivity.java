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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.PABusinessGallery;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.helpers.Information;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.Images;
import extinctspecie.com.zantetravel.services.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessActivity extends AppCompatActivity {
    private int businessGroupID;
    private int businessPosition;
    private int businessID;
    private Business business ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getIntent().getStringExtra("businessName"));


        initVariables();
        populateViewsWithData();
        initViewPager();
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
        if(business.isPremium())
        {
            float latitude = 41.328970f;
            float longitude = 19.818195f;
            if(business.getCoordinates() != null)
            {
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


                String strLongitude = strCoordinates[0].trim();
                String strLaitude = strCoordinates[1].trim();
                latitude = Float.parseFloat(strLaitude);
                longitude = Float.parseFloat(strLaitude);
            }

            String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=17&q=%f,%f", latitude, longitude, latitude, longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
        else
        {
            (findViewById(R.id.btnBusinessLocation)).setActivated(false);
        }
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
            //this workds only because of sugar orm
            businessID = business.getId();
            
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

    private void initViewPager() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbBusinessGallery);
        final List<String> gallery = new ArrayList<>();

        API.Factory.getInstance().getImagesOfBusinessWithID(businessID).enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {

                List<Images> images = response.body();

                if(images != null && !images.isEmpty())
                {

                    for(int i = 0; i < images.size(); i++) {
                        gallery.add(Information.BASE_IMAGE_URL + images.get(i).getImage());
                    }

                    ViewPager viewPager = (ViewPager) findViewById(R.id.vpGallery);
                    viewPager.setAdapter(new PABusinessGallery(getApplicationContext(),gallery));

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tlHelperForVP);
                    tabLayout.setupWithViewPager(viewPager, true);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Images>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        //PABusinessGallery paBusinessGallery;

    }

    private void initVariables() {
        businessGroupID = getIntent().getIntExtra("groupID" , -1);
        businessPosition = getIntent().getIntExtra("position" , -1);
        business = AllBusinesses.getBusiness(businessGroupID , businessPosition);
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
