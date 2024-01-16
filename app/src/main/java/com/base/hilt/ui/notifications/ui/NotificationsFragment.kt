package com.base.hilt.ui.notifications.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.LocaleManager
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentNotificationsBinding
import com.base.hilt.ui.notifications.adapter.NotificationsRecyclerViewAdapter
import com.base.hilt.ui.notifications.model.NotificationsModel
import com.base.hilt.ui.notifications.viewmodel.NotificationsViewModel
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : FragmentBase<NotificationsViewModel, FragmentNotificationsBinding>() {

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var mPref: MyPreference
    override fun getLayoutId(): Int {
        return R.layout.fragment_notifications
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isBottomNavVisible = true,
                type = 3,
                isVisible = true,
                tvMarkAllReadVisible = true,
                title = getString(R.string.notifications)
            )
        )
    }

    override fun initializeScreenVariables() {

        getDataBinding().viewModel = viewModel

        // notification recycler view
        setUpNotificationRecyclerView()

    }


    override fun getViewModelClass(): Class<NotificationsViewModel> =
        NotificationsViewModel::class.java


    private fun setUpNotificationRecyclerView(){
        val list = arrayListOf(NotificationsModel(),NotificationsModel(),NotificationsModel(),NotificationsModel(),NotificationsModel(),NotificationsModel(),NotificationsModel(),NotificationsModel(),NotificationsModel())
        getDataBinding().rcvNotifications.adapter = NotificationsRecyclerViewAdapter(requireContext(), list)
        getDataBinding().rcvNotifications.layoutManager=  LinearLayoutManager(requireContext())

    }

}