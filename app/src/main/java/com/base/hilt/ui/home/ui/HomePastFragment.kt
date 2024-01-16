package com.base.hilt.ui.home.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.FragmentHomePastBinding
import com.base.hilt.databinding.RowHomeInvitesBinding
import com.base.hilt.ui.home.adapter.HomeRecyclerViewAdapter
import com.base.hilt.ui.home.model.HomeInvitesModel
import com.base.hilt.ui.home.viewmodel.HomeViewModel


class HomePastFragment : FragmentBase<HomeViewModel, FragmentHomePastBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_past

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

        // set up recycler view adapter
        setUpHomeInvitesAdapter()
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private fun setUpHomeInvitesAdapter() {

        val list = arrayListOf(HomeInvitesModel(), HomeInvitesModel(), HomeInvitesModel())
        getDataBinding().rvHomePast.adapter =
            HomeRecyclerViewAdapter(requireContext(), list, onClick = {
                findNavController().navigate(R.id.groupDetailFragment)
            })
        getDataBinding().rvHomePast.layoutManager = LinearLayoutManager(requireContext())

    }

}