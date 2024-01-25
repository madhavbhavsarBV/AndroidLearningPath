package com.base.hilt.ui.groupdetail.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.base.hilt.R
import com.base.hilt.base.BaseBottomSheetDialogFragment
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentParticipantsListBinding
import com.base.hilt.ui.groupdetail.viewmodel.ParticipantsListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class ParticipantsListFragment : BaseBottomSheetDialogFragment<ParticipantsListViewModel,FragmentParticipantsListBinding>() {
    override fun viewModelClass(): Class<ParticipantsListViewModel> = ParticipantsListViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_participants_list

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel = viewModel

        // observe data
        observeData()
    }

    private fun observeData(){
        viewModel.apply {
            onBackClick?.observe(viewLifecycleOwner){
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }

            getBehaviour(dialog)?.isDraggable = false
        }
        return dialog
    }
    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun getBehaviour(bottomSheetDialog: BottomSheetDialog): BottomSheetBehavior<View>? {

        val bottomSheet: FrameLayout? = bottomSheetDialog.findViewById(R.id.design_bottom_sheet)
        return if (bottomSheet != null) BottomSheetBehavior.from(bottomSheet) else null

    }

}