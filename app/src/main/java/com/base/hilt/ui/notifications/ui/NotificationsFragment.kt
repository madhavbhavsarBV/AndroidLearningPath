package com.base.hilt.ui.notifications.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.base.hilt.MainActivity
import com.base.hilt.NotificationsListQuery
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.LocaleManager
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentNotificationsBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.NotificationListInput
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

        callNotificationListApi()
        callUnReadNotificationCountApi()

        getDataBinding().viewModel = viewModel

        // notification recycler view
        setUpNotificationRecyclerView()

        // refresh
        setPullToRefresh()

        //oberveData
        observeData()

    }

    private fun setPullToRefresh() {
        getDataBinding().srlNotification.setOnRefreshListener {
            callNotificationListApi()
            callUnReadNotificationCountApi()
        }
    }

    private fun observeData() {
        viewModel.notificationListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    getDataBinding().srlNotification.isRefreshing = true
                }

                is ResponseHandler.OnFailed -> {
                    getDataBinding().srlNotification.isRefreshing = false
                }

                is ResponseHandler.OnSuccessResponse -> {
                    getDataBinding().srlNotification.isRefreshing = false
                    it.response.data?.notficationsList?.data.let {
                        if (it != null) {
                            setNotificationList(it)
                        }
                    }
                }
            }
        }

        viewModel.unreadNotificationCountLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    getDataBinding().srlNotification.isRefreshing = true
                }

                is ResponseHandler.OnFailed -> {
                    getDataBinding().srlNotification.isRefreshing = false
                }

                is ResponseHandler.OnSuccessResponse -> {
                    getDataBinding().srlNotification.isRefreshing = false
                }
            }
        }

    }

    private fun setNotificationList(list: List<NotificationsListQuery.Data1>) {

        getDataBinding().rcvNotifications.adapter = NotificationsRecyclerViewAdapter(
            requireContext(),
            list as ArrayList<NotificationsListQuery.Data1>
        )
        getDataBinding().rcvNotifications.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun callUnReadNotificationCountApi() {
        viewModel.unreadNotificationCountApiCall()
    }

    private fun callNotificationListApi() {
        viewModel.notificationListApiCall(
            NotificationListInput(
                first = Optional.Present(10),
                page = Optional.Present(1)
            )
        )
    }


    override fun getViewModelClass(): Class<NotificationsViewModel> =
        NotificationsViewModel::class.java


    private fun setUpNotificationRecyclerView() {


    }

}