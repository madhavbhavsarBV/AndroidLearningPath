package com.base.hilt.ui.groupdetail.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.base.hilt.ChallengeDetailQuery
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentGroupDetailBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.groupdetail.adapter.ParticipantsRecyclerViewAdapter
import com.base.hilt.ui.groupdetail.viewmodel.GroupDetailViewModel
import com.base.hilt.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import mapToChallengeData

@AndroidEntryPoint
class GroupDetailFragment : FragmentBase<GroupDetailViewModel, FragmentGroupDetailBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_group_detail
    private var uuid: String? = null
    private var adapter: ParticipantsRecyclerViewAdapter? = null

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


        getDataBinding().layGroupDetail.viewmodel = viewModel

        //observe data
        observeData()

        //status bar color change
        (requireActivity() as MainActivity).backGroundColor()

        //scroll listener
        scrollListener()

    }

    private fun scrollListener() {

    }


    private fun observeData() {

        viewModel.onCommentClick?.observe(viewLifecycleOwner) {
            val dialog = CommentsBottomSheetFragment()
            dialog.show(childFragmentManager, "")
        }

        viewModel.challengeDetailLiveData.observe(viewLifecycleOwner) {
            viewModel.showProgressBar(it is ResponseHandler.Loading)
            when (it) {
                is ResponseHandler.OnFailed -> {
                }

                is ResponseHandler.OnSuccessResponse -> {
                    val response = it.response.data?.challengeDetail?.data
                    response.let {
                        val challengeData = it.mapToChallengeData()
                        getDataBinding().layGroupDetail.model = challengeData
                    }

                    it.response.data?.challengeDetail.let {
                        it?.data?.participants.let {
                            if (it != null) {
                                setUpParticipantsRecyclerView(it as List<ChallengeDetailQuery.Participant>)
                            }
                        }
                    }


                }
            }
        }

    }

    override fun getViewModelClass(): Class<GroupDetailViewModel> = GroupDetailViewModel::class.java
    override fun onDestroyView() {
//        setStatus bar color black
        (requireActivity() as MainActivity).backGroundColorBlack()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        uuid = arguments?.getString(Constants.UUID)
        callApi()
    }

    private fun callApi() {
        uuid?.let {
            viewModel.challengeDetailApiCall(it)
        }
    }

    private fun setUpParticipantsRecyclerView(list: List<ChallengeDetailQuery.Participant>) {
        adapter = ParticipantsRecyclerViewAdapter(
            requireContext(),
            list as ArrayList<ChallengeDetailQuery.Participant>,
            onItemBtnClick = {
                val dialog = ParticipantsListFragment()
                dialog.show(childFragmentManager, "")
            })
        getDataBinding().layGroupDetail.rcvParticipants.adapter = adapter
        getDataBinding().layGroupDetail.rcvParticipants.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }
}