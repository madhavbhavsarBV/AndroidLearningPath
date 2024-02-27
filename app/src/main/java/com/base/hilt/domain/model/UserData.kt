package com.base.hilt.domain.model

data class UserData(
    val uuid: String?,
    val chat_id: String?,
    val id: Int?,
    var first_name: String?,
    var last_name: String?,
    val avatar: String? = null,
    val email: String?,
    val mobile_number: String?,
    val status: String?,
    val email_verified_at: String?,
    val verified: String?,
    var dob: String?,
    val referral_code: String?,
    val wallet_amount: String?,
    val usable_wallet_amount: String?,
    val total_credit_amount: String?,
    val notification_settings: String?,
    val chat_jwt_token: String? = null,
    val user_timezone: String? = null,
    val original_avatar: String? = null,
)