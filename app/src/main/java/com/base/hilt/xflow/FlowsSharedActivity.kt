package com.base.hilt.xflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.base.hilt.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FlowsSharedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flows_shared)


        // shared flow (hot flow) have multiple consumers and a single flow object.
        // whereas normal flow cold, and multiple consumer will all receive their own flow object.

        // Ex. Like a Movie Show.
        // those who come on time will get to watch the movie.
        // those who come late with start watching the movie from that time. and preview history is lost

        GlobalScope.launch {
            val res = producer()
            res.collect{
                Log.d("FlowShared", "onCreate: ${it}")
            }
        }

        GlobalScope.launch {
            val res = producer()
            delay(2500)
            res.collect{
                Log.d("FlowShared", "onCreate: ${it}")
            }
        }
        // above, the second consumer starts listening late, so it would have
        // lost data for first 3 integers.

        //-------------------------
        // nothing will print because that producer has emitted all values,
        // and the below consumer joins after it. this is Shared flow, no state is maintained.
        GlobalScope.launch {
            val res = producer()
            delay(6000)
            res.collect{
                Log.d("FlowShared", "onCreate: ${it}")
            }
        }


        //---------------------------------------------------------------------------


        // State Flows.
        // Multiple comsumers - Hot Flow - Like a Shared Flow
        // It Maintains a state. So, whatever the last (latest) value it is, it will maintain it.

        // Multiple consumers will observe a single flow,
        // whenever the producer emits a new object,
        // all the consumers who are listening to that flow, will get updated.

        GlobalScope.launch {
            val result = producerStateFlow()
            delay(6000)
            result.collect{
                Log.d("FlowState", "onCreate: StateFlow ${it}")
            }
        }



    }


    // this fun produces a hot flow of integer
    // replay(optional) -> if any consumer joins late, will get replay( or last 3 integers)
    // as an output.

    private fun producer(): Flow<Int>{
        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 3)
        GlobalScope.launch {
            val list = listOf<Int>(1,2,3,4,5,6,7,8,9)
            list.forEach {
                mutableSharedFlow.emit(it)
                Log.d("Flow", "producer: Emitting - ${it}")
                delay(1000)
            }
        }
        return mutableSharedFlow
    }

    // state flow producer
    private fun producerStateFlow(): Flow<Int>{
        val mutableStateFlow = MutableStateFlow<Int>(10)
        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }

}