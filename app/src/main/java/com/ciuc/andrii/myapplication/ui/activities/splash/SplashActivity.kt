package com.ciuc.andrii.myapplication.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.ui.activities.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, MainActivity::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            startActivity(intent)
        }

    }
}