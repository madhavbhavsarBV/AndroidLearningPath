package com.base.hilt.ui.universe.ui

import android.widget.Toast
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentUniverseBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.universe.viewmodel.UniverseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UniverseFragment : FragmentBase<UniverseViewModel, FragmentUniverseBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_universe

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, type = 2, isVisible = true))
    }

    override fun initializeScreenVariables() {
        viewModel.apiCall()
        observeData()
    }

    private fun observeData() {
        
        viewModel.apiCallLiveData.observe(viewLifecycleOwner){
            viewModel.showProgressBar(it is ResponseHandler.Loading)
            when(it){
                is ResponseHandler.OnFailed -> {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
                is ResponseHandler.OnSuccessResponse -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }
            }
            
            
        }

    }

    override fun getViewModelClass(): Class<UniverseViewModel> =UniverseViewModel::class.java

}