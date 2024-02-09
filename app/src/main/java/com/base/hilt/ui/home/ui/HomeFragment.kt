package com.base.hilt.ui.home.ui

import android.util.Log
import android.widget.TextView
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentHomeBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.home.adapter.HomeViewPagerAdapter
import com.base.hilt.ui.home.viewmodel.HomeViewModel
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
//                    tab.getPosition(0).customView?.findViewById<TextView>(R.id.tvBadge).setText()
                }
                1->tab.text = getString(R.string.active_1)
                2->tab.text = getString(R.string.past_1)
            }
        }.attach()

        viewModel.userDataApiCall()
        viewModel.challengeListingCountApiCall()
        viewModel.unreadNotificationCountApiCall()

        observeData()

    }

    @Inject
    lateinit var pref:MyPreference
    private fun observeData() {
        Log.i("madhome", "observeData: ${pref.getValueString(PrefKey.TOKEN,"ff")}")
        viewModel.userDataLiveData.observe(viewLifecycleOwner){
            when(it){
                ResponseHandler.Loading -> {

                }
                is ResponseHandler.OnFailed -> {
                    Log.i("madhome", "ud observeData: ${it.message}")
                }
                is ResponseHandler.OnSuccessResponse ->{
                    Log.i("madhome", "observeData: ${it.response}")
                }
            }
        }
        viewModel.challengeListingCountLiveData.observe(viewLifecycleOwner){
            when(it){
                ResponseHandler.Loading -> {

                }
                is ResponseHandler.OnFailed -> {
                    Log.i("madhome", "clc observeData: ${it.message}")
                }
                is ResponseHandler.OnSuccessResponse ->{
                    Log.i("madhome", "observeData: ${it.response}")
                }
            }
        }
        viewModel.unreadNotificationCountLiveData.observe(viewLifecycleOwner){
            when(it){
                ResponseHandler.Loading -> {

                }
                is ResponseHandler.OnFailed -> {
                    Log.i("madhome", "unc observeData: ${it.message}")
                }
                is ResponseHandler.OnSuccessResponse ->{
                    Log.i("madhome", "observeData: ${it.response}")
                }
            }
        }


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