package com.syed.ary_news;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
     TabLayout myTab;
     ViewPager myPage;
     DrawerLayout drawer;
     Menu menu;
    webview webviewObj;
     PageAdapter adapter;
    InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    boolean isAddLoaded = true;
    AdView adView, bottomAd;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // This is auto generated code..................

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                } else if (isAddLoaded) {
                    interstitialAd.show();
                    loadRewardedVideoAd();
                } else {
                    loadRewardedVideoAd();
                    loadIntertcicialAdd();
                }
            }
        }, 1000 * 60 * 3);

        adView = findViewById(R.id.home_activity_banner_adview);
        MyApplication.setAddView(this, adView);

        bottomAd = findViewById(R.id.bottom_adview);
        MyApplication.setAddView(this, bottomAd);
        loadIntertcicialAdd();
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);
        loadRewardedVideoAd();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Auto generated code ends here

        myPage=findViewById(R.id.viewpager);
        myPage.setAdapter(new PageAdapter(getSupportFragmentManager()));

        myTab=findViewById(R.id.tablayout);
        myTab.setupWithViewPager(myPage);

        myTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                myPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onBackPressed()
    {

        /* If drawer is opened, back button will close it.
           Otherwise, app will be closed.
         */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else if(myPage.getCurrentItem()!=0)
        {
             myPage.setCurrentItem(0);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //Handle option menu item clicks here.
        int id = item.getItemId();

        if (id == R.id.livetv)
        {
            myPage.setCurrentItem(0);
            return true;
        }
        else if (id == R.id.mute)
        {
            if (f)
            {
                UnMuteAudio();

            }
            else
            {
                MuteAudio();

            }
        }

        return super.onOptionsItemSelected(item);
    }

    boolean f = true;

    public void MuteAudio()
    {
        f = true;
        AudioManager mAlramMAnager = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);

        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }

        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.mute36));


    }
    public void UnMuteAudio()
    {
        f = false;
        AudioManager mAlramMAnager = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);

            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                    AudioManager.FLAG_SHOW_UI);

        }
        else
        {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);

            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                    AudioManager.FLAG_SHOW_UI);

        }


        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.unmute36));


    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.live_drawer)
        {
            myPage.setCurrentItem(0);
        }
        else if (id == R.id.home_drawer)
        {
            myPage.setCurrentItem(1);
        }
        else if (id == R.id.business_drawer)
        {
            myPage.setCurrentItem(2);
        }
        else if (id == R.id.internationa_drawer)
        {
            myPage.setCurrentItem(3);
        }

        else if (id == R.id.sports_drawer)
        {
            myPage.setCurrentItem(4);
        }
        else if (id == R.id.technology_drawer)
        {
            myPage.setCurrentItem(5);
        }
        else if (id == R.id.health_drawer)
        {
            myPage.setCurrentItem(6);
        }
        else if (id == R.id.lifestyle_drawer)
        {
            myPage.setCurrentItem(7);
        }
        else if (id == R.id.videos_drawer)
        {
            myPage.setCurrentItem(8);
        }
        else if (id == R.id.special_drawer)
        {
            myPage.setCurrentItem(9);
        }
        else if (id == R.id.blogs_drawer)
        {
            myPage.setCurrentItem(10);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class PageAdapter extends FragmentPagerAdapter
    {
        String category[]={"Live TV","Home","Business","International","Sports","Technology","Health","Life Style","Videos","ARY Special","Blogs"};
        String links[]={"","https://arynews.tv/en/","https://arynews.tv/en/category/business/","https://arynews.tv/en/category/international-2/","https://arysports.tv/category/sports/","https://arynews.tv/en/category/sci-techno/","https://arynews.tv/en/category/health-2/","https://arynews.tv/en/category/lifestyle/","https://videos.arynews.tv/","https://arynews.tv/en/category/ary-special/","https://blogs.arynews.tv/"};

        webview[] webviews={null,new webview().setLink(links[1]),new webview().setLink(links[2]),new webview().setLink(links[3]),new webview().setLink(links[4]),new webview().setLink(links[5]),new webview().setLink(links[6]),new webview().setLink(links[7]),new webview().setLink(links[8]),new webview().setLink(links[9]),new webview().setLink(links[10])};

        public PageAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // It will display the required fragment

            if(position==0)
            {
                return new live_streaming();
            }
            else
            {
                 // Why it can't be global?
                 // user defined method
                return webviews[position];

            }
        }



        @Override
        public int getCount()
        {
            return category.length;
        }

        public CharSequence getPageTitle(int position)
        {
            // Displays Tabs Title
            return category[position];
        }
    }
    void loadIntertcicialAdd() {
        interstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        interstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("4A217A63745788610CA1AF332A1A18E2").build();

        // Load ads into Interstitial Ads
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    isAddLoaded = true;
                }
            }

            @Override
            public void onAdClosed() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isAddLoaded = false;
                        loadIntertcicialAdd();
                    }
                });
            }
        });
    }

    private RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewarded(RewardItem reward) {
            //Toast.makeText(QuizActivity.this, "onRewarded! currency: " + reward.getType() + "  amount: " + reward.getAmount(), Toast.LENGTH_SHORT).show();

            // Reward the user.
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            //Toast.makeText(QuizActivity.this, "onRewardedVideoAdLeftApplication",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            loadRewardedVideoAd();
            //Toast.makeText(QuizActivity.this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int errorCode) {
            //Toast.makeText(QuizActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdLoaded() {
            //Toast.makeText(QuizActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            //Toast.makeText(QuizActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
            //Toast.makeText(QuizActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            //Toast.makeText(QuizActivity.this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
        }
    };

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.reward_video_ad_id),
                new AdRequest.Builder().addTestDevice("4A217A63745788610CA1AF332A1A18E2").build());
    }


}
