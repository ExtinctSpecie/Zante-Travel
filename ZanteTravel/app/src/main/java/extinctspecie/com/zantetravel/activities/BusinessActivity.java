package extinctspecie.com.zantetravel.activities;

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
    }

    private void populateViewsWithData() {
        if(business != null)
        {
            //this workds only because of sugar orm
            businessID = business.getId().intValue();
            
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

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbLoadingImages);
        final List<String> gallery = new ArrayList<>();

        API.Factory.getInstance().getImagesOfBusinessWithID(businessID).enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {

                List<Images> images = response.body();

                if(images != null && !images.isEmpty())
                {

                    for(int i = 0; i < images.size(); i++) {
                        gallery.add(Information.BASE_IMAGE_URL + images.get(i).getImage());
                        Log.v("Hello", gallery.get(i));
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

                Log.v("Hello", "NO IMAGES");
                progressBar.setVisibility(View.GONE);
            }
        });
        Log.v("wtf", "NO IMAGES");
        //PABusinessGallery paBusinessGallery;

    }

    private void initVariables() {
        businessGroupID = getIntent().getIntExtra("groupID" , -1);
        businessPosition = getIntent().getIntExtra("position" , -1);
        business = AllBusinesses.getBusinessesWithGID(businessGroupID).get(businessPosition);
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
