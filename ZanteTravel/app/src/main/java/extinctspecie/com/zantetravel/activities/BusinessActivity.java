package extinctspecie.com.zantetravel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.services.API;

public class BusinessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int groupID = getIntent().getIntExtra("groupID" , -1);
        getIntent().getStringExtra("businessName");
        int pos = getIntent().getIntExtra("position" , -1);
        if(pos > -1)
        {
            ((TextView)findViewById(R.id.tvGroupID)).setText(AllBusinesses.getBusinessesWithGID(groupID).get(pos).getLongDescription());
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
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
