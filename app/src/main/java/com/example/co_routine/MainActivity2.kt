package com.example.co_routine

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

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

//        runDemo1()
//        runDemo2() // demo of suspend function

//        runDemo3()
        runDemo4()

    }


    suspend fun feedData1() : String{
        delay(2000L)
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

    /***
     * ตัวอย่างการสร้าง suspend แบบไม่ใช้ suspend function ภายใน
     * ตัวอย่างการใช้งาน measureTime เพื่อจับเวลาการทำงานของใดๆ
     */
    private fun runDemo3(){
        GlobalScope.launch {
            val time = measureTime {
//                doSomethingLog()
                delay(1000L)
            }

            Log.d(TAG,"It took ${time.inWholeMilliseconds} sec")
        }

        Log.d(TAG,"Exit from Global Scope")
    }


    private suspend fun doSomethingLog(){
        var i = 0
        for (e in 0..200000000){
            i++
        }
    }


    /**
     * Coroutine Context คือชุดขององค์ประกอบ (elements) ที่กำหนดพฤติกรรมของ Coroutine
     * เช่น Dispatcher, Job, CoroutineName และ ExceptionHandler
     *
     * สามารถกำหนด Coroutine Context ได้ตอนสร้าง Coroutine เช่น:
     *      - GlobalScope.launch(Dispatchers.IO)
     *        (เป็นการส่ง CoroutineContext เข้าไปใน launch)
     *
     * และสามารถเปลี่ยน Context ชั่วคราวภายใน Coroutine เดิมได้ด้วย:
     *      - withContext(Dispatchers.Main)
     *
     * โดย withContext จะเป็นการ switch thread ชั่วคราว
     * และยังทำงานอยู่ใน Coroutine เดิม (ไม่สร้าง Coroutine ใหม่)
     *
     * หมายเหตุ:
     * Coroutine Context สามารถรวมหลายองค์ประกอบเข้าด้วยกันได้ เช่น
     *      Dispatchers.IO + Job() + CoroutineName("MyCoroutine")
     */
    private fun runDemo4(){
        //Coroutine context (main and thread pool (default and IO))
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG,"Inside of coroutine ${Thread.currentThread().name}")
            val answer = feedData1()

            //switch coroutine from IO to Main in same Coroutine
            withContext(Dispatchers.Main){
                Log.d(TAG,"The answer is $answer")
                title = answer
            }
        }
    }
}