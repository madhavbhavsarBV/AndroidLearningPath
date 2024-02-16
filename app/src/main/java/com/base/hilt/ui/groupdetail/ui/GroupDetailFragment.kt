package com.base.hilt.ui.groupdetail.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentGroupDetailBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.groupdetail.adapter.ParticipantsRecyclerViewAdapter
import com.base.hilt.ui.groupdetail.model.ChallengeModel
import com.base.hilt.ui.groupdetail.model.ParticipantsModel
import com.base.hilt.ui.groupdetail.viewmodel.GroupDetailViewModel
import com.base.hilt.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailFragment : FragmentBase<GroupDetailViewModel, FragmentGroupDetailBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_group_detail

    private var uuid: String? = null

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                type = 1,
                title = getString(R.string.expired),
                isBottomNavVisible = false,
                loginVisible = false,
                shareBtnVisible = true,
                tlGradient = true,
                backBtnVisible = true
            )
        )
    }

    override fun initializeScreenVariables() {

        uuid = arguments?.getString(Constants.UUID)

        getDataBinding().layGroupDetail.viewmodel = viewModel
//        getDataBinding().layComments.viewmodel= viewModel

        //observe data
        observeData()

        //status bar color change
        (requireActivity() as MainActivity).backGroundColor()

        //set Up Participants Recycler Adapter
        setUpParticipantsRecyclerAdapter()

        //scroll listener
        scrollListener()


    }

    private fun scrollListener() {

    }

    private fun setUpParticipantsRecyclerAdapter() {
        val adapter = ParticipantsRecyclerViewAdapter(requireContext(), arrayListOf(
            ParticipantsModel(),
            ParticipantsModel(),
            ParticipantsModel()
        ), onItemBtnClick = {
            val dialog = ParticipantsListFragment()
            dialog.show(childFragmentManager, "")
        })
        getDataBinding().layGroupDetail.rcvParticipants.adapter = adapter
        getDataBinding().layGroupDetail.rcvParticipants.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun observeData() {

        viewModel.onCommentClick?.observe(viewLifecycleOwner) {
            val dialog = CommentsBottomSheetFragment()
            dialog.show(childFragmentManager, "")
        }

        viewModel.challengeDetailLiveData.observe(viewLifecycleOwner){
            when(it){
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                    Log.i("maduuid", "callApi:loading")
                }
                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    Log.i("maduuid", "callApi: ${it.message}")
                }
                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                    Log.i("maduuid", "callApi: ${it.response.data?.challengeDetail.toString()}")
                    it.response.data?.challengeDetail.let {
                        getDataBinding().layGroupDetail.model = ChallengeModel(type = it?.data?.type)
                    }

                }
            }
        }

    }

    override fun getViewModelClass(): Class<GroupDetailViewModel> = GroupDetailViewModel::class.java
    override fun onDestroyView() {

        //setStatus bar color black
        (requireActivity() as MainActivity).backGroundColorBlack()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        uuid = arguments?.getString(Constants.UUID)
        callApi()
    }

    private fun callApi() {
        Log.i("maduuid", "callApi: ${uuid} ${arguments?.getString(Constants.UUID)}")
        uuid?.let {

            viewModel.challengeDetailApiCall(it)
        }
    }
}

