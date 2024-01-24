package com.base.hilt.ui.challenge.ui

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentChallengeBinding
import com.base.hilt.ui.challenge.adapter.ChallengeViewPagerAdapter
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel


class ChallengeFragment : FragmentBase<ChallengeViewModel, FragmentChallengeBinding>(),
    BtnNextValidations {
    override fun getLayoutId(): Int = R.layout.fragment_challenge

    override fun setupToolbar() {

        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                type = 4,
                isBottomNavVisible = false
            )
        )
    }

    override fun initializeScreenVariables() {


        getDataBinding().viewmodel = viewModel

        // set up view pager
        setUpViewPagerAdapter()

        // set observers
        observerData()

        // set toolbar back button
        setUpBackButtonNavigation()
    }

    private fun setUpBackButtonNavigation() {
        (requireActivity() as MainActivity).binding.layToolbar.imgBackStep.setOnClickListener {
            when (getDataBinding().vpChallenges.currentItem) {
                0 -> {
                    findNavController().popBackStack()
                }
                1 -> getDataBinding().vpChallenges.setCurrentItem(0, true)
                2 -> getDataBinding().vpChallenges.setCurrentItem(1, true)
                3 -> getDataBinding().vpChallenges.setCurrentItem(2, true)
            }
        }
        (requireActivity() as MainActivity).binding.layToolbar.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java


    private fun setUpViewPagerAdapter() {

        val createChallengeFragment = CreateChallengeFragment()
        createChallengeFragment.setInterface(this)
//        var createDetailFragment = ChallengeDetailFragment().setInterface(this)
//        var createDescriptionFragment = ChallengeDescriptionFragment().setInterface(this)
//        var reviewChallengeFragment = ReviewChallengeFragment().setInterface(this)

        getDataBinding().vpChallenges.adapter =
            ChallengeViewPagerAdapter(
                requireActivity(),
                arrayListOf(
                    createChallengeFragment,
                    ChallengeDetailFragment(),
                    ChallengeDescriptionFragment(),
                    ReviewChallengeFragment()
                )
            )

        getDataBinding().vpChallenges.isUserInputEnabled = false

        getDataBinding().vpChallenges.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        getDataBinding().uHLine2.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey_2
                            )
                        )
                    }

                    1 -> {
                        getDataBinding().uHLine2.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green_4
                            )
                        )
                        getDataBinding().uHLine3.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey_2
                            )
                        )
                    }

                    2 -> {
                        getDataBinding().uHLine3.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green_4
                            )
                        )
                        getDataBinding().uHLine4.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey_2
                            )
                        )
                    }

                    3 -> {
                        getDataBinding().uHLine4.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green_4
                            )
                        )
                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

    }


    private fun observerData() {
        viewModel.apply {

            onBtnNextClick?.observe(viewLifecycleOwner) {

                when (getDataBinding().vpChallenges.currentItem) {
                    0 -> getDataBinding().vpChallenges.setCurrentItem(1, true)
                    1 -> getDataBinding().vpChallenges.setCurrentItem(2, true)
                    2 -> getDataBinding().vpChallenges.setCurrentItem(3, true)
                    3 -> {
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.challenge_created_3),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

            }


        }

    }

    override fun btnEnabled(selectedContacts: Int) {
        getDataBinding().btnNext.isEnabled = selectedContacts != 0
    }


}

interface BtnNextValidations {
    fun btnEnabled(selectedContacts: Int)
}