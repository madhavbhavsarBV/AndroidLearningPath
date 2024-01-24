package com.base.hilt.ui.challenge.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.DialogFragmentBase
import com.base.hilt.databinding.DialogChallengeBinding
import com.base.hilt.databinding.FragmentChallengeDialogBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
