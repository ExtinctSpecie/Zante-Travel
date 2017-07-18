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
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.LVAdapterMainMenu;
import extinctspecie.com.zantetravel.helpers.TypeFaces;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String TAG = this.getClass().getSimpleName();
    HashMap<Integer,Integer> menuItems;
    String menuItemSelectedName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        createMenuMap();
        initTypeFaces();
        initToolbarAndDrawer();
        initListViewMenu();

    }

    private void getDataFromServer() {



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

            menuItemSelectedName = ((TextView) view.findViewById(R.id.tvMenuItem)).getText().toString();
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
        //if menu = 0
        //that means we need to start another activity ( for info )
        //static data for info activity is fine since it won't get any update
        if(menuID > 0)
        {
            Intent intent = new Intent(getBaseContext(), AllBusinessesActivity.class);
            intent.putExtra("groupID",menuID);
            intent.putExtra("groupName",menuItemSelectedName);
            startActivity(intent);
        }
        else
        {
            startActivity(new Intent(getBaseContext(),AboutTownActivity.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        menuItemSelectedName = item.getTitle().toString();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        menuItemSelected(item.getItemId());

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

    @Override
    protected void onResume() {
        super.onResume();
        menuItemSelectedName = "";
    }
}
