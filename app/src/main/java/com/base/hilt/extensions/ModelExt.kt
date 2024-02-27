import com.base.hilt.ChallengeListQuery
import com.base.hilt.domain.model.ChallengeData

fun ChallengeListQuery.Data1.mapToChallengeData():ChallengeData{

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
        winner_declare_by = this?.winner_declare_by,
        winner_declare_at = this?.winner_declare_at,
        allow_to_edit = this?.allow_to_edit,
        status = this?.status,
        invite_status = this?.invite_status,
        challenge_status = this?.challenge_status,
    )

}