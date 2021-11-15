package com.kdapps.byteseq.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kdapps.byteseq.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = this.getSharedPreferences("user_data", MODE_PRIVATE)
        btn_login.setOnClickListener(){
            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if(email == "byteseq" && password == "byteseq"){
                sharedPreferences.edit().putBoolean("login",true).apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            else if(email == "" && password == ""){
                Toast.makeText(this, "Fill both the Fields ", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "User not found ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}