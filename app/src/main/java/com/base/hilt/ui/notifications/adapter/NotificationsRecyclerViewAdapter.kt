package com.base.hilt.ui.notifications.adapter

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.base.hilt.NotificationsListQuery
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowNotificationsBinding
import com.base.hilt.ui.notifications.model.NotificationsModel

class NotificationsRecyclerViewAdapter(context: Context, list: ArrayList<NotificationsListQuery.Data1>
) : GenericRecyclerViewAdapter<NotificationsListQuery.Data1, RowNotificationsBinding>(context, list) {

    override val layoutResId: Int
        get() = R.layout.row_notifications

    override fun onBindData(
        model: NotificationsListQuery.Data1,
        position: Int,
        dataBinding: RowNotificationsBinding
    ) {



    }

    override fun onItemClick(model: NotificationsListQuery.Data1, position: Int,dataBinding: RowNotificationsBinding) {
        dataBinding.clNotification.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
    }
}