package com.base.hilt.ui.groupdetail.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentGroupDetailBinding
import com.base.hilt.ui.groupdetail.viewmodel.GroupDetailViewModel


class GroupDetailFragment : FragmentBase<GroupDetailViewModel, FragmentGroupDetailBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_group_detail

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isVisible = true, type = 0))
    }

    override fun initializeScreenVariables() {

        getDataBinding().layGroupDetail.viewmodel= viewModel
        getDataBinding().layComments.viewmodel= viewModel

        //observe data
        observeData()


    }

    private fun observeData() {
        viewModel.apply {
            onCommentClick?.observe(viewLifecycleOwner){
                getDataBinding().layComments.clComments.visibility = View.VISIBLE
                getDataBinding().layComments.etComments.setFocusable(View.FOCUSABLE)
            }

            onBackClick?.observe(viewLifecycleOwner){
                getDataBinding().layComments.clComments.visibility = View.GONE
            }
        }
    }

    override fun getViewModelClass(): Class<GroupDetailViewModel> = GroupDetailViewModel::class.java

}

