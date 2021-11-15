package com.kdapps.byteseq.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kdapps.byteseq.R

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = this.getSharedPreferences("user_data", MODE_PRIVATE)
        val loginCheck = sharedPreferences.getBoolean("login", false)

        Handler().postDelayed({
            if(loginCheck){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        },1000)

    }
}