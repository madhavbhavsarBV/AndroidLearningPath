package com.base.hilt.ui.challenge.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentChallengeDescriptionBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDescriptionFragment : FragmentBase<ChallengeViewModel, FragmentChallengeDescriptionBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_challenge_description

    override fun setupToolbar() {
//        TODO("Not yet implemented")
    }

    override fun initializeScreenVariables() {
//        TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java

}