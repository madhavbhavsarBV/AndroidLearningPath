package com.base.hilt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.base.hilt.R

abstract class DialogFragmentBase<V : ViewModelBase, DataBinding : ViewDataBinding> :
    DialogFragment() {

    private lateinit var mViewModel: V
    private lateinit var dataBinding: DataBinding
    val viewModel: V get() = mViewModel
    val binding: DataBinding
        get() = dataBinding

    fun getDataBinding() = dataBinding

    abstract fun layoutId(): Int

    abstract fun initializeScreenVariables()

    protected abstract fun viewModelClass(): Class<V>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.root
        dataBinding.executePendingBindings()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeScreenVariables()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog)
        mViewModel = ViewModelProvider(this)[viewModelClass()]


    }



}