package com.base.hilt.ui.challenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentChallengeDetailBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel


class ChallengeDetailFragment : FragmentBase<ChallengeViewModel, FragmentChallengeDetailBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_challenge_detail

    override fun setupToolbar() {

    }

    override fun initializeScreenVariables() {

    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java

}