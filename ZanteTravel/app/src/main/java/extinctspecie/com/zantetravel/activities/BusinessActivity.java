package extinctspecie.com.zantetravel.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.PABusinessGallery;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.models.Business;

public class BusinessActivity extends AppCompatActivity {
    private int businessGroupID;
    private int businessPosition;
    private Business business ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getIntent().getStringExtra("businessName"));

        ((CheckBox)findViewById(R.id.cbCreditCard)).setChecked(true);
        initVariables();
        initViewPager();



    }

    private void initViewPager() {

        PABusinessGallery paBusinessGallery;
        List<String> gallery = new ArrayList<>();
        gallery.add("http://i.imgur.com/DvpvklR.png");
        gallery.add("http://www.intelligence-airbusds.com/files/pmedia/public/r33698_9_satellite_image_spot7_fidji_20140703.jpg");
        gallery.add("http://cdn.ebaumsworld.com/mediaFiles/picture/2334885/83773192.jpg");

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpGallery);
        viewPager.setAdapter(new PABusinessGallery(this,gallery));
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
