package com.base.hilt.ui.onboarding.ui

import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentOnBoardingBinding
import com.base.hilt.ui.onboarding.adapter.OnBoardingViewPagerAdapter
import com.base.hilt.ui.onboarding.viewmodel.OnBoardingViewModel
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment : FragmentBase<OnBoardingViewModel, FragmentOnBoardingBinding>() {

    @Inject
    lateinit var pref:MyPreference

    override fun getLayoutId(): Int = R.layout.fragment_on_boarding

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel())
    }

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel=  viewModel
        setUpViewPager()
        observerData()

    }

    override fun getViewModelClass(): Class<OnBoardingViewModel> = OnBoardingViewModel::class.java

    private fun observerData(){

        viewModel.apply {

            onBtnNextClick?.observe(viewLifecycleOwner){

                when (getDataBinding().vpOnBoarding.currentItem) {
                    0 -> getDataBinding().vpOnBoarding.setCurrentItem(1, true)
                    1 -> {
                        findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
                        pref.setValueBoolean(PrefKey.GETTING_STARTED, true)
                    }
                }

            }




        }



    }

    private fun setUpViewPager(){
        getDataBinding().vpOnBoarding.adapter = OnBoardingViewPagerAdapter()

        getDataBinding().vpOnBoarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> getDataBinding().btnNext.text = getString(R.string.next)
                    1 -> getDataBinding().btnNext.text = getString(R.string.getting_started)
                }
            }
        })
    }

}