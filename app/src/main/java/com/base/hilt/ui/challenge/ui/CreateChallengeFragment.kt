package com.base.hilt.ui.challenge.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentCreateChallengeBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel


class CreateChallengeFragment :FragmentBase<ChallengeViewModel, FragmentCreateChallengeBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_create_challenge

    override fun setupToolbar() {

    }

    override fun initializeScreenVariables() {

    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java

}