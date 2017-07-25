package extinctspecie.com.zantetravel.activities;

import android.app.ActivityOptions;
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
import extinctspecie.com.zantetravel.helpers.Information;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.Image;
import extinctspecie.com.zantetravel.services.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessActivity extends AppCompatActivity {

    private int businessID;
    private Business business ;
    private String TAG = this.getClass().getSimpleName();
    Bundle bundleAnimation;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        initVariables();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(business.getName());

        populateViewsWithData();

        initGallery();

        initButtons();
    }

    private void initGallery() {

        if(AllImages.getImagesOfBusinessID(businessID) != null && !AllImages.getImagesOfBusinessID(businessID).isEmpty())
        {
            populateViewPagerWithImages();
            if(Information.isInternetAvailable(this))
            {
                getGalleryFromAPI(false);
            }
            else
            {
                stopProgressBar();
            }
        }
        else
        {
            if(Information.isInternetAvailable(this))
            {
                getGalleryFromAPI(true);
            }
            else
            {
                stopProgressBar();
            }
        }
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
            startActivity(intent,bundleAnimation);
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
        startActivity(mailer,bundleAnimation);
    }

    private void openBusinessWebsite() {
        String url = ((TextView)findViewById(R.id.tvContactWebsite)).getText().toString();

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent,bundleAnimation);
    }

    private void phoneCallBusiness() {
        String phoneNumber = ((TextView)findViewById(R.id.tvContactPhoneNumber)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent,bundleAnimation);
    }

    private void hideViewsNotNeeded()
    {
        if(!isBusiness(business))
        {

            ( findViewById(R.id.tvContactHelper)).setVisibility(View.GONE);
            ( findViewById(R.id.tvAddress)).setVisibility(View.GONE);
            ( findViewById(R.id.tvAddressHelper)).setVisibility(View.GONE);
            ( findViewById(R.id.tvWorkingHours)).setVisibility(View.GONE);
            ( findViewById(R.id.tvContactEmail)).setVisibility(View.GONE);
            ( findViewById(R.id.tvContactWebsite)).setVisibility(View.GONE);
            ( findViewById(R.id.tvContactPhoneNumber)).setVisibility(View.GONE);
            ( findViewById(R.id.tvExtraInfoHelper)).setVisibility(View.GONE);
            ( findViewById(R.id.cbSummerOnly)).setVisibility(View.GONE);
            ( findViewById(R.id.cbCreditCard)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tvUsefulTipHelper)).setText("Find More");
            ( findViewById(R.id.tvType)).setVisibility(View.GONE);
            ( findViewById(R.id.tvTypeHelper)).setVisibility(View.GONE);
            ( findViewById(R.id.tvWorkingHoursHelper)).setVisibility(View.GONE);
            ( findViewById(R.id.tvCreditCardsHelper)).setVisibility(View.GONE);
            ( findViewById(R.id.tvSummerOnlyHelper)).setVisibility(View.GONE);

            ((TextView) findViewById(R.id.tvUsefulTip)).setTextColor(getResources().getColor(R.color.DarkBlue));
            ( findViewById(R.id.tvUsefulTip)).setOnClickListener(findMore);
        }
    }
    private boolean isBusiness(Business b)
    {
        return !(b.getEmail().isEmpty()
                && b.getPhoneNumber().isEmpty()
                && b.getWebsite().isEmpty()
                && b.getWorkingHours().isEmpty());
    }

    private void populateViewsWithData() {
        if(business != null)
        {
            hideViewsNotNeeded();
            ((TextView) findViewById(R.id.tvUsefulTip)).setText(business.getUsefulTip());
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
    View.OnClickListener findMore = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), AllBusinessesActivity.class);
            //ID 9 is for Other on menu
            intent.putExtra("groupID",9);
            intent.putExtra("groupName","Other");
            intent.putExtra("searchFor",getSupportActionBar().getTitle());
            startActivity(intent,bundleAnimation);
        }
    };
    private void getGalleryFromAPI(final boolean populateViewsAfterDownload) {



        API.Factory.getInstance().getImagesOfBusinessWithID(businessID).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {

                if(!response.body().isEmpty())
                {
                    AllImages.addImages(response.body());

                    if(populateViewsAfterDownload)
                        populateViewPagerWithImages();

                }
                else
                {
                    stopProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                stopProgressBar();
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
        PABusinessGallery paBusinessGallery = new PABusinessGallery(getApplicationContext(),gallery);
        Log.v("GALLERY",gallery.size() + "");
//        paBusinessGallery.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Intent intent = new Intent(getBaseContext(), FullScreenImagesActivity.class);
//                //startActivity(intent,bundleAnimation);
//
//            }
//        });
        viewPager.setAdapter(paBusinessGallery);

        if(images.size() > 1)
        {
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tlHelperForVP);
            tabLayout.setupWithViewPager(viewPager, true);
        }

        stopProgressBar();
    }

    private void initVariables() {

        bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.trans_right_in,R.animator.trans_left_out).toBundle();
        progressBar = (ProgressBar)findViewById(R.id.pbBusinessGallery);
        business = AllBusinesses.getBusinessWithID(getIntent().getIntExtra("businessID",0));
        businessID = business.getId();
    }

    private void stopProgressBar()
    {
        if(progressBar != null)
        {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void startProgressBar()
    {
        if(progressBar != null)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
    }
}
