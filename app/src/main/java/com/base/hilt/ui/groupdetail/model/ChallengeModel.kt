package com.base.hilt.ui.groupdetail.model

import com.base.hilt.ChallengeDetailQuery

data class ChallengeModel(
    val uuid: String?=null,
    val title: String?=null,
    val description: String?=null,
    val image: List<String?>?= null,
    val type: String?=null,
    val amount: String?=null,
    val jackpot_amount: String?=null,
    val total_spectators: Int?=null,
    val total_participants: Int?=null,
    val start_at: String?=null,
    val accept_by: String?=null,
    val end_at: String?=null,
    val is_judge: Boolean?=null,
    val is_author: Boolean?=null,
    val is_winner: Boolean?=null,
    val is_spectator: Boolean?=null,
    val is_participant: Boolean?=null,
    val is_ended: Boolean?=null,
    val current_date: String?=null,
    val author: Author?=null,
    val judge: Judge?=null,
    val participants: List<Participant?>?=null,
    val winner: Winner?=null,
    val modification_request: Modification_request?=null,
    val winner_declare_by: String?=null,
    val winner_declare_at: String?=null,
    val allow_to_edit: String?=null,
    val invite_status: String?=null,
    val challenge_status: String?=null,
    val invitation_status_label: String?=null,
) {
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

     data class Participant(
         val uuid: String?,
         val first_name: String?,
         val last_name: String?,
         val avatar: String?,
         val invite_status: String?,
         val mobile_number: String?,
    )

     data class Winner(
         val uuid: String?,
         val first_name: String?,
         val last_name: String?,
         val avatar: String?,
    )

     data class Modification_request(
         val uuid: String?,
         val challenge_id: Int?,
         val amount: String?,
         val description: String?,
         val judge: Judge?,
         val invitation_accept_date: String?,
         val end_at: String?,
         val amount_approved: Int?,
         val description_approved: Int?,
         val judge_id_approved: Int?,
         val invitation_accept_date_approved: Int?,
         val end_at_approved: Int?,
         val status: Int?,
         val action_on: String?,
    )
    
    fun type1v1():Boolean{
        return type=="1v1"
    }

    fun typeGroup():Boolean{
        return type=="GROUP"
    }

//    fun hostName(): String {
//        return this.author?.first_name?:"gg"
//    }
    var getJudgeName:String = this.judge?.first_name+" "+this.judge?.last_name

}