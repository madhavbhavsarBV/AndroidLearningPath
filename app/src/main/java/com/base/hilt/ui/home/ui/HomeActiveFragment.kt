package com.base.hilt.ui.home.ui

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
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ChallengeListInput
import com.base.hilt.ui.home.adapter.HomeRecyclerViewAdapter
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActiveFragment : FragmentBase<HomeViewModel,FragmentHomeActiveBinding >(){
    override fun getLayoutId(): Int = R.layout.fragment_home_active

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, type = 2, isVisible = true))
    }

    override fun initializeScreenVariables() {

        // observeData
        observeData()

        // setOnRefreshListener
        setOnRefreshListener()

    }

    private fun callApi(){
        viewModel.challengeListApiCall(
            ChallengeListInput(
                first = Optional.Present(10),
                page = Optional.Present(1),
                type = Optional.Present(getString(R.string.api_active))
            )
        )
    }

    private fun setOnRefreshListener() {

        getDataBinding().srlInvites.setOnRefreshListener {
            callApi()
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
                                getDataBinding().groupIfListEmpty.visibility = View.GONE
                                getDataBinding().rvHomeActive.visibility = View.VISIBLE
                                setUpHomeInvitesAdapter(it)
                            } else {
                                getDataBinding().groupIfListEmpty.visibility = View.VISIBLE
                                getDataBinding().rvHomeActive.visibility = View.GONE
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
                list as ArrayList<ChallengeListQuery.Data1>, onClick = {
                    findNavController().navigate(R.id.groupDetailFragment)
                })
        getDataBinding().rvHomeActive.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onResume() {
        super.onResume()
        callApi()
        Log.i("madres", "onResume: active tab change res called")
    }

}