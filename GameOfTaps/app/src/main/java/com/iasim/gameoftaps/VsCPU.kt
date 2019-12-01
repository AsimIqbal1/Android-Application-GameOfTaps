package com.iasim.gameoftaps

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_vs_cpu.*
import java.util.*
import kotlin.concurrent.thread

class VsCPU : AppCompatActivity(), RewardedVideoAdListener {
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
        val intent = Intent(this, VsCPU::class.java)
        intent.putExtra("player1Wins", playerOneWins)
        intent.putExtra("player2Wins", playerTwoWins)
        startActivity(intent)
        finish()
    }

    override fun onRewardedVideoCompleted() {
        val intent = Intent(this, VsCPU::class.java)
        intent.putExtra("player1Wins", playerOneWins)
        intent.putExtra("player2Wins", playerTwoWins)
        startActivity(intent)
        finish()
    }

    private var scorePlayerOne = 0
    private var scorePlayerTwo = 0

    private var playerOneWins = 0
    private var playerTwoWins = 0

    private var isPlayerOneReady = true
    private var isPlayerTwoReady = false

    private var isGameStarted = false
    private var kill = false

    private val startTimeInMillis: Long = 20000

    private val mTextViewCountDown: TextView? = null
    private val mButtonStartPause: Button? = null
    private val mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning: Boolean = false
    private val lock = java.lang.Object()

    private var mTimeLeftInMillis = startTimeInMillis
    private val win = "You Win"
    private val lose = "You Lose"
    private val tie = "Match Tied"

    private val list = listOf(1,2,3,4,5,6,7,8,9)

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mRewardedVideoAd: RewardedVideoAd


    override fun onStart() {
        super.onStart()
        MobileAds.initialize(this, "admob key")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "admob ad key"

        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_vs_cpu)


        MobileAds.initialize(this, "admob key")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "admob ad key"

        MobileAds.initialize(applicationContext, "admob ad key")

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadRewardedVideo()

        vrestartGame.visibility = View.INVISIBLE

        try {
            playerOneWins = intent.extras.getInt("player1Wins")
            playerTwoWins = intent.extras.getInt("player2Wins")
            vwin1.text = "Win: $playerOneWins"
            vwin2.text = "Win: $playerTwoWins"
        } catch (e: Throwable) {
        }

        vbtn_back.setOnClickListener {
            finish()
        }

        thread {
            while(!isPlayerTwoReady){
                synchronized(lock){
                    lock.wait(50)
                }
            }
            var rand = list.random()
            if (rand == 1) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(250)
                    }
                }
            } else if (rand == 2) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(220)
                    }
                }
            } else if (rand == 3) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(190)
                    }
                }
            } else if (rand == 4) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(170)
                    }
                }
            } else if (rand == 5) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(160)
                    }
                }
            } else if (rand == 6) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(145)
                    }
                }
            } else if (rand == 7) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(130)
                    }
                }
            } else if (rand == 8) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(120)
                    }
                }
            } else if (rand == 9) {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(100)
                    }
                }
            } else {
                while (!kill) {
                    runOnUiThread {
                        vplayer1.performClick()
                        scorePlayerOne++
                        startGame()
                        vtextPlayer1.text = scorePlayerOne.toString()
                    }
                    synchronized(lock) {
                        lock.wait(180)
                    }
                }
            }

        }

//        vplayer1.setOnClickListener {
//            isPlayerOneReady = true
//
//            if (isPlayerOneReady && isPlayerTwoReady) {
//                scorePlayerOne++
//                startGame()
//                vtextPlayer1.text = scorePlayerOne.toString()
//            }
//
//            vreadyPlayer1.textSize = 33f
//            vreadyPlayer1.text = "I'm Ready to Bang!!"
//        }

        vplayer2.setOnClickListener {
            isPlayerTwoReady = true

            if (isPlayerTwoReady && isPlayerOneReady) {
                scorePlayerTwo++
                startGame()
                vtextPlayer2.text = scorePlayerTwo.toString()
            }

            vreadyPlayer2.textSize = 33f
            vreadyPlayer2.text = "I'm Ready to Bang!!"
        }

    }

    private fun startGame(){
        if(!isGameStarted){
            vreadyPlayer1.visibility = View.INVISIBLE
            vreadyPlayer2.visibility = View.INVISIBLE
            vbtn_back.visibility = View.INVISIBLE
            isGameStarted = true
            scorePlayerTwo = 0
            scorePlayerOne = 0
            mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    mTimeLeftInMillis = millisUntilFinished
                    if(mTimeLeftInMillis<7000){
                        vtimePlayer1!!.setTextColor(Color.RED)
                        vtimePlayer2!!.setTextColor(Color.RED)
                        vtimePlayer1.textSize = 36f
                        vtimePlayer2.textSize = 36f
                    }
                    updateCountDownText()
                }

                override fun onFinish() {
                    mTimerRunning = false
                    showResults()
                    kill = true
                }
            }.start()

            mTimerRunning = true
        }
    }

    private fun resetTimer() {
        mTimeLeftInMillis = startTimeInMillis
        updateCountDownText()
        mButtonReset!!.visibility = View.INVISIBLE
        mButtonStartPause!!.visibility = View.VISIBLE
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt()/ 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val milliSec = (mTimeLeftInMillis%100).toInt()

        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds,milliSec)

        vtimePlayer1!!.text = timeLeftFormatted
        vtimePlayer2!!.text = timeLeftFormatted
    }

    private fun showResults(){

        vrestartGame.visibility = View.VISIBLE
        vbtn_back.visibility = View.VISIBLE

        vtimePlayer1!!.text = "00:00:00"
        vtimePlayer2!!.text = "00:00:00"

        vfinalScore1.text = "Final Score: "+scorePlayerOne.toString()
        vfinalScore2.text = "Final Score: "+scorePlayerTwo.toString()

        val finalScore1 = String.format(Locale.getDefault(), "%.2f",scorePlayerOne.toFloat()/30.0)
        val finalScore2 = String.format(Locale.getDefault(), "%.2f",scorePlayerTwo.toFloat()/30.0)
        vavrgScorePerSecond1.text = "Average Clicks Per Second: $finalScore1"
        vavrgScorePerSecond2.text = "Average Clicks Per Second: $finalScore2"

        if(scorePlayerOne>scorePlayerTwo){
            //means player one wins
            val winList = listOf(1,2,3,4,5,6,7,8)
            val winRand = winList.random()
            if(winRand == 1){
                vreadyPlayer1.text = "School Boy Error!!"
            }else if(winRand == 2){
                vreadyPlayer1.text = "I'm Pro!!"
            }else if(winRand == 3){
                vreadyPlayer1.text = "Next Please!!"
            }else if(winRand == 4){
                vreadyPlayer1.text = "You can't beat me."
            }else if(winRand == 5){
                vreadyPlayer1.text = "Out Classed"
            }else if(winRand == 6){
                vreadyPlayer1.text = "Did you even tried?"
            }else if(winRand == 7){
                vreadyPlayer1.text = "You can't see me."
            }else if(winRand == 8){
                vreadyPlayer1.text = "You can only win hearts."
            }
            vreadyPlayer2.text = lose
            playerOneWins++
            vscoreDifference1.text = "Won by "+(scorePlayerOne - scorePlayerTwo).toString()+" Clicks"
            vscoreDifference2.text = "Lost by "+(scorePlayerOne - scorePlayerTwo).toString()+" Clicks"

        } else if(scorePlayerTwo>scorePlayerOne){
            //means player two wins
            if(scorePlayerTwo - scorePlayerOne < 4){
                vreadyPlayer1.text = "Woah.. Close!!"
            }else {
                val loseList = listOf(1, 2, 3, 4)
                val loseRand = loseList.random()
                if (loseRand == 1) {
                    vreadyPlayer1.text = "Well Played"
                } else if (loseRand == 2) {
                    vreadyPlayer1.text = "Let's play again"
                } else if (loseRand == 3) {
                    vreadyPlayer1.text = "You're good"
                } else if (loseRand == 4) {
                    vreadyPlayer1.text = "Nice Babes!!"
                }
            }
            vreadyPlayer2.text = win
            playerTwoWins++

            vscoreDifference2.text = "Won by "+(scorePlayerTwo - scorePlayerOne).toString()+" Clicks"
            vscoreDifference1.text = "Lost by "+(scorePlayerTwo - scorePlayerOne).toString()+" Clicks"
        }else{
            //tied
            vreadyPlayer1.text = tie
            vreadyPlayer2.text = tie

            vscoreDifference1.text = "Same Clicks"
            vscoreDifference2.text = "Same Clicks"
        }



        vwin1.text = "Win: $playerOneWins"
        vwin2.text = "Win: $playerTwoWins"

        vreadyPlayer1.visibility = View.VISIBLE
        vreadyPlayer2.visibility = View.VISIBLE

        vplayer2.isEnabled = false
        vplayer1.isEnabled = false

        vrestartGame.setOnClickListener {
            if(mRewardedVideoAd.isLoaded){
                mRewardedVideoAd.show()
            }else if(mInterstitialAd.isLoaded){
                mInterstitialAd.show()
                mInterstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        // Code to be executed when when the user is about to return
                        // to the app after tapping on an ad.
                        val intent = Intent(this@VsCPU, VsCPU::class.java)
                        intent.putExtra("player1Wins", playerOneWins)
                        intent.putExtra("player2Wins", playerTwoWins)
                        startActivity(intent)
                        finish()
                    }
                }
            }else {
                val intent = Intent(this, VsCPU::class.java)
                intent.putExtra("player1Wins", playerOneWins)
                intent.putExtra("player2Wins", playerTwoWins)
                startActivity(intent)
                finish()
            }



        }

    }

    private fun loadRewardedVideo(){
        mRewardedVideoAd.loadAd("admob rewardedvideo ad key",
                AdRequest.Builder().build())
    }

    private fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

}
