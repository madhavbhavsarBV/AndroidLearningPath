package com.base.hilt.ui.account.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup

//
//class CustomGridLayout(context: Context?, spanCount: Int) :
//    GridLayoutManager(context, spanCount) {
//    init {
//        spanSizeLookup = object : SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//
//                return if(position % getSpanCount() == 0 )
//                    getSpanCount()
//                else 1
//            }
//        }
//    }
//}

//class CustomGridLayout(private val columns: Int) : SpanSizeLookup() {
//    override fun getSpanSize(position: Int): Int {
//        return if (position % columns == 0) {
//            columns // Make first item in each row span the entire column
//        } else {
//            1
//        }
//    }
//}