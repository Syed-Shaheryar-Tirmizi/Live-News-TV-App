package com.syed.ary_news;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class webview extends Fragment
{
    private static WebView webViewObj;
    private String link;
    private ProgressBar progressBarObj;
    private boolean flag=false;
    private Handler handler;
    InterstitialAd interstitialAd;
    boolean isAddLoaded=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_webview, container, false);
        webViewObj=view.findViewById(R.id.webview);
        webViewObj.loadUrl(link);
        loadIntercicialAdd();

        progressBarObj=view.findViewById(R.id.progressBar2);
        webViewObj.getSettings().setJavaScriptEnabled(true);
        webViewObj.setWebViewClient(new WebViewClients(progressBarObj));
        webViewObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(isAddLoaded)
                {
                    interstitialAd.show();
                }
                else
                {
                    loadIntercicialAdd();
                }
                flag=true;
            }
        });
        return view;

    }

    public webview setLink(String link)
    {
        this.link=link;
        return this;
    }

    public class WebViewClients extends WebViewClient
    {
        ProgressBar pb;
        public WebViewClients(ProgressBar pb)
        {
            this.pb=pb;
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String link)
        {
            view.loadUrl(link);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            pb.setVisibility(View.GONE);
        }
    }
public boolean onBackPressed()
{
    if(flag)
    {
        webViewObj.loadUrl(link);
        flag=false;
        return true;
    }
    else
    {
        return false;
    }
}

public void loadIntercicialAdd()
{
    interstitialAd=new InterstitialAd(getContext());
    interstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
    AdRequest adRequest = new AdRequest.Builder().addTestDevice("4A217A63745788610CA1AF332A1A18E2").build();
    interstitialAd.loadAd(adRequest);
    interstitialAd.setAdListener(new AdListener() {
        public void onAdLoaded() {
            if (interstitialAd.isLoaded())
            {
                isAddLoaded=true;
            }
        }
        public void onAdClosed() {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    isAddLoaded=false;
                    loadIntercicialAdd();
                }
            });
        }
    });

}
    private void webViewGoBack(){

        webViewObj.goBack();
    }
}

