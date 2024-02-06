package com.base.hilt.ui.login.ui

import android.content.res.ColorStateList
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.api.CustomTypeValue.GraphQLNull.value
import com.apollographql.apollo3.api.Optional
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentLoginBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.LoginInput
import com.base.hilt.ui.login.viewmodel.LoginViewModel
import com.base.hilt.utils.CommonDialogs
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import com.base.hilt.utils.Validation
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : FragmentBase<LoginViewModel, FragmentLoginBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_login

    @Inject
    lateinit var pref: MyPreference

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isVisible = false, isBottomNavVisible = false))
    }

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel = viewModel

        // set spannable signup and btn navigation
       // setSignUpNavigation()

        // text watcher for mobile number style +1 (111) 111-1111
        setTextWatcherMobile()

        // set focus change listener
        setFocusChangeListener()

        // observe live data
        observeData()


    }

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    private fun observeData() {


        viewModel.onBtnSignUpClick?.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }

        viewModel.onBtnLoginClick?.observe(viewLifecycleOwner) {
            if (checkValidations()) {

//                Log.i("madmad", "observeData: on ${ }")

                viewModel.loginApi(LoginInput(
                    phone = getString(R.string.plaus1).plus(
                        getDataBinding().etMobile.text.toString().trim()
                            .filter { it.isDigit() }),
                    password = getDataBinding().etPassword.text.toString().trim(),
                    device_id = Optional.Present(""),
                    device_type =Optional.Present(""),
                    ip_address =Optional.Present(""),
                    user_timezone = Optional.Present(""),

                ))

            } else {
                getDataBinding().btnLogin.isEnabled = false
            }




        }

        viewModel.onBtnForgotPasswordClick?.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        viewModel.loginLiveData.observe(this) {
            when (it) {
                ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }

                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    CommonDialogs.showOkDialog(requireContext(),it.message)
                }

                is ResponseHandler.OnSuccessResponse -> {
                    viewModel.showProgressBar(false)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    pref.setValueBoolean(PrefKey.IS_LOGINED, true)
                    pref.setValueString(PrefKey.TOKEN, it.response.data?.login?.data?.access_token)
                }
            }
        }
    }


    private fun updateButton() {
        getDataBinding().btnLogin.isEnabled = checkValidations()
    }


    private fun setSignUpNavigation() {
        val signup = SpannableString(getString(R.string.lbl_sign_up))

        signup.setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {

            }

        }, 0, signup.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        getDataBinding().tvSignUp.movementMethod = LinkMovementMethod.getInstance()


        getDataBinding().tvSignUp.text = signup


    }


    private fun setFocusChangeListener() {

        context.apply {

            getDataBinding().etMobile.onFocusChangeListener = OnFocusChangeListener { p0, p1 ->
                if (!p1) {
                    if (getDataBinding().etMobile.text.toString().trim().isEmpty()) {
                        setError(getDataBinding().tilMobile, getString(R.string.val_mobile_empty))
                    } else if (Validation.validatePhone(getDataBinding().etMobile.text.toString())) {
                        setError(getDataBinding().tilMobile, getString(R.string.val_mobile))
                    } else {
                        removeError(getDataBinding().tilMobile)
                        updateButton()
                    }
                }
            }
            getDataBinding().etPassword.onFocusChangeListener =
                OnFocusChangeListener { p0, p1 ->
                    if (!p1) {
                        if (getDataBinding().etPassword.text.toString().trim().isEmpty()) {
                            setError(
                                getDataBinding().tilPassword,
                                getString(R.string.val_password_empty)
                            )
                        } else if (!Validation.isValidPassword(getDataBinding().etPassword.text.toString())) {
                            setError(getDataBinding().tilPassword, getString(R.string.val_password))
                        } else {
                            removeError(getDataBinding().tilPassword)
                            updateButton()
                        }
                    }
                }


        }


    }

    private fun setTextWatcherMobile() {
        context.apply {

            getDataBinding().etPassword.doAfterTextChanged {
                updateButton()
            }
            getDataBinding().etMobile.doAfterTextChanged {
                updateButton()
            }

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


    private fun checkValidations(): Boolean {
        var isValidForm = true
        when {
            getDataBinding().etMobile.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }

            Validation.validatePhone(getDataBinding().etMobile.text.toString().trim()) -> {
                isValidForm = false

            }

            getDataBinding().etPassword.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }

            !Validation.isValidPassword(getDataBinding().etPassword.text.toString()) -> {
                isValidForm = false
            }
        }

        if (isValidForm) {
            removeError(getDataBinding().tilMobile)
            removeError(getDataBinding().tilPassword)
        }

        return isValidForm
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