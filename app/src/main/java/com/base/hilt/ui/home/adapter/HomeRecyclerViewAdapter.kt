package com.base.hilt.ui.home.adapter

import android.content.Context
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowHomeInvitesBinding
import com.base.hilt.ui.home.model.HomeInvitesModel

class HomeRecyclerViewAdapter(context:Context, val list:ArrayList<HomeInvitesModel>, var onClick:()->Unit
)
    : GenericRecyclerViewAdapter<HomeInvitesModel, RowHomeInvitesBinding>(context,list){

    override val layoutResId: Int
        get() = R.layout.row_home_invites

    override fun onBindData(
        model: HomeInvitesModel,
        position: Int,
        dataBinding: RowHomeInvitesBinding
    ) {
        //
    }

    override fun onItemClick(model: HomeInvitesModel, position: Int, dataBinding: RowHomeInvitesBinding) {
        onClick.invoke()
    }
}