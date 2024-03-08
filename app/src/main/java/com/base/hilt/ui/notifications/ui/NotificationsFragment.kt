package com.base.hilt.ui.notifications.ui

import PaginationScrollListener
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.base.hilt.MarkNotificationReadMutation
import com.base.hilt.NotificationsListQuery
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentNotificationsBinding
import com.base.hilt.domain.model.NotificationsListData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.MarkNotificationReadInput
import com.base.hilt.type.NotificationListInput
import com.base.hilt.ui.notifications.adapter.NotificationsRecyclerViewAdapter
import com.base.hilt.ui.notifications.viewmodel.NotificationsViewModel
import com.base.hilt.utils.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import mapToNotificationsListData
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : FragmentBase<NotificationsViewModel, FragmentNotificationsBinding>() {

    @Inject
    lateinit var mPref: MyPreference

    lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: NotificationsRecyclerViewAdapter? = null
    var notificationList: ArrayList<NotificationsListData?> = arrayListOf()
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
        getDataBinding().rcvNotifications.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                callNotificationListApi(page)

            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })

    }

    private fun setPullToRefresh() {

        getDataBinding().srlNotification.setOnRefreshListener {
            page = 1
            isLastPage = false
            isLoading = false
            callNotificationListApi(page)
            callUnReadNotificationCountApi()
        }
    }

    private fun observeData() {
        viewModel.notificationListLiveData.observe(viewLifecycleOwner) {

            isLoading = it is ResponseHandler.Loading
            if (page == 1) {
                getDataBinding().srlNotification.isRefreshing = true
            }
            else {
                adapter?.showLoader(it is ResponseHandler.Loading)
            }

            when (it) {
                is ResponseHandler.OnFailed -> {
                    getDataBinding().srlNotification.isRefreshing = false
                    isLoading = false
                    getDataBinding().pbNotification.visibility = View.GONE
                }

                is ResponseHandler.OnSuccessResponse -> {
                    getDataBinding().srlNotification.isRefreshing = false
                    isLoading = false
                    getDataBinding().pbNotification.visibility = View.GONE
                    it.response.data?.notficationsList?.data.let {
                        if (it != null) {
                            if (page == 1) notificationList.clear()
                            it.forEach {
//                                Log.i("notiread", "observeData: runing")
                                val notifcationListData = it.mapToNotificationsListData()
                                notificationList.add(notifcationListData)
                            }
//                            notificationList.addAll(it)
                            adapter?.notifyDataSetChanged()
                        }
                    }

                    val paginatorInfo = it.response.data?.notficationsList?.paginatorInfo
                    paginatorInfo?.let { it ->
                        if (it.totalPages != null) {
                            isLastPage = it.totalPages == it.currentPage
                            if (it.totalPages > page) {
                                page++
                            }
                        }

                    }
                }
            }
        }

        viewModel.unreadNotificationCountLiveData.observe(viewLifecycleOwner)
        {
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

        viewModel.markNotificationReadLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {}
                is ResponseHandler.OnFailed -> {}
                is ResponseHandler.OnSuccessResponse -> markNotificationSuccess(it)
            }

        }

    }

    private fun markNotificationSuccess(response: ResponseHandler.OnSuccessResponse<ApolloResponse<MarkNotificationReadMutation.Data>>) {
        response.let {
            Log.i(
                "notiread",
                "markNotificationSuccess: ${response.response.data?.markNotificationRead?.meta?.message}"
            )
        }

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
        linearLayoutManager = LinearLayoutManager(requireContext())
        getDataBinding().rcvNotifications.layoutManager = linearLayoutManager
        adapter = NotificationsRecyclerViewAdapter(requireContext(), notificationList,
            onItemBtnClick = {
                callReadApi(it)
            })
        getDataBinding().rcvNotifications.adapter = adapter
    }

    private fun callReadApi(uuid: String) {
        viewModel.markNotificationReadApiCall(
            MarkNotificationReadInput(
                status = Optional.Present(1),
                uuid = Optional.Present(uuid)
            )
        )
    }

}