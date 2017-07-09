package extinctspecie.com.zantetravel.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
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

    private RVAdapterBusinessesID rvAdapterBusinessesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_businesses);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        populateViewsWithData(getIntent().getIntExtra("groupID",-1));
        initSpinner();


    }




    private void populateViewsWithData(final int groupID) {

        final LinearLayout tvTodayProgress = (LinearLayout) findViewById(R.id.rvDataLoadingProgress);
        tvTodayProgress.setVisibility(View.VISIBLE);

        API.Factory.getInstance().getBusinessesWithGroupID(groupID).enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {

                try
                {
                    if(!response.body().isEmpty())
                    {
                        AllBusinesses.setAllBusinesses(response.body());

                        AllBusinesses.addBusinessesWithGID(response.body() , groupID);

                        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAllBusinesses);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rvAdapterBusinessesID = new RVAdapterBusinessesID(AllBusinesses.getAllBusinesses(),getApplicationContext());
                        rvAdapterBusinessesID.setClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = recyclerView.indexOfChild(v);
                                Intent intent = new Intent(getBaseContext(), BusinessActivity.class);
                                intent.putExtra("businessName",((TextView)v.findViewById(R.id.tvBusinessName)).getText());
                                intent.putExtra("position",position);
                                intent.putExtra("groupID",groupID);
                                startActivity(intent);
                            }
                        });

                        recyclerView.setAdapter(rvAdapterBusinessesID);

                    }
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();

                }
                tvTodayProgress.setVisibility(View.GONE);

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

    private void initSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerSortByList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortByList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

                String methodName = "get"+parent.getItemAtPosition(position).toString();
                if(!parent.getItemAtPosition(position).toString().equals("Default"))
                    sortListItems(AllBusinesses.getBusinessesWithGID(getIntent().getIntExtra("groupID",-1)) ,methodName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void sortListItems(List<Business> businesses ,String methodName)
    {
        final Method method;
        try {
            method = businesses.get(0).getClass().getMethod(methodName);
            //use reflections to call the method by the given sortBy String
            Collections.sort(businesses, new Comparator<Business>(){
                public int compare(Business obj1, Business obj2) {
                    // ## Ascending order
                    try {
                        return ((String) method.invoke(obj1)).compareToIgnoreCase((String) method.invoke(obj2));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    // To compare string values
                    // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                    // ## Descending order
                    // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                    // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
                    return 0;
                }
            });
            if(rvAdapterBusinessesID!=null)
                rvAdapterBusinessesID.changeDataSet(businesses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
    }

}
