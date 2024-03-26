package com.base.hilt.xcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.base.hilt.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class CoroutinesActivity : AppCompatActivity() {

    lateinit var coroutinesViewModel: CoroutinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        coroutinesViewModel = ViewModelProvider(this)[CoroutinesViewModel::class.java]
        // calling a suspend function inside coroutine
        // launch -> it is used to initialize a coroutine and executes the task within the block.
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            callFunction()
        }


        // types of Scopes.
        //1
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("Coroutines", "onCreate: CoroutineScope")
        }
        // default scope, can be cancelled at a later time

        //2
        MainScope().launch {
            Log.d("Coroutines", "onCreate: MainScope")
        }

        //3
        // run at an application context level. Destroyed when the application is destroyed
        GlobalScope.launch {
            Log.d("Coroutines", "onCreate: GlobalScope")
        }

        //4 Lifecycle scope -> depends on the activity or fragment's lifecycle
        //            and gets cancelled automatically when the lifecycle gets destroyed.
        lifecycleScope.launch {
            Log.d("Coroutines", "onCreate: lifecycleScope")
        }


        // 5 viewModelScope -> depends on the lifecycle of the viewModel. needs a view model class to launch
        coroutinesViewModel.viewModelScope.launch {
            Log.d("Coroutines", "onCreate: viewModelScope")
        }
        // this is also example of viewmodel scope.
        coroutinesViewModel.performBackgroundTask()


        // types of Dispatchers.
        // 4 types.
        // Dispatchers.Main -> run on Main Thread
        // Dispatchers.Default -> run on background Thread
        // Dispatchers.IO -> run on input/output Thread for performinf r/w operations.
        // Dispatchers.Unconfined -> it allows coroutine to run on multiple thread
        //                         starting on the current thread and potentially resuming on a different thread


        // Run Blocking
        runBlockingExample()
        // Run Blocking
        runBlockingExample2()

        // Async-Await Example
        asyncAwait()


        // supervisor job
        // it supervises the execution of multiple child coroutines
        // if 1 child throws exception, it does not effect other childrens.
        supervisorJobExample()

    }

    private fun supervisorJobExample() = runBlocking {
        val supervisorJob = SupervisorJob()

        val coroutineScope = CoroutineScope(Dispatchers.Default + supervisorJob)

        val job1 = coroutineScope.launch {
            println("Child coroutine 1 started")
            delay(1000)
            println("Child coroutine 1 completed")
        }

        val job2 = coroutineScope.launch {
            try {
                println("Child coroutine 2 started")
                delay(2000)
                throw RuntimeException("Something went wrong in coroutine 2")
            } catch (e: Exception) {
                println("Coroutine 2 failed: ${e.message}")
            }
        }

        job1.join()
        job2.join()

        supervisorJob.cancel()

    }

    private fun runBlockingExample2() = runBlocking {
        val job: Job = launch {        // it supervises the execution of multiple coroutines

            // Coroutine code
            delay(1000)
            println("Coroutine is completed")
        }

        println("Main thread is still working")

        // Delay to allow the coroutine to complete
        delay(2000)

        // Check if the coroutine is still active
        if (job.isActive) {
            println("Coroutine is still active")
        } else if (job.isCancelled) {
            println("Coroutine was cancelled")
        } else if (job.isCompleted) {
            println("Coroutine completed")
        }
    }


    private fun runBlockingExample() {
        runBlocking {
            // this will block the main thread, until the block is completely executed.
            delay(2000)
            Log.d("Coroutines", "runBlockingExample: block main thread for 2 sec")
        }
        Log.d("Coroutines", "runBlockingExample: runblocking end")
    }


    // suspend function -> this function can be paused and resumed at a later time
    private suspend fun callFunction() {
        delay(2000)
        Log.d("Coroutine", "callFunction: called")
    }

    private fun asyncAwait() {

        val callThis = CoroutineScope(Dispatchers.Main).launch {
            val gg = async {

            }

            val hh = gg.await()
        }
    }


}