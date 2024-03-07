package com.base.hilt.ui.notifications.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.base.hilt.ChallengeDetailQuery
import com.base.hilt.NotificationsListQuery
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowNotificationsBinding
import com.base.hilt.domain.model.NotificationsListData

class NotificationsRecyclerViewAdapter(
    context: Context, list: ArrayList<NotificationsListData>,
    private val onItemBtnClick: (String) -> Unit
): GenericRecyclerViewAdapter<NotificationsListData, RowNotificationsBinding>(
    context,
    list
) {

    override val layoutResId: Int
        get() = R.layout.row_notifications

    override fun onBindData(
        model: NotificationsListData,
        position: Int,
        dataBinding: RowNotificationsBinding
    ) {
        dataBinding.model = model
    }

    override fun onItemClick(
        model: NotificationsListData,
        position: Int,
        dataBinding: RowNotificationsBinding
    ) {
        dataBinding.clNotification.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
        model.uuid?.let { onItemBtnClick.invoke(it) }
    }
}