package com.base.hilt.ui.challenge.model

import com.google.gson.annotations.SerializedName

data class ContactsRequest(
    @SerializedName("mobile_number") val mobile_number : List<String>?= null
)