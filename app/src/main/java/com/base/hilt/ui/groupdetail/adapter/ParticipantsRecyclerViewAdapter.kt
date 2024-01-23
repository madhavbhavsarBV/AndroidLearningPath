package com.base.hilt.ui.groupdetail.adapter

import android.content.Context
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowParticipantsBinding
import com.base.hilt.ui.groupdetail.model.ParticipantsModel

class ParticipantsRecyclerViewAdapter(mContext:Context, data:ArrayList<ParticipantsModel>,
):GenericRecyclerViewAdapter<ParticipantsModel,RowParticipantsBinding>(mContext,data) {

    override val layoutResId: Int = R.layout.row_participants

    override fun onBindData(
        model: ParticipantsModel,
        position: Int,
        dataBinding: RowParticipantsBinding
    ) {
        //
    }

    override fun onItemClick(
        model: ParticipantsModel,
        position: Int,
        dataBinding: RowParticipantsBinding
    ) {
        //
    }
}