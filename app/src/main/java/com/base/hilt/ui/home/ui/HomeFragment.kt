package com.base.hilt.ui.home.ui

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentHomeBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.home.adapter.HomeViewPagerAdapter
import com.base.hilt.ui.home.interfaces.NoRecordsFound
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.base.hilt.utils.MyPreference
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : FragmentBase<HomeViewModel, FragmentHomeBinding>(), NoRecordsFound {


    @Inject
    lateinit var pref: MyPreference

    lateinit var homeInvites: HomeInvitesFragment
    lateinit var homeActive: HomeActiveFragment
    lateinit var homePast: HomePastFragment

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isBottomNavVisible = true,
                type = 2,
                isVisible = true
            )
        )
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java


    override fun initializeScreenVariables() {

        Log.i("currrfrag", "initializeScreenVariables: ${currentFragName}")
        //set view pager adapter
        setUpViewPagerAdapter()

        //set tab layout
        TabLayoutMediator(getDataBinding().tlHome, getDataBinding().vpHome) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = getString(R.string.invites_1)
                    tab.setCustomView(R.layout.layout_tab_item)
                }

                1 -> tab.text = getString(R.string.active_1)
                2 -> tab.text = getString(R.string.past_1)
            }
        }.attach()

        viewModel.userDataApiCall()
        viewModel.challengeListingCountApiCall()
        viewModel.unreadNotificationCountApiCall()

        // observe data
        observeData()

    }

    private fun observeData() {
        viewModel.userDataLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }

                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                }

                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                }
            }
        }
        viewModel.challengeListingCountLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }

                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                }

                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                }
            }
        }
        viewModel.unreadNotificationCountLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }

                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                }

                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                }
            }
        }


    }

    private fun setUpViewPagerAdapter() {

        homeInvites = HomeInvitesFragment()
        homeActive = HomeActiveFragment()
        homePast = HomePastFragment()

        homeInvites.setInterface(this)
        homePast.setInterface(this)
        homeActive.setInterface(this)

        getDataBinding().vpHome.adapter =
            HomeViewPagerAdapter(fa = requireActivity(), list = arrayListOf(homeInvites, homeActive, homePast))


        getDataBinding().vpHome.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when(position){
//                    0->{
//                        getDataBinding().layNoData.tvActiveChallenges.text = getString(R.string.currently_no_invite)
//                    }
//                    1->{
//                        getDataBinding().layNoData.tvActiveChallenges.text = getString(R.string.currently_no_active_challenges)
//                    }
//                    2->{
//                        getDataBinding().layNoData.tvActiveChallenges.text = getString(R.string.currently_no_past_challenges)
//                    }

                }
            }

        })
    }

    override fun noRecords(b: Boolean) {
        if (b) {
            getDataBinding().layNoData.clNoData.visibility = View.VISIBLE
        } else {
            getDataBinding().layNoData.clNoData.visibility = View.GONE
        }
    }

}