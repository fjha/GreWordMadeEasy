/**
 @author flora jha on 27/05/17
 */
package application.android.flora.grewordmadeeasy.word;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import application.android.flora.grewordmadeeasy.R;
import application.android.flora.grewordmadeeasy.utils.Intents;
import application.android.flora.grewordmadeeasy.utils.ViewUtils;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends Activity {



    InterstitialAd mInterstitialAd;
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            System.out.print("Interstitial Ad is  showing");
        }
    }

    public static final String PREF_WELCOME_SCREEN_SHOWN = "welcomeScreenShown";
    public static final String PREF_TEXT_COLOR = "textColorInt";
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int MILLIS_DELAY_IN_SHOWING_MEANING = 1000;
    @Bind(R.id.textview_word)
    TextView wordTv;
    @Bind(R.id.textview_meaning)
    TextView meaningTv;
    @BindColor(R.color.holo_blue_light) int colorBlue;
    @BindColor(R.color.holo_green_light) int colorGreen;
    @BindColor(R.color.holo_orange_light) int colorOrange;
    @BindColor(R.color.holo_red_light) int colorRed;
    @BindColor(R.color.holo_purple) int colorPurple;
    /** Engines */
    private WordEngine wordEngine;
    private SharedPreferences prefs;
    private Runnable meaningChangeRunnable;

    private AdView mAdView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);
        MobileAds.initialize(getApplicationContext());
        AdView mAdView = (AdView) findViewById(R.id.adView);
        System.out.print("AdViewAdViewAdViewAdViewAdViewAdView "+mAdView.toString());
        Log.i(TAG, "MyClass.getView() — get item number " + mAdView.toString());
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
/*
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });*/

//        mAdView.setAdUnitId("ca-app-pub-3459004490451586/6584214757");

        ButterKnife.bind(this);

        wordEngine = new XmlWordEngine(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeColorAndSaveInPrefs(prefs.getInt(PREF_TEXT_COLOR, colorGreen));

        showWelcomeScreenIfNotShownYet();
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3459004490451586~5525857952");

    }

    private void showWelcomeScreenIfNotShownYet() {
        if (!prefs.getBoolean(PREF_WELCOME_SCREEN_SHOWN, false)) {
            AlertDialog.Builder builder = ViewUtils.newBestAlertDialogBuilder(this);
            builder.setTitle(R.string.welcomeTitle)
                    .setMessage(R.string.welcomeText)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(PREF_WELCOME_SCREEN_SHOWN, true);
                            editor.commit();
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @OnClick(R.id.button_search) void searchWord() {


        String searchQuery = wordTv.getText().toString();
        // Interstitial ad

        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        AdRequest adRequestInterstital = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequestInterstital);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        searchQuery = searchQuery.replace(' ', '+');
        Uri uri = Uri.parse("http://www.google.com/#q=define:" + searchQuery);
        Intents.maybeStartActivity(this, new Intent(Intent.ACTION_VIEW, uri));
    }

    @OnClick(R.id.textview_meaning) void nextRandom() {
        meaningTv.removeCallbacks(meaningChangeRunnable);

        WordPair wordPair = wordEngine.getRandomWord();
        wordTv.setText(wordPair.getWord());
        meaningTv.setText("");   //Empty, if user waits then show else move on

        //Run on thread with a delay of MILLIS_DELAY_IN_SHOWING_MEANING
        meaningChangeRunnable = new TextChangeRunnable(meaningTv, wordPair.getMeaningForDisplay());
        meaningTv.postDelayed(meaningChangeRunnable, MILLIS_DELAY_IN_SHOWING_MEANING);
    }

    @OnClick({
            R.id.button_blue,
            R.id.button_red,
            R.id.button_green,
            R.id.button_orange,
            R.id.button_purple,
    }) void changeColor(View v) {
        int id = v.getId();
        int color = wordTv.getCurrentTextColor();
        if (id == R.id.button_blue) {
            color = colorBlue;
        } else if (id == R.id.button_green) {
            color = colorGreen;
        } else if (id == R.id.button_orange) {
            color = colorOrange;
        } else if (id == R.id.button_red) {
            color = colorRed;
        } else if (id == R.id.button_purple) {
            color = colorPurple;
        }
        changeColorAndSaveInPrefs(color);
    }

    private void changeColorAndSaveInPrefs(@ColorInt int color) {
        wordTv.setTextColor(color);
        meaningTv.setTextColor(color);
        //Store in preferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_TEXT_COLOR, color);
        editor.commit(); // Very important to save the preference
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_color_blue) {
            changeColorAndSaveInPrefs(colorBlue);
        } else if (id == R.id.action_color_green) {
            changeColorAndSaveInPrefs(colorGreen);
        } else if (id == R.id.action_color_orange) {
            changeColorAndSaveInPrefs(colorOrange);
        }
        return super.onOptionsItemSelected(item);
    }

    private static class TextChangeRunnable implements Runnable {
        private final TextView textView;
        private final String text;

        public TextChangeRunnable(TextView textView, String text) {
            this.textView = textView;
            this.text = text;
        }

        @Override
        public void run() {
            if (textView != null) {
                textView.setText(text);
            }
        }
    }
}
// vim: set ts=4 sw=4 tw=0 et :
