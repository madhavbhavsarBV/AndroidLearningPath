package com.base.hilt.ui.challenge.ui

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentChallengeBinding
import com.base.hilt.ui.challenge.adapter.ChallengeViewPagerAdapter
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel


class ChallengeFragment : FragmentBase<ChallengeViewModel, FragmentChallengeBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_challenge

    override fun setupToolbar() {

        viewModel.setToolbarItems(ToolbarModel(isVisible = true, isBottomNavVisible = false))
    }

    override fun initializeScreenVariables() {


        getDataBinding().viewmodel = viewModel
        setUpViewPagerAdapter()
        observerData()
    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java


    private fun setUpViewPagerAdapter() {

        getDataBinding().vpChallenges.adapter =
            ChallengeViewPagerAdapter(
                requireActivity(),
                arrayListOf(
                    CreateChallengeFragment(),
                    ChallengeDetailFragment(),
                    ChallengeDescriptionFragment(),
                    ReviewChallengeFragment()
                )
            )


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


}