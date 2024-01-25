package com.base.hilt.ui.forgotpassword.ui

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentForgotPasswordBinding
import com.base.hilt.ui.forgotpassword.viewmodel.ForgotPasswordViewModel
import com.base.hilt.utils.Extention.removeError
import com.base.hilt.utils.Validation
import com.google.android.material.textfield.TextInputLayout


class ForgotPasswordFragment :
    FragmentBase<ForgotPasswordViewModel, FragmentForgotPasswordBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_forgot_password

    override fun setupToolbar() {

        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = "Forgot Password",
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

        viewModel.apply {


            btnSendOtpClick?.observe(viewLifecycleOwner) {
                if (checkValidations()) {

                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpFragment)
                }

            }


        }


    }


    fun updateButton() {
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
                        ?.dropWhile { it == '0' }
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