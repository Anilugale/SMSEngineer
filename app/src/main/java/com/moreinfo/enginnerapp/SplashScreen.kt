package com.moreinfo.enginnerapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moreinfo.enginnerapp.cache.UserCache
import kotlinx.android.synthetic.main.activity_main.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UserCache.initUser(this)
        container.postDelayed({

            if(UserCache.user!=null){
                startActivity(Intent(this, DashboardActivity::class.java))
            }else{
              startActivity(Intent(this, LoginView::class.java))
            }
                finish()
        }, 1000)
    }

    override fun onBackPressed() {

    }
}