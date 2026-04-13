package com.example.co_routine

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        runDemo1()
    }



    private fun runDemo1(){
        GlobalScope.launch {
            while (true){
                delay(1000L)
                //Thread.currentThread().name => ชื่อของ Thread ที่ coroutine ใช้ run
                Log.d(TAG,"Inside of coroutine ${Thread.currentThread().name}")
            }

        }

        //Thread.currentThread().name => ชื่อของ Thread ที่ function runDemo1 ใช้ run
        Log.d(TAG,"Outside of coroutine ${Thread.currentThread().name}")
    }
}