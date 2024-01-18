package com.base.hilt.ui.challenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentReviewChallengeBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel


class ReviewChallengeFragment : FragmentBase<ChallengeViewModel, FragmentReviewChallengeBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_review_challenge

    override fun setupToolbar() {
    }

    override fun initializeScreenVariables() {
//        TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java

}