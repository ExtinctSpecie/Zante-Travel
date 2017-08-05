package extinctspecie.com.zantetravel.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.SearchView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.adapters.RVAdapterBusinessesID;
import extinctspecie.com.zantetravel.data.AllBusinesses;
import extinctspecie.com.zantetravel.models.Coordinates;
import extinctspecie.com.zantetravel.helpers.Information;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.services.API;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBusinessesActivity extends AppCompatActivity {

    private RVAdapterBusinessesID rvAdapterBusinessesID;
    private int businessGroupID;
    final static int ACCESS_LOCATION_PERMISSION = 99;
    ProgressDialog progressDialog;
    LinearLayout rvLoadingData;
    AlertDialog.Builder dialog;
    Bundle bundleAnimation;
    Menu actionBarMenu;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_businesses);

        businessGroupID = getIntent().getIntExtra("groupID", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setActionBarTitle();

        initData();
        initProgressDialog();
        initSpinner();

    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
    }

    private void setActionBarTitle() {
        getSupportActionBar().setTitle(getIntent().getStringExtra("groupName"));
    }
    private void initData()
    {
        bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.trans_right_in,R.animator.trans_left_out).toBundle();

        rvLoadingData = (LinearLayout) findViewById(R.id.rvDataLoadingProgress);
        rvLoadingData.setVisibility(View.VISIBLE);

        if(AllBusinesses.getBusinessesWithGID(businessGroupID) != null && !AllBusinesses.getBusinessesWithGID(businessGroupID).isEmpty())
        {
            populateViews();
            if(Information.isInternetAvailable(this))
            {
                getBusinessesFromAPI(false);
            }
            else
            {
                rvLoadingData.setVisibility(View.GONE);
            }
        }
        else
        {
            if(Information.isInternetAvailable(this))
            {
                getBusinessesFromAPI(true);
            }
            else
            {
                rvLoadingData.setVisibility(View.GONE);
            }
        }
    }

    public void populateViews()
    {
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAllBusinesses);

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            rvAdapterBusinessesID = new RVAdapterBusinessesID(AllBusinesses.getBusinessesWithGID(businessGroupID), getApplicationContext());

            rvAdapterBusinessesID.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //int position = recyclerView.indexOfChild(v);
                    int position = recyclerView.getChildLayoutPosition(v);

                    Business business = rvAdapterBusinessesID.getBusiness(position);
                    business.printSelf();
                    Intent intent = new Intent(getBaseContext(), BusinessActivity.class);
                    intent.putExtra("businessID", business.getId());
                    startActivity(intent,bundleAnimation);
                }
            });
            recyclerView.setAdapter(rvAdapterBusinessesID);
            rvLoadingData.setVisibility(View.GONE);

            if(getIntent().getStringExtra("searchFor")!=null)
                onQueryTextListener.onQueryTextChange(getIntent().getStringExtra("searchFor"));
    }

    private void getBusinessesFromAPI(final boolean populateViesAfterDownload) {

        API.Factory.getInstance().getBusinessesWithGroupID(businessGroupID).enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {

                try {
                    if (!response.body().isEmpty()) {

                        //saves data
                        AllBusinesses.addBusinesses(response.body());
                        //populates views
                        if(populateViesAfterDownload)
                            populateViews();
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                Log.v("error: ", "Error While Getting Data" + t);
                rvLoadingData.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        actionBarMenu = menu;

        final MenuItem item = menu.findItem(R.id.searchMenu);

        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(onQueryTextListener);

        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v) {

                setItemsVisibility(menu, item, false);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItemsVisibility(menu, item, true);
                return false;
            }
        });

        if(getIntent().getStringExtra("searchFor") != null)
        {
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocus();
            searchView.setQuery(getIntent().getStringExtra("searchFor") , false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId() == R.id.searchText)
        {
            searchView.setIconified(false);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i=0; i<menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }

        Log.v("Hello","hello");
        searchView.requestFocusFromTouch();
    }
    //Search text Listener
    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            rvAdapterBusinessesID.filter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            rvAdapterBusinessesID.filter(newText);
            return false;
        }
    };


    private void initSpinner() {

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerSortByList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortByList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.my_spinner_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String methodName = getMethodName(parent.getItemAtPosition(position).toString());
                TextView textView;
                textView = ((TextView) spinner.getSelectedView());

                if(textView!=null)
                   textView.setTextColor(getResources().getColor(R.color.White));
                selectSortingMethod(methodName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void selectSortingMethod(String methodName) {

        switch (methodName) {
            case "getDistance": {
                //method name is called "getDistanceToUser"
                new Distances(AllBusinesses.getBusinessesWithGID(businessGroupID), methodName);
                break;
            }
            case "getDefault": {
                resetSorting();
                break;
            }
            default: {
                sortListitems(AllBusinesses.getBusinessesWithGID(businessGroupID), methodName);
                break;
            }
        }
    }

    private void resetSorting() {
        if (rvAdapterBusinessesID != null)
            rvAdapterBusinessesID.resetData();
    }

    private void sortListitems(List<Business> businesses, final String methodName) {

        final Method method;

        try {
            method = businesses.get(0).getClass().getMethod(methodName);

            Collections.sort(businesses, new Comparator<Business>() {
                public int compare(Business obj1, Business obj2) {
                    // ## Ascending order
                    try {
                        if (methodName.startsWith("get")) {
                            if (methodName.equals("getDistanceToUser")) {
                                return (Math.round(obj1.getDistanceToUser() - obj2.getDistanceToUser()));
                            } else
                                return ((String) method.invoke(obj1)).compareToIgnoreCase((String) method.invoke(obj2));
                        } else {
                            int b1 = obj1.isRecommended() ? 1 : 0;
                            int b2 = obj2.isRecommended() ? 1 : 0;

                            return b2 - b1;

                        }
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Realm.getDefaultInstance().close();
        if (rvAdapterBusinessesID != null)
            rvAdapterBusinessesID.changeDataSet(businesses);
    }

    public String getMethodName(String strMethodName) {
        String methodName;


        if (strMethodName.equals("Recommended"))
            methodName = "is" + strMethodName;
        else
            methodName = "get" + strMethodName;


        return methodName;
    }
    private void startProgressDialog(String message, boolean cancelable)
    {

        if (progressDialog != null)
        {
            progressDialog.setMessage(message);
            progressDialog.setCancelable(cancelable);
            progressDialog.show();
        }
    }
    public void stopProgressDialog()
    {
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
    public void updateProgressDialog(String message)
    {
        if(progressDialog != null)
        {
            progressDialog.setMessage(message);
        }
    }
    private void showDialogToProvideGPS() {


        dialog.setMessage(AllBusinessesActivity.this.getResources().getString(R.string.gpsNotEnabled));

        dialog.setPositiveButton(AllBusinessesActivity.this.getResources().getString(R.string.openLocationSettings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                AllBusinessesActivity.this.startActivity(myIntent,bundleAnimation);
            }
        });
        dialog.setNegativeButton(AllBusinessesActivity.this.getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                ((Spinner) findViewById(R.id.spinnerSortByList)).setSelection(0);
                stopProgressDialog();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((Spinner) findViewById(R.id.spinnerSortByList)).setSelection(0);
                stopProgressDialog();
            }
        });
        dialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case ACCESS_LOCATION_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    selectSortingMethod("getDistance");
                }
                else
                {
                    ((Spinner) findViewById(R.id.spinnerSortByList)).setSelection(0);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        //Intent intent = new Intent(getBaseContext(), MainActivity.class);
        //startActivity(intent);

        super.onBackPressed();

        //incoming activity // outgoing activity
        overridePendingTransition(R.anim.left_in,R.anim.right_out);




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
    }


/*
*AsyncTask class for doing calculations of distances between the user and the business.
*/

    private class Distances extends AsyncTask<List<Business>, Integer, List<String>> implements LocationListener{


        List<Business> businesses;

        LocationManager locationManager;
        Location userLocation;



        private Distances(List<Business> businesses, String methodName) {
            this.businesses = businesses;
            initPreparations();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //start dialog when trying to get the users location
            updateProgressDialog("Calculating distances please wait...");
            // locationManager.removeUpdates(locationListener);

        }

        @Override
        protected List<String> doInBackground(List<Business>... params) {

            for (Business business : params[0]) {

                //Split string into longitude and altitude
                String strCoordinates[] = business.getMapCoordinates().split(",");

                //if not exactly 2 strings throw error
                //that means the data provided from the api is wrong
                if (strCoordinates.length != 2) {
                    throw new NullPointerException("Coordinates does not have 2 properties ( WRONG COORDINATES CHECK API )");
                }



                String strLatitude = strCoordinates[0].trim();
                String strLongitude = strCoordinates[1].trim();



                business.setCoordinates(new Coordinates(Float.parseFloat(strLatitude), Float.parseFloat(strLongitude)));

                Coordinates userCoordinates = new Coordinates((float) userLocation.getLatitude() , (float) userLocation.getLongitude());

                business.setDistanceToUser(distanceToUser(userCoordinates ,business.getCoordinates()));
            }

            AllBusinesses.addBusinesses(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            sortListitems(AllBusinesses.getBusinessesWithGID(businessGroupID), "getDistanceToUser");
            stopProgressDialog();
        }
        private  float distanceToUser(Coordinates startingPoint , Coordinates endingPoint)
        {
            double theta = startingPoint.getLongitude() - endingPoint.getLongitude();
            double dist = Math.sin(deg2rad(startingPoint.getLatitude())) * Math.sin(deg2rad(endingPoint.getLatitude())) + Math.cos(deg2rad(startingPoint.getLatitude())) * Math.cos(deg2rad(endingPoint.getLatitude())) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;

            //convert it to KM
            dist = dist * 1.609344;

            return (float) dist;

        }

	    //This function converts decimal degrees to radians
        private  double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }
        //This function converts radians to decimal degrees
        private  double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
        }

        private void initPreparations() {

            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION ,  Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_LOCATION_PERMISSION);
                return;
            }

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,50, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER ,100,50,this);

            startProgressDialog("Getting your location..." , false);

            if (!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))
            {

                dialog = new AlertDialog.Builder(AllBusinessesActivity.this);
                dialog.setCancelable(false);
                showDialogToProvideGPS();
            }
        }


        //On location changed listener ( interface )
        @Override
        public void onLocationChanged(Location location) {
            userLocation = location;
            locationManager.removeUpdates(this);
            execute(businesses);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            //set sorting to default again
        }
    }
}
