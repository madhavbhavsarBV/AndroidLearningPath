package com.base.hilt.ui.challenge.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentReviewChallengeBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewChallengeFragment : FragmentBase<ChallengeViewModel, FragmentReviewChallengeBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_review_challenge

    override fun setupToolbar() {
    }

    override fun initializeScreenVariables() {
//        TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java

}