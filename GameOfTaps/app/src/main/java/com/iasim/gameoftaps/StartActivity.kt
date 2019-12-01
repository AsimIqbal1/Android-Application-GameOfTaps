package com.iasim.gameoftaps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {


    private lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onStart() {
        super.onStart()
        MobileAds.initialize(this, "admob key")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "admob ad key"

        mInterstitialAd.loadAd(AdRequest.Builder().build())

        val adRequest = AdRequest.Builder()
                .build()

        menuBanner.loadAd(adRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_start)


        MobileAds.initialize(this, "admob key")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "admob ad key"

        btn_two_players.setOnClickListener {
            if(mInterstitialAd.isLoaded){
                mInterstitialAd.show()

                mInterstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        // Code to be executed when when the user is about to return
                        // to the app after tapping on an ad.
                        val mIntent = Intent(this@StartActivity, MainActivity::class.java)
                        mIntent.putExtra("player1Wins", 0)
                        mIntent.putExtra("player2Wins", 0)
                        startActivity(mIntent)
                    }
                }
            }else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("player1Wins", 0)
                intent.putExtra("player2Wins", 0)
                startActivity(intent)
            }
        }

        btn_vs_cpu.setOnClickListener {
            if(mInterstitialAd.isLoaded){
                mInterstitialAd.show()

                mInterstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        // Code to be executed when when the user is about to return
                        // to the app after tapping on an ad.
                        val mIntent = Intent(this@StartActivity, VsCPU::class.java)
                        mIntent.putExtra("player1Wins", 0)
                        mIntent.putExtra("player2Wins", 0)
                        startActivity(mIntent)
                    }
                }
            }else {
                val intent = Intent(this, VsCPU::class.java)
                intent.putExtra("player1Wins", 0)
                intent.putExtra("player2Wins", 0)
                startActivity(intent)
            }
        }

    }
}
