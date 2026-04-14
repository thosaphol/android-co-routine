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
        runDemo2() // demo of suspend function

    }


    suspend fun feedData1() : String{
        delay(1000L)
        return "Dog"
    }

    suspend fun feedData2() : String{
        delay(3000L)
        return "Cat"
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

    /**
     * suspend function คือ function ที่สามารถหยุด (suspend) การทำงานชั่วคราวได้
     * โดยไม่ block thread ทำให้ thread สามารถไปทำงานอื่นต่อได้
     *
     * เมื่อเงื่อนไขที่รอเสร็จสิ้น (เช่น network / delay)
     * coroutine จะ resume และทำงานต่อจากจุดเดิม
     *
     * suspend function ไม่สามารถเรียกจาก function ปกติได้
     * ต้องเรียกภายใน coroutine หรือ suspend function เท่านั้น
     */
    private fun runDemo2(){
        //suspend function
        GlobalScope.launch {
//            delay(10000L)
            val answer1 = feedData1()
            val answer2 = feedData2()
            Log.d(TAG,"Answer1 is $answer1")
            Log.d(TAG,"Answer2 is $answer2")
        }
    }
}