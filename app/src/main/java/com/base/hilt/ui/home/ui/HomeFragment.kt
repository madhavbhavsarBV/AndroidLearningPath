package com.base.hilt.ui.home.ui

import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentHomeBinding
import com.base.hilt.ui.home.adapter.HomeViewPagerAdapter
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : FragmentBase<HomeViewModel, FragmentHomeBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, type = 2, isVisible = true))
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java


    override fun initializeScreenVariables() {


        //set view pager adapter
        setUpViewPagerAdapter()

        //set tab layout
        TabLayoutMediator(getDataBinding().tlHome, getDataBinding().vpHome) { tab, position ->

            when(position){
                0-> {
                    tab.text = getString(R.string.invites_1)
                    tab.setCustomView(R.layout.layout_tab_item)
                }
                1->tab.text = getString(R.string.active_1)
                2->tab.text = getString(R.string.past_1)
            }
        }.attach()

    }

    private fun setUpViewPagerAdapter() {

            getDataBinding().vpHome.adapter =
                HomeViewPagerAdapter(requireActivity(),
                    arrayListOf(
                        HomeInvitesFragment(),
                        HomeActiveFragment(),
                        HomePastFragment()
                    ))
//            getDataBinding().vpHome.isUserInputEnabled = false


        }

}