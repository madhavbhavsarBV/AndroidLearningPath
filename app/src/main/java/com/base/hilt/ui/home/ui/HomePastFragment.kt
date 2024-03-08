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
import com.base.hilt.databinding.FragmentHomePastBinding
import com.base.hilt.domain.model.ChallengeData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ChallengeListInput
import com.base.hilt.ui.home.adapter.ChallengeListRecyclerAdapter
import com.base.hilt.ui.home.interfaces.NoRecordsFound
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.base.hilt.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePastFragment : FragmentBase<HomeViewModel, FragmentHomePastBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_past

    lateinit var noRecords: NoRecordsFound
    val gson = Gson()
    val type = object : TypeToken<List<ChallengeData>>() {}.type
    var page = 1
    var isLastPage = false
    var isLoading = false
    var invitesList: ArrayList<ChallengeData?> = arrayListOf()
    var oldArray: ArrayList<ChallengeListQuery.Data1> = arrayListOf()
    lateinit var adapter: ChallengeListRecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isBottomNavVisible = true,
                isVisible = true,
                type = 2
            )
        )
    }

    override fun initializeScreenVariables() {

        setUpRecyclerView()

        // observeData
        observeData()

        // setOnRefreshListener
        setOnRefreshListener()

        // set Pagination in Recycler View
        setPaginationRecyclerView()
    }

    private fun setPaginationRecyclerView() {

        getDataBinding().rvHomePast.addOnScrollListener(object :
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
        adapter =
            ChallengeListRecyclerAdapter(
                context = requireContext(),
                list = invitesList,
                onClick = {
//            Log.i("clickhere1", "setUpRecyclerView: here")
                    val bundle = Bundle()
                    bundle.putString(Constants.UUID, it)
                    noRecords.noRecords(false)
                    findNavController().navigate(R.id.groupDetailFragment, bundle)
                })

        getDataBinding().rvHomePast.adapter = adapter
        layoutManager = LinearLayoutManager(requireContext())
        getDataBinding().rvHomePast.layoutManager = layoutManager
    }

    private fun callApi(page: Int) {
//        Log.i("madapicallhere", "callApi: $page")
        viewModel.challengeListApiCall(
            ChallengeListInput(
                first = Optional.Present(10),
                page = Optional.Present(page),
                type = Optional.Present(getString(R.string.api_past))
            )
        )
    }

    fun setInterface(i: NoRecordsFound) {
        noRecords = i
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
                    else {
                        isLoading = true
                        getDataBinding().pbLoading.visibility = View.VISIBLE
                    }
                }

                is ResponseHandler.OnFailed -> {
                    getDataBinding().srlInvites.isRefreshing = false
                    isLoading = false
                    getDataBinding().pbLoading.visibility = View.GONE
                }

                is ResponseHandler.OnSuccessResponse -> {
                    getDataBinding().srlInvites.isRefreshing = false
                    isLoading = false
                    getDataBinding().pbLoading.visibility = View.GONE
                    val response = it.response.data?.challengeList?.data
                    response.let {
                        if (it.isNullOrEmpty()) {
                            noRecords.noRecords(true)
                            getDataBinding().rvHomePast.visibility = View.GONE
                        } else {
                            noRecords.noRecords(false)
                            getDataBinding().rvHomePast.visibility = View.VISIBLE
                            oldArray = it as ArrayList<ChallengeListQuery.Data1>
                            val myObjectList: List<ChallengeData> =
                                gson.fromJson(gson.toJson(it), type)
                            if (page == 1) invitesList.clear()
                            invitesList.addAll(myObjectList)
                            adapter.notifyDataSetChanged()

//                            val nadapter = HomeRecyclerViewAdapter(requireContext(),oldArray, onClick = {
//                                val bundle = Bundle()
//                                bundle.putString(Constants.UUID,it)
//                                findNavController().navigate(R.id.groupDetailFragment,bundle)
//                            })
//                            getDataBinding().rvHomePast.adapter = nadapter
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