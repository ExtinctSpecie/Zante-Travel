package extinctspecie.com.zantetravel.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.LVAdapterAllBusinesses;
import extinctspecie.com.zantetravel.adapters.RVAdapterBusinessesID;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.services.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        final LinearLayout tvTodayProgress = (LinearLayout) findViewById(R.id.rvDataLoadingProgress);
        tvTodayProgress.setVisibility(View.VISIBLE);

        API.Factory.getInstance().getBusinessesWithGroupID(0).enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {

                try
                {
                    if(!response.body().isEmpty())
                    {
                        AllBusinesses.setAllBusinesses(response.body());

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAllBusinesses);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new RVAdapterBusinessesID(AllBusinesses.getAllBusinesses(),getApplicationContext()));
                        tvTodayProgress.setVisibility(View.GONE);
                    }
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                    tvTodayProgress.setVisibility(View.GONE);
                }



            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                Log.v("error: ", "Error While Getting Data" + t);
            }
        });

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
