package com.kdapps.byteseq.activities

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kdapps.byteseq.MyUserAdapter
import com.kdapps.byteseq.R
import com.kdapps.byteseq.UserEntity
import com.kdapps.byteseq.connectionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupToolbar()
        sharedPreferences = this.getSharedPreferences("user_data", MODE_PRIVATE)

        val url = "http://byteseq.com/temp/api.php"
        val queue = Volley.newRequestQueue(this)
        btn_fetch_data.setOnClickListener(){
            if (connectionManager().checkConnectivity(this)){
                progressBar.visibility = View.VISIBLE

                val jsonArrayRequest = object : JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener{
                        progressBar.visibility = View.INVISIBLE
                        linearLayout_name.visibility = View.VISIBLE
                        val userList = arrayListOf<UserEntity>()
                        for(i in 0 until it.length()){
                            val jsonObject = it.getJSONObject(i)
                            val id = jsonObject.getString("idx")
                            val key = jsonObject.getString("pkey")
                            val date = jsonObject.getString("adate")
                            val sold = jsonObject.getString("sold")

                            val userEntity = UserEntity(id, key, date, sold)
                            userList.add(userEntity)
                        }

                        recycler_view.apply {
                            adapter = MyUserAdapter(userList)
                            layoutManager = LinearLayoutManager(this@MainActivity)
                        }
                    },
                    Response.ErrorListener {
                        progressBar.visibility = View.INVISIBLE
                        println("Error is $it")
                        Toast.makeText(this@MainActivity, " Something went wrong", Toast.LENGTH_LONG).show()
                    }
                ){
                    override fun getHeaders(): MutableMap<String, String> {
                        return super.getHeaders()
                    }
                }
                queue.add(jsonArrayRequest)
            }

            else{
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Error")
                dialog.setMessage("Internet connection is not found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    this.finish()
                }
                dialog.setNegativeButton("Cancel") { text, listener ->
                    ActivityCompat.finishAffinity(this)
                }
                dialog.create()
                dialog.show()
            }

        }
        btn_logout.setOnClickListener(){
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Confirm")
            dialog.setMessage("Are you sure about logout?")
            dialog.setPositiveButton("OK") { text, listener ->
                sharedPreferences.edit().clear().apply()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            dialog.setNegativeButton("Cancel") { text, listener ->

            }
            dialog.create()
            dialog.show()
        }


    }

    fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("ByteSeq")
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.gradient_background))
    }
}