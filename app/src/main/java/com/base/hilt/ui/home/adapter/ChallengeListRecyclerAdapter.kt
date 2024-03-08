package com.base.hilt.ui.home.adapter

import android.content.Context
import android.util.Log
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowHomeInvitesBinding
import com.base.hilt.domain.model.ChallengeData

class ChallengeListRecyclerAdapter(context:Context, val list:ArrayList<ChallengeData?>, var onClick:(String?)->Unit
)
    : GenericRecyclerViewAdapter<ChallengeData, RowHomeInvitesBinding>(context,list){

    override val layoutResId: Int
        get() = R.layout.row_home_invites

    override fun onBindData(
        model: ChallengeData,
        position: Int,
        dataBinding: RowHomeInvitesBinding
    ) {
        dataBinding.model =model
        dataBinding.root.setOnClickListener {
            Log.i("clickhere1", "onBindData: gerer")
        }
    }

    override fun onItemClick(model: ChallengeData, position: Int, dataBinding: RowHomeInvitesBinding) {
        Log.i("clickhere1", "onItemClick: here0")
        onClick.invoke(model.uuid)
    }
}