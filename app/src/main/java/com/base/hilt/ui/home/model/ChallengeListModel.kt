package com.base.hilt.ui.home.model

data class ChallengeListModel(
    val uuid: String?,
    val title: String?,
    val description: String?,
    val image: List<String?>?,
    val type: String?,
    val amount: String?,
    val jackpot_amount: String?,
    val start_at: String?,
    val accept_by: String?,
    val end_at: String?,
    val is_judge: Boolean?,
    val is_author: Boolean?,
    val is_participant: Boolean?,
    val is_winner: Boolean?,
    val is_spectator: Boolean?,
    val is_reported: Boolean?,
    val timestamp: String?,
    val author: Author?,
    val judge: Judge?,
    val participants: List<Participant?>?,
    val winner: Winner?,
    val external_invites_challenge: List<External_invites_challenge?>?,
    val winner_declare_by: String?,
    val winner_declare_at: String?,
    val allow_to_edit: String?,
    val status: String?,
    val invite_status: String?,
    val challenge_status: String?,
    val invitation_status_label: String?,
){
}

 data class Participant(
     val uuid: String?,
     val first_name: String?,
     val last_name: String?,
     val avatar: String?,
     val invite_status: String?,
)

 data class External_invites_challenge(
     val mobile_number: String?,
     val name: String?,
     val status: String?,
)

 data class Author(
     val uuid: String?,
     val first_name: String?,
     val last_name: String?,
     val avatar: String?,
)

 data class Judge(
     val uuid: String?,
     val first_name: String?,
     val last_name: String?,
     val avatar: String?,
)


 data class Winner(
     val uuid: String?,
     val first_name: String?,
     val last_name: String?,
     val avatar: String?,
)