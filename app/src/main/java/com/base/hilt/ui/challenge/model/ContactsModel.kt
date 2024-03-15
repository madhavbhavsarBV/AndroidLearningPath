package com.base.hilt.ui.challenge.model

import com.google.gson.annotations.SerializedName

data class ContactsModel(
    @SerializedName("first_name") val first_name: String? = null,
    @SerializedName("last_name") val last_name: String? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("uuid") val uuid: String? = null,
    @SerializedName("verified") val verified: Int? = null,
    @SerializedName("mobile_number") val mobile_number: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("full_name") val full_name: String? = null,
    @SerializedName("avatar_list_url") val avatar_list_url: String? = null,
    @SerializedName("is_block") val is_block: Int? = null,
)