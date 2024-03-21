package com.base.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FlowOperatorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_operators)

        // Events inside flow
        GlobalScope.launch(Dispatchers.Main) {
            producer()
                // events inside flow
                .onStart {
                    emit(-1)
                    Log.d("OperatorsFlows", "called at starting")
                }
                .onCompletion {
                    emit(11)
                    Log.d("OperatorsFlows", "called at complete")
                }
                .onEach {
                    Log.d("OperatorsFlows", "on each - ${it}")
                }
                .collect {
                    Log.d("OperatorsFlows", "collect: ${it}")
                }
        }

        // Operators in Flow
        // 1 - Terminal Operator
        // 2 - Non-Terminal Operator
        GlobalScope.launch(Dispatchers.Main) {

            // Terminal operators
            // All terminal operators are suspend function
            val firstVal = producer().first()
            val producerList = producer().toList()
            Log.d("OperatorsFlows", "onCreate: ${firstVal}")
            Log.d("OperatorsFlows", "onCreate: ${producerList}")


            // Non terminal Operators
            // this operators are not suspend function they return Flows
            producer()
                // will map every int by multiflying by 2
                .map {
                    it * 2
                }
                // all intergers < 4 will only get consumed otherwise not consumed
                .filter {
                    it<4
                }
                .collect{
                    Log.d("OperatorsFlows", "onCreate: Collected - ${it}")
                }



        }



        // Custom FLow for notes
        notesFlow()

    }

    private fun producer(): Flow<Int> {
        return flow {
            val list = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            list.forEach {
                kotlinx.coroutines.delay(1000)
                emit(it)
            }
        }
    }

    private fun notesFlow() {

        GlobalScope.launch {
            getNotes().map {
                FormattedNote(it.isActive,it.title.uppercase(),it.desc)
            }.filter {
                isActive
            }.collect{
                Log.d("OperatorsFlows", "notesFlow: ${it.toString()}")
            }
        }



    }

    private fun getNotes():Flow<Note>{

        val list = listOf<Note>(
            Note(1,true, "First", "First Desc"),
            Note(2,true, "Second", "Second Desc"),
            Note(3,false, "Third", "Third Desc")
        )

        return list.asFlow()
    }

    data class Note(
        val id:Int,
        val isActive:Boolean,
        val title:String,
        val desc:String
    )

    data class FormattedNote(
        val isActive:Boolean,
        val title:String,
        val desc:String
    )



}