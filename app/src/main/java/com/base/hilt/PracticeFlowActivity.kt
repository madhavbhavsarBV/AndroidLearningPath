package com.base.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import java.io.IOException

class PracticeFlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_flow)


        // map the flow and emits in the same order.
        flatMapConcat()

        // will retry in flow n no. of times if there is an error
        CoroutineScope(Dispatchers.Main).launch {
            retryInFlow()
        }


        // debounce example
        debounceFlow()

        //channel
        channelFlowExample()

    }

    private fun channelFlowExample() {
        val channel = Channel<String>()
        CoroutineScope(Dispatchers.Main).launch {
            for (i in 1..5) {
                delay(1000L)
                channel.send("Data $i")
            }
            channel.close()
        }
        CoroutineScope(Dispatchers.Main).launch {
            channel.receiveAsFlow().collect{ data ->
                println("Received: $data")
            }
        }


    }


    // filter out if any new element is emitted in this debounce timeframe.
    private fun debounceFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            flowOf(1, 2, 3, 4, 5)
                .debounce(1000)
                .collect { value ->
                    println(value)
                }
        }
    }


    private suspend fun retryInFlow() {
        startTask()
            .retry(3) {
                println("retrying...")
                delay(2000)
                it is IndexOutOfBoundsException
            }.catch {
                print(it.toString())
            }
            .collect {
                println(it)
            }
    }


    private fun startTask(): Flow<Int> {
        return flow {
            for (i in 1..4) {
                val randomInt = (0..2).random()
                if (randomInt == 0) {
                    throw IndexOutOfBoundsException()
                } else if (randomInt == 2) {
                    throw IOException()
                }
                emit(i)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun flatMapConcat() {
        val numFlow = flowOf(1, 2, 3)
        val stringFlow = numFlow.flatMapConcat { number ->
            flowOf("$number First", "$number Second")
        }
        CoroutineScope(Dispatchers.Main).launch {
            stringFlow.collect { println(it) }
        }
    }


}