package com.base.hilt.ui.otp.ui

import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo3.api.Optional
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentOtpBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.OtpInput
import com.base.hilt.type.ResendSmsOtpInput
import com.base.hilt.ui.otp.viewmodel.OtpViewModel
import com.base.hilt.utils.CommonDialogs
import com.base.hilt.utils.Extention.hideKeyboard
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpFragment : FragmentBase<OtpViewModel, FragmentOtpBinding>() {

    var cTimer: CountDownTimer? = null


    private val args: OtpFragmentArgs by navArgs()
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


        Log.i("madgg", "${args.sendData?.get(0)} ${args.sendData?.get(2)} ${args.sendData?.get(1)}")
        getDataBinding().clOtpVisibility = true
        // set Mobile No.
        getDataBinding().tvOtpPhoneNumber.text = args.sendData?.get(1)

        // start timer
        startTimer()

        // text watcher for otp
        setUpTextWatcher()

        // observe data
        observeData()

        // set spannable in resend
        setResendSpan()
    }

    private fun setResendSpan() {

        val resendOtp = SpannableString(getString(R.string.resend_password))

        resendOtp.setSpan(
           UnderlineSpan() ,
            0,
            resendOtp.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        getDataBinding().tvResendCode.text = resendOtp


    }

    private fun observeData() {

        getDataBinding().viewmodel = viewModel

        viewModel.onResendClick?.observe(viewLifecycleOwner) {
            viewModel.resendOtpApi(
                ResendSmsOtpInput(
                    type = Optional.Present(getString(R.string._one1)),
                    mobile_number =  Optional.Present(args.sendData?.get(1)),
                    email =  Optional.Present(args.sendData?.get(0))
                )
            )
        }
        viewModel.onBtnContinueClick?.observe(viewLifecycleOwner) {


            viewModel.otpVerifyApi(
                OtpInput(
                    otp = Optional.Present(getOtpFromUser()),
                    type = Optional.Present(getString(R.string._one1)),
                    uuid = Optional.Present(args.sendData?.get(4))
                )
            )
        }

        viewModel.resendOtpLiveData.observe(viewLifecycleOwner){
            when(it){
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }
                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    CommonDialogs.showOkDialog(requireContext(), it.message)
                }
                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                    getDataBinding().tvTimer.visibility = View.VISIBLE
                    getDataBinding().tvTimer.setText(getString(R.string._00_30s))
                    getDataBinding().tvResendCode.isEnabled = false
                    startTimer()

                }
            }
        }


        viewModel.otpVerificationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }

                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    CommonDialogs.showOkDialog(requireContext(), it.message)
                }

                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                    setUpContinueClick()
                }
            }
        }


        viewModel.onEditClick?.observe(viewLifecycleOwner){
            findNavController().popBackStack()
        }

    }

    private fun getOtpFromUser(): String {
        return getDataBinding().tvOtp1.text.toString().trim() +
                getDataBinding().tvOtp2.text.toString().trim() +
                getDataBinding().tvOtp3.text.toString().trim() +
                getDataBinding().tvOtp4.text.toString().trim() +
                getDataBinding().tvOtp5.text.toString().trim() +
                getDataBinding().tvOtp6.text.toString().trim()
    }

    private fun setUpTextWatcher() {
        getDataBinding().tvOtp1.doAfterTextChanged {
            if (getDataBinding().tvOtp1.text.toString().trim().length == 1) {
                getDataBinding().tvOtp2.requestFocus()

            } else {
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }
        getDataBinding().tvOtp2.doAfterTextChanged {
            if (getDataBinding().tvOtp2.text.toString().trim().length == 1) {
                getDataBinding().tvOtp3.requestFocus()

            } else if (getDataBinding().tvOtp2.text.toString().trim().isEmpty()) {
                getDataBinding().tvOtp1.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            } else {
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }
        getDataBinding().tvOtp3.doAfterTextChanged {
            if (getDataBinding().tvOtp3.text.toString().trim().length == 1) {
                getDataBinding().tvOtp4.requestFocus()

            } else if (getDataBinding().tvOtp3.text.toString().trim().isEmpty()) {
                getDataBinding().tvOtp2.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            } else {
                getDataBinding().btnContinue.isEnabled = false
            }

            checkAllText()
        }
        getDataBinding().tvOtp4.doAfterTextChanged {
            if (getDataBinding().tvOtp4.text.toString().trim().length == 1) {
                getDataBinding().tvOtp5.requestFocus()
            } else if (getDataBinding().tvOtp4.text.toString().trim().isEmpty()) {
                getDataBinding().tvOtp3.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            } else {
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }

        getDataBinding().tvOtp5.doAfterTextChanged {
            if (getDataBinding().tvOtp5.text.toString().trim().length == 1) {
                getDataBinding().tvOtp6.requestFocus()
            } else if (getDataBinding().tvOtp5.text.toString().trim().isEmpty()) {
                getDataBinding().tvOtp4.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            } else {
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }


        getDataBinding().tvOtp6.doAfterTextChanged {
            if (getDataBinding().tvOtp6.text.toString().trim().length == 1) {

            } else if (getDataBinding().tvOtp6.text.toString().trim().isEmpty()) {
                getDataBinding().tvOtp5.requestFocus()
                getDataBinding().btnContinue.isEnabled = false
            } else {
                getDataBinding().btnContinue.isEnabled = false
            }
            checkAllText()
        }


    }

    override fun getViewModelClass(): Class<OtpViewModel> = OtpViewModel::class.java

    private fun setUpContinueClick() {

        Log.i("madsgg", "setUpContinueClick: ")

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

    private fun checkAllText() {
        getDataBinding().apply {
            if (tvOtp1.text.toString().trim().length == 1 &&
                tvOtp2.text.toString().trim().length == 1 &&
                tvOtp3.text.toString().trim().length == 1 &&
                tvOtp4.text.toString().trim().length == 1 &&
                tvOtp5.text.toString().trim().length == 1 &&
                tvOtp6.text.toString().trim().length == 1
            ) {
                btnContinue.isEnabled = true
            }


        }


    }

    //cancel timer
    //start timer function
    private fun startTimer() {
        cTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                getDataBinding().tvTimer.setText("00:${millisUntilFinished / 1000}s");
            }
            override fun onFinish() {
                getDataBinding().tvTimer.visibility = View.GONE
                getDataBinding().tvResendCode.isEnabled = true
            }
        }
        (cTimer as CountDownTimer).start()
    }
    private fun cancelTimer() {
        cTimer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelTimer()
    }
}