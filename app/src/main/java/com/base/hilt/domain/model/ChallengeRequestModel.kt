package com.base.hilt.domain.model

data class ChallengeRequestModel(
    var title: String = "",
    var description: String = "",
    var image: String? = null,
    var amount: String = "",
    var type: String = "", // 1on1 is type 1 and Group is type 2
    var start_at: String = "",
    var end_at: String = "",
    var status: String = "",
    var accept_by: String = "",
    var judge_id: String = "",
    var participants: String = "",
    var invite_contacts: String = ""
) {
}