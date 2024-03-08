package com.base.hilt.ui.groupdetail.adapter

import android.content.Context
import com.base.hilt.ChallengeDetailQuery
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowParticipantsBinding
import mapToUserData

class ParticipantsRecyclerViewAdapter(
    mContext: Context, data: ArrayList<ChallengeDetailQuery.Participant?>,
    private val onItemBtnClick: () -> Unit
) : GenericRecyclerViewAdapter<ChallengeDetailQuery.Participant, RowParticipantsBinding>(
    mContext,
    data
) {

    override val layoutResId: Int = R.layout.row_participants

    override fun onBindData(
        model: ChallengeDetailQuery.Participant,
        position: Int,
        dataBinding: RowParticipantsBinding
    ) {
        val participantMOdel = model.mapToUserData()
        //
    }

    override fun onItemClick(
        model: ChallengeDetailQuery.Participant,
        position: Int,
        dataBinding: RowParticipantsBinding
    ) {
        onItemBtnClick.invoke()
    }
}