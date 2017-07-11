package extinctspecie.com.zantetravel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import extinctspecie.com.zantetravel.R;

public class AboutTownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_town);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("About Zakynthos");

        loadHtmlToWebView();

    }

    private void loadHtmlToWebView() {
        ((WebView) findViewById(R.id.wvAboutTown)).loadData("<p align=\"center\">&nbsp;</p>\n" +
                "<p align=\"center\">&nbsp;</p>\n" +
                "<p align=\"center\"><span style=\"color: #2b7ed2;\"><span style=\"font-size: large;\"><strong>About Zakynthos</strong></span></span></p>\n" +
                "<p align=\"left\"><br /> Zakynthos is the southernmost and <strong>third largest </strong>in both size and population of the <strong>Ionian</strong> <strong>islands</strong>. It is situated 8.5 nautical miles south of Kefallonia, 9.5 nautical miles west of the Peloponnese and approximately <strong>300 k</strong><strong>m</strong> west of the capital <strong>of Greece, Athens</strong>.<br /> <br /> Its <strong>geographical</strong> position ensures <strong>easy</strong> <strong>travel</strong> to the <strong>other</strong> <strong>islands</strong> and <strong>even</strong> the <strong>mainland</strong> <strong>of</strong> <strong>Greece</strong>. A short journey across the <strong>Peloponnese</strong> brings one to the ancient city of <strong>Olympia</strong>, the <strong>birthplace</strong> and original venue of the <strong>Olympic</strong> <strong>Games</strong>. Due to the islands location, it is an ideal base to link up to the larger cities of Greece such as Patras, Athens and Thesaloniki.</p>\n" +
                "<p align=\"left\"><br /> An <strong>island</strong> full of <strong>contrasts</strong>, Zakynthos consists of <strong>mainly</strong> <strong>woodland</strong>, with an abundance of pine tree covered mountains and fertile plains. In the north, east and south numerous picturesque beaches can be found, whereas in the west the imposing, <strong>rocky</strong> <strong>landscape</strong> has a number of <strong>sea</strong> <strong>caves</strong> such as the famous Blue Caves on the north-west of the island.</p>\n" +
                "<p align=\"left\">The <strong>climate</strong> in Zakynthos is mild <strong>mediterranean</strong> with both heavy rainfall during the winter months and brilliant <strong>sunshine</strong> in the <strong>summer</strong>. The rich vegetation is probably due to this, and has resulted in the nicknames of <strong>Fior di Levante</strong> (<strong>Flower of the East</strong>) and <strong>Iliessa</strong> (<strong>F</strong><strong>ull of </strong><strong>W</strong><strong>oods</strong>) by the Venetians and Omiros respectively.</p>\n" +
                "<p align=\"left\">It is productive island agriculturally, supported mainly by the cultivation of <strong>olives</strong>, <strong>raisins</strong>, <strong>citrus</strong> <strong>fruits</strong>. Apart from this, the <strong>main</strong> <strong>source</strong> <strong>of</strong> local <strong>income</strong> is from the recently developed business of <strong>tourism</strong>. The <strong>island</strong> has a wealth of history as for many centuries it was the <strong>crossroads</strong> for <strong>numerous</strong> <strong>nations</strong> and <strong>cultures</strong>.</p>\n" +
                "<h2><span style=\"color: #4b67a1;\">&nbsp;</span></h2>","text/html","UTF-8");
        ((WebView) findViewById(R.id.wvAboutTown)).getSettings().setTextZoom(80);
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
        //finish();
    }
}
