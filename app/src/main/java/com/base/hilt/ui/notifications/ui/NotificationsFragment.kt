package com.base.hilt.ui.notifications.ui

import PaginationScrollListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.base.hilt.NotificationsListQuery
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentNotificationsBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.NotificationListInput
import com.base.hilt.ui.notifications.adapter.NotificationsRecyclerViewAdapter
import com.base.hilt.ui.notifications.viewmodel.NotificationsViewModel
import com.base.hilt.utils.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : FragmentBase<NotificationsViewModel, FragmentNotificationsBinding>() {

    @Inject
    lateinit var mPref: MyPreference

    lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: NotificationsRecyclerViewAdapter? = null
    private var isLoading = false
    private var page = 1
    private var unreadNotificationCount = 0
    private var isLastPage = false

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

        callNotificationListApi(page)
        callUnReadNotificationCountApi()

        getDataBinding().viewModel = viewModel

        // notification recycler view
        setUpNotificationRecyclerView()

        // refresh
        setPullToRefresh()

        //observeData
        observeData()

        //scroll Listener
        scrollListener()

    }

    private fun scrollListener() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        getDataBinding().rcvNotifications.layoutManager = linearLayoutManager

        getDataBinding().rcvNotifications.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                if (!isLoading && !isLastPage) {
                    callNotificationListApi(
                        page
                    )
                }
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })

    }

    private fun setPullToRefresh() {
        page =1
        getDataBinding().srlNotification.setOnRefreshListener {
            callNotificationListApi(page)
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


    }

    private fun callUnReadNotificationCountApi() {
        viewModel.unreadNotificationCountApiCall()
    }

    private fun callNotificationListApi(page: Int) {
        viewModel.notificationListApiCall(
            NotificationListInput(
                first = Optional.Present(10),
                page = Optional.Present(page)
            )
        )
    }


    override fun getViewModelClass(): Class<NotificationsViewModel> =
        NotificationsViewModel::class.java


    private fun setUpNotificationRecyclerView() {


    }

}