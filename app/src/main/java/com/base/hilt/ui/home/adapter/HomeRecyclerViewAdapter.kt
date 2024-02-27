package com.base.hilt.ui.home.adapter

import android.content.Context
import android.util.Log
import com.base.hilt.ChallengeListQuery
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowHomeInvitesBinding

class HomeRecyclerViewAdapter(context:Context, val list:ArrayList<ChallengeListQuery.Data1>, var onClick:(String?)->Unit
)
    : GenericRecyclerViewAdapter<ChallengeListQuery.Data1, RowHomeInvitesBinding>(context,list){

    override val layoutResId: Int
        get() = R.layout.row_home_invites

    override fun onBindData(
        model: ChallengeListQuery.Data1,
        position: Int,
        dataBinding: RowHomeInvitesBinding
    ) {
        dataBinding.model =model
    }

    override fun onItemClick(model: ChallengeListQuery.Data1, position: Int, dataBinding: RowHomeInvitesBinding) {
        Log.i("maduuid", "onItemClick: ic")
        onClick.invoke(model.uuid)
    }
}