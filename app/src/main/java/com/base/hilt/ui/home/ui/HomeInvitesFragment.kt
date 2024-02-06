package com.base.hilt.ui.home.ui

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.FragmentHomeInvitesBinding
import com.base.hilt.databinding.RowHomeInvitesBinding
import com.base.hilt.type.ChallengeListInput
import com.base.hilt.ui.home.adapter.HomeRecyclerViewAdapter
import com.base.hilt.ui.home.model.HomeInvitesModel
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.LineNumberReader

@AndroidEntryPoint
class HomeInvitesFragment : FragmentBase<HomeViewModel, FragmentHomeInvitesBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_invites

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, type = 2, isVisible = true))
    }

    override fun initializeScreenVariables() {
        // challenge list api
//        viewModel.challengeListApiCall(ChallengeListInput(
//
//        ))

        setUpHomeInvitesAdapter()
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java


    private fun setUpHomeInvitesAdapter(){

        val list = arrayListOf(HomeInvitesModel(),HomeInvitesModel(),HomeInvitesModel())
        getDataBinding().rvHomeInvites.adapter = HomeRecyclerViewAdapter(requireContext(),list, onClick = {
            findNavController().navigate(R.id.groupDetailFragment)
        })
        getDataBinding().rvHomeInvites.layoutManager = LinearLayoutManager(requireContext())

    }

}