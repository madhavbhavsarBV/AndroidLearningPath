package com.base.hilt.ui.home.adapter

import android.content.Context
import android.util.Log
import com.base.hilt.ChallengeListQuery
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowHomeInvitesBinding
import com.base.hilt.domain.model.ChallengeData
import com.base.hilt.ui.home.model.HomeInvitesModel
import kotlin.math.log

class ChallengeListRecyclerAdapter(context:Context, val list:ArrayList<ChallengeData>, var onClick:(String?)->Unit
)
    : GenericRecyclerViewAdapter<ChallengeData, RowHomeInvitesBinding>(context,list){

    override val layoutResId: Int
        get() = R.layout.row_home_invites

    override fun onBindData(
        model: ChallengeData,
        position: Int,
        dataBinding: RowHomeInvitesBinding
    ) {
        Log.i("model here", "onBindData: ${model}")
//        dataBinding.model =model
    }

    override fun onItemClick(model: ChallengeData, position: Int, dataBinding: RowHomeInvitesBinding) {
        Log.i("maduuid", "onItemClick: ic")
        onClick.invoke(model.uuid)
    }
}