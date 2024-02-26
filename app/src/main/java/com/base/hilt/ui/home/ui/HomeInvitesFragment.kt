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
import com.base.hilt.databinding.FragmentHomeInvitesBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ChallengeListInput
import com.base.hilt.ui.home.adapter.HomeRecyclerViewAdapter
import com.base.hilt.ui.home.model.ChallengeListModel
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.base.hilt.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeInvitesFragment() :
    FragmentBase<HomeViewModel, FragmentHomeInvitesBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_invites

    lateinit var adapter: HomeRecyclerViewAdapter
    var page = 1
    var isLastPage = false
    var isLoading = false
    lateinit var layoutManager: LinearLayoutManager
    var invitesList: ArrayList<ChallengeListModel> = arrayListOf()

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

        callApi(1)

        //set Recycler View
        setRecyclerView()
        // observeData
        observeData()

        // setOnRefreshListener
        setOnRefreshListener()

        // scroll listener
        setOnScrollListener()
    }

    private fun setRecyclerView() {
        layoutManager = LinearLayoutManager(requireContext())
        getDataBinding().rvHomeInvites.layoutManager = layoutManager
    }

    private fun setOnScrollListener() {

        getDataBinding().rvHomeInvites.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (!isLoading && !isLastPage) callApi(page)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

//        loadFirstPage()

    }

    private fun callApi(page: Int) {
        viewModel.challengeListApiCall(
            ChallengeListInput(
                first = Optional.Present(10),
                page = Optional.Present(page),
                type = Optional.Present(getString(R.string.api_invites))
            )
        )
    }

    private fun setOnRefreshListener() {
        getDataBinding().srlInvites.setOnRefreshListener {
            callApi(1)
        }
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

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


                    it.response.data.let {
                        it?.challengeList?.data.let {
                            Log.i("madhere", "observeData: ${it}")
                            if (!it.isNullOrEmpty()) {
                                Log.i("madhere", "observeData:1")
                                getDataBinding().layNoData.groupIfListEmpty.visibility = View.GONE
                                getDataBinding().rvHomeInvites.visibility = View.VISIBLE
                            } else {
                                Log.i("madhere", "observeData:2")
                                it?.forEach { model ->
//                                    val challengeListModel = model as ChallengeListModel
//                                    val stringAnimal = Gson().toJson(this, Animal::class.java)
//                                    invitesList.add(challengeListModel)
                                }
                                Log.i("madhere", "observeData: madhere ${invitesList}")
                                getDataBinding().layNoData.groupIfListEmpty.visibility =
                                    View.VISIBLE
                                getDataBinding().rvHomeInvites.visibility = View.GONE
                            }
                        }

                        it?.challengeList?.paginatorInfo?.let { it ->
                            if (it.totalRecords == 0) {
                                getDataBinding().layNoData.groupIfListEmpty.visibility =
                                    View.VISIBLE
                                getDataBinding().rvHomeInvites.visibility = View.GONE
                            } else {
                                if (it.totalPages != null) {
                                    isLastPage = it.totalPages <= page
                                    if (it.totalPages > page) {
                                        page++
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }


    }

    private fun setUpHomeInvitesAdapter(list: List<ChallengeListQuery.Data1>) {

//        val list = arrayListOf(HomeInvitesModel(), HomeInvitesModel(), HomeInvitesModel())
        getDataBinding().rvHomeInvites.adapter =
            HomeRecyclerViewAdapter(requireContext(),
                list as ArrayList<ChallengeListQuery.Data1>, onClick = {
                    val bundle = Bundle()
                    bundle.putString(Constants.UUID, it)
                    findNavController().navigate(R.id.groupDetailFragment, bundle)
                })
        getDataBinding().rvHomeInvites.layoutManager = LinearLayoutManager(requireContext())

    }
}