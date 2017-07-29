package extinctspecie.com.zantetravel.activities;


import android.app.ActivityOptions;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import extinctspecie.com.zantetravel.fragments.AboutUsDialogFragment;
import extinctspecie.com.zantetravel.helpers.TypeFaces;
import extinctspecie.com.zantetravel.models.Business;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , ShareActionProvider.OnShareTargetSelectedListener
{

    String TAG = this.getClass().getSimpleName();
    HashMap<Integer,Integer> menuItems;
    String menuItemSelectedName = "";
    Bundle bundleAnimation;
    private ShareActionProvider mShareActionProvider;

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
        drawer.addDrawerListener(toggle);
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
                break;
            }
            case R.id.savedBusinesses:
            {
                showSavedBusinesses();
                break;
            }
            case R.id.aboutUs:
            {
                showAboutUsDialog();
                break;
            }
            case R.id.shareApp:
            {
                shareApp();
                break;
            }
            case R.id.help:
            {
                helpUser();
                break;
            }
        }
    }

    private void helpUser() {
        
    }

    private void showAboutUsDialog() {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment prev = getFragmentManager().findFragmentByTag("shownDialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = AboutUsDialogFragment.newInstance();

        newFragment.setCancelable(true);

        newFragment.show(ft, "shownDialog");

    }

    private void showSavedBusinesses() {
        List<Business> favBusinesses = AllFavoriteBusinesses.getFavBusinesses();

        Toast.makeText(this,favBusinesses.size()+" SIZE ",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(), FavBusinessesActivity.class);

        startActivity(intent, bundleAnimation);
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
    public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
        Toast.makeText(this, intent.getComponent().toString(),
                Toast.LENGTH_LONG).show();
        return false;
    }
    // Call to update the share intent
    private void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
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
