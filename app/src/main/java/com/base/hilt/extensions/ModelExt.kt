import com.base.hilt.ChallengeDetailQuery
import com.base.hilt.ChallengeListQuery
import com.base.hilt.NotificationsListQuery
import com.base.hilt.domain.model.ChallengeData
import com.base.hilt.domain.model.NotificationsListData


fun ChallengeListQuery.Author?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

fun ChallengeListQuery.Judge?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

//fun ChallengeListQuery.Judge1?.mapToUserData(): ChallengeData.UserData {
//    return ChallengeData.UserData(
//        uuid = this?.uuid,
//        first_name = this?.first_name,
//        last_name = this?.last_name,
//        avatar = this?.avatar,
//        invite_status = null
//    )
//}

fun ChallengeListQuery.Participant?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = this?.invite_status
    )
}

fun ChallengeListQuery.Winner?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

//fun ChallengeListQuery.Modification_request?.mapToModificationData(): ChallengeData.ModificationData {
//    return ChallengeData.ModificationData(
//        uuid = this?.uuid,
//        challenge_id = this?.challenge_id.toString(),
//        amount = this?.amount,
//        description = this?.description,
//        judge = this?.judge?.mapToUserData(),
//        invitation_accept_date = this?.invitation_accept_date,
//        end_at = this?.end_at,
//        amount_approved = this?.amount_approved.toString(),
//        description_approved = this?.description_approved.toString(),
//        judge_id_approved = this?.judge_id_approved.toString(),
//        invitation_accept_date_approved = this?.invitation_accept_date_approved.toString(),
//        end_at_approved = this?.end_at_approved.toString(),
//        status = this?.status.toString(),
//        action_on = this?.action_on
//    )
//}

fun ChallengeListQuery.Data1?.mapToChallengeData(): ChallengeData {
    return ChallengeData(
        uuid = this?.uuid,
        title = this?.title,
        description = this?.description,
        image = this?.image,
        type = this?.type,
        amount = this?.amount,
        jackpot_amount = this?.jackpot_amount,
        start_at = this?.start_at,
        accept_by = this?.accept_by,
        end_at = this?.end_at,
        is_judge = this?.is_judge,
        is_author = this?.is_author,
        is_winner = this?.is_winner,
        is_spectator = this?.is_spectator,
        is_participant = this?.is_participant,
        author = this?.author?.mapToUserData(),
        judge = this?.judge?.mapToUserData(),
        participants = this?.participants?.map { it?.mapToUserData() },
        winner = this?.winner?.mapToUserData(),
        winner_declare_by = this?.winner_declare_by,
        winner_declare_at = this?.winner_declare_at,
        allow_to_edit = this?.allow_to_edit,
        status = this?.status,
        invite_status = this?.invite_status,
        challenge_status = this?.challenge_status,
        timestamp = this?.timestamp,
        invitation_status_label = this?.invitation_status_label
    )
}


fun ChallengeDetailQuery.Data1?.mapToChallengeData(): ChallengeData {
    return ChallengeData(
        uuid = this?.uuid,
        title = this?.title,
        description = this?.description,
        image = this?.image,
        type = this?.type,
        amount = this?.amount,
        jackpot_amount = this?.jackpot_amount,
        start_at = this?.start_at,
        accept_by = this?.accept_by,
        end_at = this?.end_at,
        is_judge = this?.is_judge,
        is_author = this?.is_author,
        is_winner = this?.is_winner,
        is_spectator = this?.is_spectator,
        is_participant = this?.is_participant,
        is_ended = this?.is_ended,
        author = this?.author?.mapToUserData(),
        judge = this?.judge?.mapToUserData(),
        participants = this?.participants?.map { it?.mapToUserData() },
        winner = this?.winner?.mapToUserData(),
        winner_declare_by = this?.winner_declare_by,
        winner_declare_at = this?.winner_declare_at,
        allow_to_edit = this?.allow_to_edit,
        invite_status = this?.invite_status,
        challenge_status = this?.challenge_status,
        modification_request = this?.modification_request?.mapToModificationData(),
        total_spectators = this?.total_spectators,
        total_participants = this?.total_participants,
        current_date = this?.current_date,
        invitation_status_label = this?.invitation_status_label
    )
}


fun ChallengeDetailQuery.Author?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

fun ChallengeDetailQuery.Judge1?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

fun ChallengeDetailQuery.Judge?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

fun ChallengeDetailQuery.Participant?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = this?.invite_status
    )
}

fun ChallengeDetailQuery.Winner?.mapToUserData(): ChallengeData.UserData {
    return ChallengeData.UserData(
        uuid = this?.uuid,
        first_name = this?.first_name,
        last_name = this?.last_name,
        avatar = this?.avatar,
        invite_status = null
    )
}

fun ChallengeDetailQuery.Modification_request?.mapToModificationData(): ChallengeData.ModificationData {
    return ChallengeData.ModificationData(
        uuid = this?.uuid,
        challenge_id = this?.challenge_id.toString(),
        amount = this?.amount,
        description = this?.description,
        judge = this?.judge?.mapToUserData(),
        invitation_accept_date = this?.invitation_accept_date,
        end_at = this?.end_at,
        amount_approved = this?.amount_approved.toString(),
        description_approved = this?.description_approved.toString(),
        judge_id_approved = this?.judge_id_approved.toString(),
        invitation_accept_date_approved = this?.invitation_accept_date_approved.toString(),
        end_at_approved = this?.end_at_approved.toString(),
        status = this?.status.toString(),
        action_on = this?.action_on
    )
}


fun NotificationsListQuery.Data1.mapToNotificationsListData(): NotificationsListData {
    return NotificationsListData(
        uuid = this.uuid,
        title = this.title,
        content = this.content,
        read = this.read,
        created_at = this.created_at,
        image = this.image,
        action_uuid = this.action_uuid,
        type = this.type,
        ago = this.ago,
        push_type = this.push_type,
    )

}