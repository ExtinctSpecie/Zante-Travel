package extinctspecie.com.zantetravel.activities;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import extinctspecie.com.zantetravel.fragments.MyDialogFragment;
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
    MenuItem myItem;
    DrawerLayout drawer;


    static android.app.DialogFragment newFragment;
    public static final int CUSTOM_DIALOG_ID_FOR_ABOUT_US = 1;
    public static final int CUSTOM_DIALOG_ID_FOR_ABOUT_APP = 2;
    public static final int CUSTOM_DIALOG_ID_FOR_HELP = 3;



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

    private void drawerItemSelected()
    {
        Log.v("HIII","HIII");
        if(myItem != null)
        {
            switch (myItem.getItemId())
            {
                case R.id.savedBusinesses:
                {
                    showSavedBusinesses();
                    break;
                }
                case R.id.shareApp:
                {
                    shareApp();
                    break;
                }
                case R.id.rateApp:
                {
                    showRateDialog();
                    break;
                }
                case R.id.aboutZante:
                {
                    showAboutZanteDialog();
                    break;
                }
                case R.id.aboutUs:
                {
                    showAboutUsDialog();
                    break;
                }
                case R.id.help:
                {
                    helpUser();
                    break;
                }
                case R.id.exitApp:
                {
                    exitApp();
                    break;
                }
                default:
                {
                    toastMessageShort("Something went wrong!");
                    break;
                }
            }
        }

    }

    private void exitApp() {
        finish();
    }

    private void showRateDialog() {
        Uri uri = Uri.parse("market://details?id=" + "extinctspecie.com.zantetravel");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + "extinctspecie.com.zantetravel")));
        }
    }
    private void helpUser() {

    }

    private void showAboutUsDialog() {

        // MyDialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment prev = getFragmentManager().findFragmentByTag("shownDialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        android.app.DialogFragment newFragment = MyDialogFragment.newInstance(CUSTOM_DIALOG_ID_FOR_ABOUT_US);

        newFragment.setCancelable(true);

        newFragment.show(ft, "shownDialog");

    }

    private void showSavedBusinesses() {

        List<Business> favBusinesses = AllFavoriteBusinesses.getFavBusinesses();

        if(favBusinesses != null)
        {
            Intent intent = new Intent(getBaseContext(), FavBusinessesActivity.class);
            startActivity(intent, bundleAnimation);
        }
        else
        {
            toastMessageShort("No Saved Items");
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
            showAboutZanteDialog();
        }
        //overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
    }
    private void showAboutZanteDialog()
    {
        Log.v("HELLO","HELLLO");
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment prev = getFragmentManager().findFragmentByTag("shownDialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        newFragment = MyDialogFragment.newInstance(CUSTOM_DIALOG_ID_FOR_ABOUT_APP);

        newFragment.setCancelable(true);

        newFragment.show(ft, "shownDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.addDrawerListener(drawerListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.

        menuItemSelectedName = item.getTitle().toString();

        myItem = item;
        
        drawer.closeDrawer(GravityCompat.START);
        //added drawer listener to avoid "LAG"

        return true;
    }
    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if(myItem != null)
                myItem = null;
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            drawerItemSelected();
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
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
    private void toastMessageLong(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }
    private void toastMessageShort(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
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
