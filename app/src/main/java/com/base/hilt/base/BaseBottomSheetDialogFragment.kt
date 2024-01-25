package com.base.hilt.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.base.hilt.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<V : ViewModelBase, DataBinding : ViewDataBinding> :
    BottomSheetDialogFragment() {

    private lateinit var mViewModel: V
    val viewModel: V get() = mViewModel
    private lateinit var dataBinding: DataBinding
    private lateinit var behavior: BottomSheetBehavior<View>
    val binding: DataBinding
        get() = dataBinding

    protected abstract fun viewModelClass(): Class<V>

    fun getDataBinding() = dataBinding

    abstract fun layoutId(): Int

    abstract fun initializeScreenVariables()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog)
        mViewModel = ViewModelProvider(this)[viewModelClass()]
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeScreenVariables()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val sheet = d.findViewById<View>(R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(sheet!!)

            behavior.isGestureInsetBottomIgnored = false
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            sheet.setBackgroundResource(android.R.color.transparent)
        }

        return dialog
    }

}