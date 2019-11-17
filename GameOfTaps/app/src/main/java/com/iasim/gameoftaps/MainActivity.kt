package com.iasim.gameoftaps

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import java.util.*


class MainActivity : AppCompatActivity(), RewardedVideoAdListener {
    override fun onRewardedVideoAdLeftApplication() {

    }

    override fun onRewardedVideoAdLoaded() {
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewarded(p0: RewardItem?) {
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }

    override fun onRewardedVideoAdClosed() {
        finish()
        startActivity(Intent(this@MainActivity, VsCPU::class.java))
    }

    override fun onRewardedVideoCompleted() {
        finish()
        startActivity(Intent(this@MainActivity, VsCPU::class.java))
    }

    private var scorePlayerOne = 0
    private var scorePlayerTwo = 0

    private var playerOneWins = 0
    private var playerTwoWins = 0

    private var isPlayerOneReady = false
    private var isPlayerTwoReady = false

    private var isGameStarted = false

    private val startTimeInMillis: Long = 30000

    private val mTextViewCountDown: TextView? = null
    private val mButtonStartPause: Button? = null
    private val mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning: Boolean = false

    private var mTimeLeftInMillis = startTimeInMillis
    private val win = "You Win"
    private val lose = "You Lose"
    private val tie = "Match Tied"

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mRewardedVideoAd: RewardedVideoAd


    override fun onStart() {
        super.onStart()
        MobileAds.initialize(this, "ca-app-pub-5759603394484097~6831065374")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-5759603394484097/8607181471"

        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        restartGame.visibility = View.INVISIBLE


        MobileAds.initialize(this, "ca-app-pub-5759603394484097~6831065374")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-5759603394484097/8607181471"

        MobileAds.initialize(applicationContext, "ca-app-pub-5759603394484097~6831065374")

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadRewardedVideo()

        try {
            playerOneWins = intent.extras.getInt("player1Wins")
            playerTwoWins = intent.extras.getInt("player2Wins")
            win1.text = "Win: $playerOneWins"
            win2.text = "Win: $playerTwoWins"
        }catch(e: Throwable){
        }

        btn_back.setOnClickListener {
            finish()
        }

        player1.setOnClickListener {
            isPlayerOneReady = true

            if (isPlayerOneReady && isPlayerTwoReady) {
                scorePlayerOne++
                startGame()
                textPlayer1.text = scorePlayerOne.toString()
            }

            readyPlayer1.textSize = 33f
            readyPlayer1.text = "I'm Ready to Bang!!"
        }

        player2.setOnClickListener {
            isPlayerTwoReady = true

            if (isPlayerTwoReady && isPlayerOneReady) {
                scorePlayerTwo++
                startGame()
                textPlayer2.text = scorePlayerTwo.toString()
            }

            readyPlayer2.textSize = 33f
            readyPlayer2.text = "I'm Ready to Bang!!"
        }

    }

    private fun startGame(){
        if(!isGameStarted){
            btn_back.visibility = View.INVISIBLE
            readyPlayer1.visibility = View.INVISIBLE
            readyPlayer2.visibility = View.INVISIBLE
            isGameStarted = true
            scorePlayerTwo = 0
            scorePlayerOne = 0
            mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    mTimeLeftInMillis = millisUntilFinished
                    if(mTimeLeftInMillis<7000){
                        timePlayer1!!.setTextColor(Color.RED)
                        timePlayer2!!.setTextColor(Color.RED)
                        timePlayer1.textSize = 36f
                        timePlayer2.textSize = 36f
                    }
                    updateCountDownText()
                }

                override fun onFinish() {
                    mTimerRunning = false
                    showResults()
                }
            }.start()

            mTimerRunning = true
        }
    }

//    private fun resetTimer() {
//        mTimeLeftInMillis = startTimeInMillis
//        updateCountDownText()
//        mButtonReset!!.visibility = View.INVISIBLE
//        mButtonStartPause!!.visibility = View.VISIBLE
//    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt()/ 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val milliSec = (mTimeLeftInMillis%100).toInt()

        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds,milliSec)

        timePlayer1!!.text = timeLeftFormatted
        timePlayer2!!.text = timeLeftFormatted
    }

    private fun showResults(){

        restartGame.visibility = View.VISIBLE
        btn_back.visibility = View.VISIBLE
        timePlayer1!!.text = "00:00:00"
        timePlayer2!!.text = "00:00:00"

        finalScore1.text = "Final Score: "+scorePlayerOne.toString()
        finalScore2.text = "Final Score: "+scorePlayerTwo.toString()

        val finalScore1 = String.format(Locale.getDefault(), "%.2f",scorePlayerOne.toFloat()/30.0)
        val finalScore2 = String.format(Locale.getDefault(), "%.2f",scorePlayerTwo.toFloat()/30.0)
        avrgScorePerSecond1.text = "Average Clicks Per Second: $finalScore1"
        avrgScorePerSecond2.text = "Average Clicks Per Second: $finalScore2"

        if(scorePlayerOne>scorePlayerTwo){
            //means player one wins
            readyPlayer1.text = win
            readyPlayer2.text = lose
            playerOneWins++
            scoreDifference1.text = "Won by "+(scorePlayerOne - scorePlayerTwo).toString()+" Clicks"
            scoreDifference2.text = "Lost by "+(scorePlayerOne - scorePlayerTwo).toString()+" Clicks"

        }else if(scorePlayerTwo>scorePlayerOne){
            //means player two wins
            readyPlayer1.text = lose
            readyPlayer2.text = win
            playerTwoWins++

            scoreDifference2.text = "Won by "+(scorePlayerTwo - scorePlayerOne).toString()+" Clicks"
            scoreDifference1.text = "Lost by "+(scorePlayerTwo - scorePlayerOne).toString()+" Clicks"
        }else{
            //tied
            readyPlayer1.text = tie
            readyPlayer2.text = tie

            scoreDifference1.text = "Same Clicks"
            scoreDifference2.text = "Same Clicks"
        }



        win1.text = "Win: $playerOneWins"
        win2.text = "Win: $playerTwoWins"

        readyPlayer1.visibility = View.VISIBLE
        readyPlayer2.visibility = View.VISIBLE

        player2.isEnabled = false
        player1.isEnabled = false

        restartGame.setOnClickListener {
            if(mRewardedVideoAd.isLoaded){
                mRewardedVideoAd.show()
            }else if(mInterstitialAd.isLoaded){
                mInterstitialAd.show()
                mInterstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        // Code to be executed when when the user is about to return
                        // to the app after tapping on an ad.
                        val intent = Intent(this@MainActivity, MainActivity::class.java)
                        intent.putExtra("player1Wins", playerOneWins)
                        intent.putExtra("player2Wins", playerTwoWins)
                        startActivity(intent)
                        finish()
                    }
                }
            }else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("player1Wins", playerOneWins)
                intent.putExtra("player2Wins", playerTwoWins)
                startActivity(intent)
                finish()
            }

        }

    }

    private fun loadRewardedVideo(){
        mRewardedVideoAd.loadAd("ca-app-pub-5759603394484097/2396996349",
                AdRequest.Builder().build())
    }

}
