package com.base.hilt.ui.challenge.model

import com.google.gson.annotations.SerializedName

data class ChallengeModel(

    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("start_at") var startAt: String? = null,
    @SerializedName("accept_by") var acceptBy: String? = null,
    @SerializedName("end_at") var endAt: String? = null,
    @SerializedName("judge_id") var judgeId: Int? = null,
    @SerializedName("allow_to_edit") var allowToEdit: Int? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("winner_declare_by") var winnerDeclareBy: String? = null,
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("images") var images: ArrayList<Images> = arrayListOf()
) {
    data class Images(
        @SerializedName("image") var image: String? = null
    )

}