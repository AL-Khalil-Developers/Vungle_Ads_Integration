package com.alkhalildevelopers.vungleads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.vungle.warren.AdConfig;
import com.vungle.warren.Banners;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.VungleBanner;
import com.vungle.warren.error.VungleException;

import static com.vungle.warren.AdConfig.*;
import static com.vungle.warren.AdConfig.AdSize.BANNER;

public class MainActivity extends AppCompatActivity {
    ViewGroup bannerAdContainer;
    VungleBanner vungleBanner;
    Button interstitialBtn,BannerBtn,BannerHideBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerAdContainer = (LinearLayout) findViewById(R.id.bannerAdContainer);
        interstitialBtn = findViewById(R.id.interstitialBtn);
        BannerBtn = findViewById(R.id.bannerBtn);
        BannerHideBtn = findViewById(R.id.hideBannerBtn);

        interstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitialAd();
            }
        });

        BannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBannerAd();
            }
        });

        BannerHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vungleBanner.destroyAd();
            }
        });

        Vungle.init("5e773259af441d0001b7e2b7", getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Vungle Ads SDK initialized Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VungleException exception) {
                Toast.makeText(MainActivity.this, "Vungle Ads SDK not initialized" + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {

            }
        });
    }

    private void showBannerAd(){
        Banners.loadBanner("BANNER-3147636", AdConfig.AdSize.BANNER,new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                Toast.makeText(MainActivity.this, "Banner Ad Loaded", Toast.LENGTH_SHORT).show();
                if (Banners.canPlayAd("BANNER-3147636", BANNER)){
                    vungleBanner = Banners.getBanner("BANNER-3147636", BANNER,null);
                    bannerAdContainer.addView(vungleBanner);
                }
            }

            @Override
            public void onError(String id, VungleException exception) {
                Toast.makeText(MainActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showInterstitialAd(){
        Vungle.loadAd("INTERSTITIAL-2458719", new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                if (Vungle.canPlayAd("INTERSTITIAL-2458719")){
                    Vungle.playAd("INTERSTITIAL-2458719", null, new PlayAdCallback() {
                        @Override
                        public void onAdStart(String id) {

                        }

                        @Override
                        public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {

                        }

                        @Override
                        public void onError(String id, VungleException exception) {

                        }
                    });
                }
            }

            @Override
            public void onError(String id, VungleException exception) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        vungleBanner.destroyAd();
        super.onDestroy();
    }
}
