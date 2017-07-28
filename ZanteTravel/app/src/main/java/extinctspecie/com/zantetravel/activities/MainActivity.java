package extinctspecie.com.zantetravel.activities;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.LVAdapterMainMenu;
import extinctspecie.com.zantetravel.data.AllFavoriteBusinesses;
import extinctspecie.com.zantetravel.helpers.TypeFaces;
import extinctspecie.com.zantetravel.models.Business;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String TAG = this.getClass().getSimpleName();
    HashMap<Integer,Integer> menuItems;
    String menuItemSelectedName = "";
    Bundle bundleAnimation;

    static final int CUSTOM_DIALOG_ID_FOR_ABOUT_US = 1;
    static final int CUSTOM_DIALOG_ID_FOR_ABOUT_APP = 2;
    static final int CUSTOM_DIALOG_ID_FOR_HELP = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        initVars();
        initTypeFaces();
        initToolbarAndDrawer();
        initListViewMenu();
    }
    private void initVars() {

        menuItems = new HashMap<>();
        menuItems.put(0,R.id.aboutZante);
        bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.trans_right_in,R.animator.trans_left_out).toBundle();
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

    private void drawerItemSelected(int itemId)
    {
        switch (itemId)
        {
            case R.id.aboutZante:
            {
                startAboutZanteAct();
            }
            case R.id.savedBusinesses:
            {
                List<Business> favBusinesses = AllFavoriteBusinesses.getFavBusinesses();

                Toast.makeText(this,favBusinesses.size()+" SIZE ",Toast.LENGTH_LONG).show();
            }

        }
    }
    private void menuItemSelected(int position)
    {
        try {

            goToNextActivity(position);
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
        //#Slide Animation transition

        if(menuID > 0)
        {
            Intent intent = new Intent(getBaseContext(), AllBusinessesActivity.class);
            intent.putExtra("groupID",menuID);
            intent.putExtra("groupName",menuItemSelectedName);
            startActivity(intent, bundleAnimation);

        }
        else
        {
            startAboutZanteAct();
        }
        //overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
    }
    private void startAboutZanteAct()
    {
        startActivity(new Intent(getBaseContext(),AboutTownActivity.class) , bundleAnimation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        menuItemSelectedName = item.getTitle().toString();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        drawerItemSelected(item.getItemId());

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
