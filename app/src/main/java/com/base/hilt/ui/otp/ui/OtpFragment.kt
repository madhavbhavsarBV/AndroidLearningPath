package com.base.hilt.ui.otp.ui

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentOtpBinding
import com.base.hilt.utils.Extention.hideKeyboard
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class OtpFragment : FragmentBase<ViewModelBase, FragmentOtpBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_otp

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = "Verification Code",
                type = 1,
                backBtnVisible = true,
                loginVisible = false
            )
        )


    }

    override fun initializeScreenVariables() {

        getDataBinding().clOtpVisibility = true

        // btn continue click
        setUpContinueClick()

        // text watcher for otp
        setUpTextWatcher()

    }

    private fun setUpTextWatcher() {
        getDataBinding().tvOtp1.doAfterTextChanged {
            if (getDataBinding().tvOtp1.text.toString().trim().length == 1) {
                getDataBinding().tvOtp2.requestFocus()

            }else{
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }
        getDataBinding().tvOtp2.doAfterTextChanged {
            if (getDataBinding().tvOtp2.text.toString().trim().length == 1) {
                getDataBinding().tvOtp3.requestFocus()

            }else if(getDataBinding().tvOtp2.text.toString().trim().isEmpty()){
                getDataBinding().tvOtp1.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            }else{
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }
        getDataBinding().tvOtp3.doAfterTextChanged {
            if (getDataBinding().tvOtp3.text.toString().trim().length == 1) {
                getDataBinding().tvOtp4.requestFocus()

            }else if(getDataBinding().tvOtp3.text.toString().trim().isEmpty()){
                getDataBinding().tvOtp2.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            }else{
                getDataBinding().btnContinue.isEnabled = false
            }

            checkAllText()
        }
        getDataBinding().tvOtp4.doAfterTextChanged {
            if (getDataBinding().tvOtp4.text.toString().trim().length == 1) {

            }else if(getDataBinding().tvOtp4.text.toString().trim().isEmpty()){
                getDataBinding().tvOtp3.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            }else{
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }



    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java

    private fun setUpContinueClick() {
        getDataBinding().btnContinue.setOnClickListener {

//            viewModel.hideKeyboard()
            viewModel.setToolbarItems(
                ToolbarModel()
            )
            getDataBinding().root.hideKeyboard()


            getDataBinding().clOtpVisibility = false
//            getDataBinding().clCongratulations.visibility = View.VISIBLE
            Glide.with(requireContext()).load(R.drawable.splash_anim)
                .into(DrawableImageViewTarget(getDataBinding().imgCongratulations));

            lifecycleScope.launch {
                delay(3000)
                findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
            }


        }


    }

    private fun checkAllText(){
        getDataBinding().apply {
            if (tvOtp1.text.toString().trim().length==1 &&
                tvOtp2.text.toString().trim().length==1 &&
                tvOtp3.text.toString().trim().length==1 &&
                tvOtp4.text.toString().trim().length==1){
                btnContinue.isEnabled = true
            }


        }


    }
}