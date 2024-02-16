package com.base.hilt.ui.groupdetail.model

data class ChallengeModel(
    val type: String? = null,
) {

    fun type1v1():Boolean{
        return type=="1v1"
    }

    fun typeGroup():Boolean{
        return type=="GROUP"
    }

}