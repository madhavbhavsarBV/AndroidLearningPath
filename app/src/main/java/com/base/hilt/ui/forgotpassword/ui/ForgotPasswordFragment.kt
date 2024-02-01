package com.base.hilt.ui.forgotpassword.ui

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.api.Optional
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentForgotPasswordBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ForgotPasswordInput
import com.base.hilt.ui.forgotpassword.viewmodel.ForgotPasswordViewModel
import com.base.hilt.utils.CommonDialogs
import com.base.hilt.utils.Extention.removeError
import com.base.hilt.utils.PrefKey
import com.base.hilt.utils.Validation
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment :
    FragmentBase<ForgotPasswordViewModel, FragmentForgotPasswordBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_forgot_password

    override fun setupToolbar() {

        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = getString(R.string.forgot_password2),
                type = 1,
                backBtnVisible = true,
                loginVisible = false
            )
        )

    }

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel = viewModel

        setTextWatcherMobile()

        setFocusChangeListener()

        observeData()


    }

    override fun getViewModelClass(): Class<ForgotPasswordViewModel> =
        ForgotPasswordViewModel::class.java

    private fun observeData() {


        viewModel.btnSendOtpClick?.observe(viewLifecycleOwner) {
            if (checkValidations()) {
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpFragment)

                Log.i(
                    "madmad",
                    "observeData: pn ${
                        getDataBinding().etMobile.text.toString().trim().filter { it.isDigit() }
                    }"
                )
                viewModel.forgotPasswordApi(
                    ForgotPasswordInput(
                        Optional.Present(
                            getString(R.string.plaus1).plus(
                                getDataBinding().etMobile.text.toString().trim()
                                    .filter { it.isDigit() })
                        ),
                        type = Optional.Present("2")
                    )
                )


            }

        }

        viewModel.forgotPasswordLiveData.observe(this) {
            Log.i("madmad", "observeData: fp vm")
            when (it) {
                ResponseHandler.Loading -> {
                    Log.i("madmad", "observeData:fp loading")
                    viewModel.showProgressBar(true)
                }

                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    Log.i("madmad", "observeData:fp failed ${it.message}")
                    CommonDialogs.showOkDialog(requireContext(), it.message)
                }

                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpFragment)
                    Log.i("madmad", "observeData:fp success ${it.response?.data}")
                }
            }
        }


    }


    private fun updateButton() {
        getDataBinding().btnSendCode.isEnabled = checkValidations()
    }


    private fun setFocusChangeListener() {

        context.apply {

            getDataBinding().etMobile.doAfterTextChanged {
                updateButton()
            }

            getDataBinding().etMobile.onFocusChangeListener = object : View.OnFocusChangeListener {
                override fun onFocusChange(p0: View?, p1: Boolean) {
                    if (!p1) {
                        if (getDataBinding().etMobile.text.toString().trim().isEmpty()) {
                            setError(
                                getDataBinding().tilMobile,
                                getString(R.string.val_mobile_empty)
                            )
                        } else if (Validation.validatePhone(getDataBinding().etMobile.text.toString())) {
                            setError(getDataBinding().tilMobile, getString(R.string.val_mobile))
                        } else {
                            removeError(getDataBinding().tilMobile)
                        }
                    }
                }
            }


        }


    }


    private fun checkValidations(): Boolean {

        var isValidForm = true

        when {
            getDataBinding().etMobile.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }

            Validation.validatePhone(getDataBinding().etMobile.text.toString().trim()) -> {
                isValidForm = false
            }
        }

        if (isValidForm) {
            getDataBinding().tilMobile.removeError()
        }

        return isValidForm
    }


    private fun setTextWatcherMobile() {
        context.apply {

            getDataBinding().etMobile.addTextChangedListener(object : TextWatcher {
                private var isRunning: Boolean = false
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (isRunning) {
                        return
                    }

                    var cursorPosition = p1 + p3
                    val digits = s?.filter(Char::isDigit)

                        ?.take(11)
                    cursorPosition -= s?.take(cursorPosition)?.run {
                        count { !it.isDigit() } + filter(Char::isDigit).takeWhile { it == '0' }
                            .count()
                    } ?: 0

                    val output = StringBuilder(digits)

                    fun punctuate(position: Int, punctuation: String) {
                        if (digits?.length!! > position) {
                            output.insert(position, punctuation)
                            if (cursorPosition > position) {
                                cursorPosition += punctuation.length
                            }
                        }
                    }


                    punctuate(6, "-")
                    punctuate(3, ") ")
                    punctuate(0, " (")

                    isRunning = true
                    getDataBinding().etMobile.setText(output)
                    getDataBinding().etMobile.setSelection(cursorPosition.coerceAtMost(output.length))
                    isRunning = false
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }
    }


    private fun setError(view: TextInputLayout, s: String) {
        view.error = s
        view.isErrorEnabled = true
        view.setErrorIconDrawable(0)
        view.boxStrokeErrorColor = ColorStateList.valueOf(resources.getColor(R.color.black))
    }

    private fun removeError(view: TextInputLayout) {
        view.isErrorEnabled = false
    }

}