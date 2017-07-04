package extinctspecie.com.zantetravel.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.LVAdapterAllBusinesses;

public class AllBusinessesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_businesses);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        populateViewsWithData();

    }


    private void populateViewsWithData() {

        ListView lvAllBusinesses = (ListView) findViewById(R.id.lvAllBusinesses);

        lvAllBusinesses.setAdapter(new LVAdapterAllBusinesses(getIntent().getIntExtra("menuID" , -1), getApplicationContext()));

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
