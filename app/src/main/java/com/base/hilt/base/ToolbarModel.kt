package com.base.hilt.base

data class ToolbarModel(
    var isVisible: Boolean = false,
    var title: String? = null,
    var isBottomNavVisible: Boolean = false,
    var loginVisible:Boolean = false,
    var backBtnVisible:Boolean = false,
    var type:Int = 1,
    var editButtonVisible:Boolean = false,
    var searchButtonVisible:Boolean = false,
    var tvMarkAllReadVisible:Boolean = false
)