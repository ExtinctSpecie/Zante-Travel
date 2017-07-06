package extinctspecie.com.zantetravel.activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.LVAdapterMainMenu;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.helpers.TypeFaces;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.services.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String TAG = this.getClass().getSimpleName();
    HashMap<Integer,Integer> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getDataFromServer();
        createMenuMap();
        initTypeFaces();
        initToolbarAndDrawer();
        initListViewMenu();

    }

    private void getDataFromServer() {


        API.Factory.getInstance().getAllBusinesses().enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {

                try
                {
                    if(!response.body().isEmpty())
                    {
                        AllBusinesses.setAllBusinesses(response.body());
                        Log.v(TAG,AllBusinesses.getAllBusinesses().get(0).getName());
                    }
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                Log.v(TAG, "Error While Getting Data" + t);
            }
        });

    }

    private void createMenuMap() {

        menuItems = new HashMap<>();
        menuItems.put(0,R.id.aboutZante);
        menuItems.put(1,R.id.attractions);
        menuItems.put(2,R.id.accommodation);
        menuItems.put(3,R.id.food);
        menuItems.put(4,R.id.entertainment);
        menuItems.put(5,R.id.shopping);
        menuItems.put(6,R.id.activities);
        menuItems.put(7,R.id.beaches);
        menuItems.put(8,R.id.rentals);
        menuItems.put(9,R.id.other);
        menuItems.put(10,R.id.emergencyHelp);

    }

    private void initTypeFaces() {

        TypeFaces.initializeFonts(getApplicationContext());

        Thread one = new Thread() {
            public void run() {
                try {

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        one.start();
    }


    private void initToolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initListViewMenu() {

        ListView listView = (ListView) findViewById(R.id.lvMainMenu);
        listView.setAdapter(new LVAdapterMainMenu(this));
        listView.setOnItemClickListener(onMainMenuItemClickListener);

    }
    ListView.OnItemClickListener onMainMenuItemClickListener = new ListView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            menuItemSelected(position);
        }
    };
    public void menuItemSelected(int item)
    {
        try {

            int menuID = -1;

            if(menuItems.containsKey(item))
            {
                menuID = item;
            }
            else if(menuItems.containsValue(item))
            {
                for (Map.Entry<Integer, Integer> entry : menuItems.entrySet()) {
                    if (entry.getValue().equals(item))
                    {
                        menuID = entry.getKey();
                    }
                }
            }
            goToNextActivity(menuID);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private void goToNextActivity(int menuID) {
        if(menuID > -1)
        {
            Intent intent = new Intent(getBaseContext(), AllBusinessesActivity.class);
            intent.putExtra("menuID",menuID);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        menuItemSelected(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
