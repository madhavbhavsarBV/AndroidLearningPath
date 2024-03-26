package com.base.hilt.xflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.base.hilt.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowsActivity : AppCompatActivity() {

    // flows and channels
    val channel = Channel<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flows)

        // initializing producers of data and consumers of the data
        producer()
        consumer()

        // normal coroutines for getting a list of data
        CoroutineScope(Dispatchers.Main).launch {
            getUserNames().forEach {
                Log.d("Flows", "onCreate: User $it")
            }
        }

        // launching the producerFlow using Global Scope
        GlobalScope.launch {
            val data :Flow<Int> = producerFlow()
            data.collect{
                Log.d("Flows-1", "onCreate: $it")
            }
        }

        //in cold streams both the obeserverables will the the same data starting from 1
        // this is multiple comsumers for same flow
        // if delayed, the cold flow will receive the data from the starting
        GlobalScope.launch {
            val data :Flow<Int> = producerFlow()
            delay(2500)
            data.collect{
                Log.d("Flows-2", "onCreate: $it")
            }
        }
        // in case of hot streams the data which has been lost, is lost forever.




        // create the same coroutine job above and then cancellign it
        val job = GlobalScope.launch {
            val data :Flow<Int> = producerFlow()
            data.collect{
                Log.d("Flows", "onCreate: $it")
            }
        }

        //the above job will get cancelled
        // the output will product only till 3 and then the job will get cancelled
        GlobalScope.launch {
            delay(3500)
            job.cancel()
        }



    }

    // getting usernames from the list per second
    private suspend fun getUserNames():List<String>{
        val list = mutableListOf<String>()
        list.add(getUser(1))
        list.add(getUser(2))
        list.add(getUser(3))
        list.add(getUser(4))
        list.add(getUser(5))
        return list
    }

    private suspend fun getUser(id:Int):String{
        delay(1000)
        return "User$id"
    }

    fun producer(){
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
        }
    }

    fun consumer(){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("Flows", "consumer: ${channel.receive().toString()}")
            Log.d("Flows", "consumer: ${channel.receive().toString()}")
        }
    }


    // this function will produce normal flow
    // emit function emit the value of the type of flow
    fun producerFlow() = flow<Int>{
        val list = listOf<Int>(0,1,2,3,4,5,6,7,8,9)
        list.forEach{
            delay(1000)
            emit(it)
        }
    }




}
