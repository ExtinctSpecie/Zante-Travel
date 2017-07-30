package extinctspecie.com.zantetravel.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.RVAdapterFavorites;
import extinctspecie.com.zantetravel.data.AllFavoriteBusinesses;
import extinctspecie.com.zantetravel.models.Business;

public class FavBusinessesActivity extends AppCompatActivity {

    String TOOLBAR_TITLE = "Favorites";
    Bundle bundleAnimation;
    RVAdapterFavorites rvAdapterFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_businesses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(TOOLBAR_TITLE);


        initData();
        populateViews();
    }

    private void populateViews() {

        List<Business> businessList = AllFavoriteBusinesses.getFavBusinesses();
        if(businessList != null && businessList.size() > 0)
        {
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvFavoriteBusinesses);

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            rvAdapterFavorites = new RVAdapterFavorites(businessList, getApplicationContext());

            rvAdapterFavorites.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //int position = recyclerView.indexOfChild(v);
                    int position = recyclerView.getChildLayoutPosition(v);

                    Business business = rvAdapterFavorites.getBusiness(position);

                    Intent intent = new Intent(getBaseContext(), BusinessActivity.class);
                    intent.putExtra("businessID", business.getId());
                    startActivity(intent,bundleAnimation);
                }
            });
            recyclerView.setAdapter(rvAdapterFavorites);
        }
        else
        {
            onBackPressed();
        }

    }

    private void initData() {
        bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.trans_right_in,R.animator.trans_left_out).toBundle();
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
    protected void onResume() {

        populateViews();
        super.onResume();
    }
}
