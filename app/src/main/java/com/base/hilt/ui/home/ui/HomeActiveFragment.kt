package com.base.hilt.ui.home.ui

import PaginationScrollListener
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.base.hilt.ChallengeListQuery
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentHomeActiveBinding
import com.base.hilt.domain.model.ChallengeData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ChallengeListInput
import com.base.hilt.ui.home.adapter.ChallengeListRecyclerAdapter
import com.base.hilt.ui.home.adapter.HomeRecyclerViewAdapter
import com.base.hilt.ui.home.interfaces.NoRecordsFound
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.base.hilt.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActiveFragment : FragmentBase<HomeViewModel, FragmentHomeActiveBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_active

    lateinit var noRecords : NoRecordsFound
    val gson = Gson()
    val type = object : TypeToken<List<ChallengeData>>() {}.type
    var page = 1
    var isLastPage = false
    var isLoading = false
    var invitesList: ArrayList<ChallengeData?> = arrayListOf()
    lateinit var adapter: ChallengeListRecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isBottomNavVisible = true,
                type = 2,
                isVisible = true
            )
        )
    }

    override fun initializeScreenVariables() {

        //set Up Recycler View
        setUpRecyclerView()

        // observeData
        observeData()

        // setOnRefreshListener
        setOnRefreshListener()

        // set Pagination in Recycler View
        setPaginationRecyclerView()

    }

    private fun setPaginationRecyclerView() {

        getDataBinding().rvHomeActive.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                Log.i("apicalled", "loadMoreItems: called$page")
                isLoading = true
                callApi(page)
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })
    }

    private fun setUpRecyclerView() {
        adapter = ChallengeListRecyclerAdapter(requireContext(), invitesList, onClick = {
            noRecords.noRecords(false)
            findNavController().navigate(R.id.groupDetailFragment)
        })
        getDataBinding().rvHomeActive.adapter = adapter
        layoutManager = LinearLayoutManager(requireContext())
        getDataBinding().rvHomeActive.layoutManager = layoutManager
    }
    fun setInterface(i : NoRecordsFound){
        noRecords = i
    }

    private fun callApi(page: Int) {
        viewModel.challengeListApiCall(
            ChallengeListInput(
                first = Optional.Present(10),
                page = Optional.Present(page),
                type = Optional.Present(getString(R.string.api_active))
            )
        )
    }

    private fun setOnRefreshListener() {
        getDataBinding().srlInvites.setOnRefreshListener {
            page = 1
            invitesList.clear()
            isLastPage = false
            isLoading = false
            callApi(page)
        }
    }

    private fun observeData() {

        viewModel.challengeListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    if (page == 1) getDataBinding().srlInvites.isRefreshing = true
                }

                is ResponseHandler.OnFailed -> {
                    getDataBinding().srlInvites.isRefreshing = false
                    isLoading = false
                }

                is ResponseHandler.OnSuccessResponse -> {
                    getDataBinding().srlInvites.isRefreshing = false
                    isLoading = false

                    val response = it.response.data?.challengeList?.data
                    response.let {
                        if (it.isNullOrEmpty()) {
                            noRecords.noRecords(true)
//                            getDataBinding().layNoData.groupIfListEmpty.visibility =
//                                View.VISIBLE
                            getDataBinding().rvHomeActive.visibility = View.GONE
                        } else {
                            noRecords.noRecords(false)
//                            getDataBinding().layNoData.groupIfListEmpty.visibility = View.GONE
                            getDataBinding().rvHomeActive.visibility = View.VISIBLE
                            val myObjectList: List<ChallengeData> =
                                gson.fromJson(gson.toJson(it), type)
                            if (page == 1) invitesList.clear()
                            invitesList.addAll(myObjectList)
                            adapter.notifyDataSetChanged()
                        }
                    }


                    val paginatorInfo = it.response.data?.challengeList?.paginatorInfo
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


    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    private fun setUpHomeInvitesAdapter(list: List<ChallengeListQuery.Data1>) {

//        val list = arrayListOf(HomeInvitesModel(), HomeInvitesModel(), HomeInvitesModel())
        getDataBinding().rvHomeActive.adapter =
            HomeRecyclerViewAdapter(requireContext(),
                list as ArrayList<ChallengeListQuery.Data1?>, onClick = {
                    val bundle = Bundle()
                    bundle.putString(Constants.UUID, it)
                    findNavController().navigate(R.id.groupDetailFragment, bundle)
                })
        getDataBinding().rvHomeActive.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onResume() {
        super.onResume()
        page = 1
        noRecords.noRecords(false)
        invitesList.clear()
        isLoading = false
        isLastPage = false
        callApi(page)
    }
    override fun onPause() {
        super.onPause()
        invitesList.clear()
        adapter?.notifyDataSetChanged()
    }
}