package com.base.hilt.ui.challenge.ui

import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.DialogFragmentBase
import com.base.hilt.databinding.FragmentChallengeDialogBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeDialogViewModel

class ChallengeDialogFragment :
    DialogFragmentBase<ChallengeDialogViewModel, FragmentChallengeDialogBinding>() {
    override fun layoutId(): Int = R.layout.fragment_challenge_dialog

    override fun initializeScreenVariables() {
        getDataBinding().viewmodel = viewModel
        //
        observeData()

    }

    private fun observeData() {

        viewModel.apply {
            onBtnCreateChallengeClick?.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.challengeFragment)
                dismiss()
            }


            onCancelClick?.observe(viewLifecycleOwner) {
                dismiss()
            }

        }

    }

    override fun viewModelClass(): Class<ChallengeDialogViewModel> =
        ChallengeDialogViewModel::class.java

}
