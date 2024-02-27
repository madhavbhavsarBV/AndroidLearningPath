package com.base.hilt.ui.home.ui

import PaginationScrollListener
import android.util.Log
import android.view.View
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
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePastFragment : FragmentBase<HomeViewModel, FragmentHomePastBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_past

    lateinit var adapter: ChallengeListRecyclerAdapter
    var page = 1
    var isLastPage = false
    var isLoading = false
    lateinit var layoutManager: LinearLayoutManager
    var invitesList: ArrayList<ChallengeData> = arrayListOf()

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

        getDataBinding().rvHomePast.addOnScrollListener(object:PaginationScrollListener(layoutManager){
            override fun loadMoreItems() {
                isLoading = true
                callApi(page)
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })

    }

    private fun setUpRecyclerView() {
        adapter = ChallengeListRecyclerAdapter(requireContext(), invitesList, onClick = {

        })
        getDataBinding().rvHomePast.adapter = adapter
        layoutManager = LinearLayoutManager(requireContext())
        getDataBinding().rvHomePast.layoutManager = layoutManager
    }

    private fun callApi(page:Int) {
        viewModel.challengeListApiCall(
            ChallengeListInput(
                first = Optional.Present(10),
                page = Optional.Present(page),
                type = Optional.Present(getString(R.string.api_past))
            )
        )
    }

    private fun setOnRefreshListener() {

        getDataBinding().srlInvites.setOnRefreshListener {
            callApi(page)
        }
    }

    private fun observeData() {

        viewModel.challengeListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    getDataBinding().srlInvites.isRefreshing = true
                }

                is ResponseHandler.OnFailed -> {
                    getDataBinding().srlInvites.isRefreshing = false
                    ResponseHandler.OnFailed(0, it.message.toString(), "")
                }

                is ResponseHandler.OnSuccessResponse -> {
                    getDataBinding().srlInvites.isRefreshing = false
                    Log.i("maddata", "observeData: ${it.response.data?.challengeList}")
                    it.response.data.let {
                        it?.challengeList?.data.let {
                            if (!it.isNullOrEmpty()) {
                                getDataBinding().layNoData.groupIfListEmpty.visibility = View.GONE
                                getDataBinding().rvHomePast.visibility = View.VISIBLE
                                setUpHomeInvitesAdapter(it)
                            } else {
                                getDataBinding().layNoData.groupIfListEmpty.visibility = View.VISIBLE
                                getDataBinding().rvHomePast.visibility = View.GONE
                            }
                        }
                    }

                    val response = it.response.data?.challengeList?.data
                    response.let {
                        val gson = Gson()
                        var jsonString = gson.toJson(it)
                        val type = object : TypeToken<List<ChallengeData>>() {}.type
                        val myObjectList: List<ChallengeData> = gson.fromJson(jsonString, type)

                        if (page==1) invitesList.clear()
                        invitesList.addAll(myObjectList)
                        adapter.notifyDataSetChanged()
                    }


                    val paginatorInfo= it.response.data?.challengeList?.paginatorInfo
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
//        getDataBinding().rvHomePast.adapter =
//            HomeRecyclerViewAdapter(requireContext(),
//                list as ArrayList<ChallengeListQuery.Data1>, onClick = {
//                    val bundle = Bundle()
//                    bundle.putString(Constants.UUID,it)
//                    findNavController().navigate(R.id.groupDetailFragment,bundle)
//                })
//        getDataBinding().rvHomePast.layoutManager = LinearLayoutManager(requireContext())

    }


    override fun onResume() {
        super.onResume()
        page =1
        isLoading = false
        isLastPage = false
        callApi(page)
    }

}