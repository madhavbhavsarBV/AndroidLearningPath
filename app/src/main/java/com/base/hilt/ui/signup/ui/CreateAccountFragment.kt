package com.base.hilt.ui.signup.ui

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentCreateAccountBinding
import com.base.hilt.ui.signup.viewmodel.CreateAccountViewModel
import com.base.hilt.utils.Validation
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class CreateAccountFragment : FragmentBase<CreateAccountViewModel, FragmentCreateAccountBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_create_account

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = "Create Account",
                loginVisible = true
            )
        )
    }

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel = viewModel

        // date picker for date of birth
        setUpDatePicker()

        // text watcher mobile for brackets and dash
        setTextWatcherMobile()

        // set check box
        setTermsAndConditions()

        // observe data
        observeData()

        //login button click listener
        btnLoginClick()

        // set focus change listeners
        setFocusChangeListener()
    }

    private fun setFocusChangeListener() {

        context.apply {

            getDataBinding().etEmail.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
                if (!p1) {
                    when {
                        getDataBinding().etEmail.text.toString().trim().isEmpty() -> {
                            setError(getDataBinding().tilEmail, getString(R.string.val_email_empty))
                        }
                        !Validation.isEmailValid(
                            getDataBinding().etEmail.text.toString().trim()
                        ) -> {
                            setError(getDataBinding().tilEmail, getString(R.string.val_email))
                        }
                        else -> {
                            removeError(getDataBinding().tilEmail)
                        }
                    }
                }
            }



            getDataBinding().etConfirmPassword.onFocusChangeListener =
                View.OnFocusChangeListener { p0, p1 ->
                    if (!p1) {
                        when {
                            getDataBinding().etConfirmPassword.text.toString().trim().isEmpty() -> {
                                setError(
                                    getDataBinding().tilConfirmPass,
                                    getString(R.string.val_conf_password_empty)
                                )
                            }
                            !getDataBinding().etConfirmPassword.text.toString().trim()
                                .equals(getDataBinding().etPassword.text.toString().trim()) -> {
                                setError(
                                    getDataBinding().tilConfirmPass,
                                    getString(R.string.val_conf_password)
                                )
                            }
                            else -> {
                                removeError(getDataBinding().tilConfirmPass)
                            }
                        }
                    }
                }
            getDataBinding().etLastName.onFocusChangeListener =
                View.OnFocusChangeListener { p0, p1 ->
                    if (!p1) {
                        when {
                            getDataBinding().etLastName.text.toString().trim().isEmpty() -> {
                                setError(
                                    getDataBinding().tilLastName,
                                    getString(R.string.val_last_name)
                                )
                            }
                            getDataBinding().etLastName.text.toString().length < 2 -> {
                                setError(
                                    getDataBinding().tilLastName,
                                    getString(R.string.val_last_name_short)
                                )
                            }
                            else -> {
                                removeError(getDataBinding().tilLastName)
                            }
                        }
                    }
                }
            getDataBinding().etFirstName.onFocusChangeListener =
                View.OnFocusChangeListener { p0, p1 ->
                    if (!p1) {
                        when {
                            getDataBinding().etFirstName.text.toString().trim().isEmpty() -> {
                                setError(
                                    getDataBinding().tilFirtName,
                                    getString(R.string.val_first_name)
                                )

                            }
                            getDataBinding().etFirstName.text.toString().length < 2 -> {
                                setError(
                                    getDataBinding().tilFirtName,
                                    getString(R.string.val_first_name_short)
                                )
                            }
                            else -> {
                                removeError(getDataBinding().tilFirtName)
                            }
                        }
                    }
                }

            getDataBinding().etMobile.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
                if (!p1) {
                    when {
                        getDataBinding().etMobile.text.toString().trim().isEmpty() -> {
                            setError(
                                getDataBinding().tilMobile,
                                getString(R.string.val_mobile_empty)
                            )
                        }
                        Validation.validatePhone(getDataBinding().etMobile.text.toString()) -> {
                            setError(getDataBinding().tilMobile, getString(R.string.val_mobile))
                        }
                        else -> {
                            removeError(getDataBinding().tilMobile)
                        }
                    }
                }
            }
            getDataBinding().etPassword.onFocusChangeListener =
                View.OnFocusChangeListener { p0, p1 ->
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
                        }
                    }
                }


        }


    }

    private fun btnLoginClick() {
        (requireActivity() as MainActivity).binding.layToolbar.toolbarLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun updateButton() {
        getDataBinding().btnNext.isEnabled = checkValidations()
    }

    override fun getViewModelClass(): Class<CreateAccountViewModel> =
        CreateAccountViewModel::class.java

    private fun observeData() {

        viewModel.apply {
            onBtnNextClick?.observe(viewLifecycleOwner) {
                if (checkValidations()) {
                    findNavController().navigate(R.id.action_createAccountFragment_to_otpFragment)
                }
            }
        }


    }

    private fun setTermsAndConditions() {
        val terms = SpannableString(getString(R.string.terms))
        // terms
        terms.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
                }
            },
            terms.indexOf("terms conditions"),
            terms.indexOf("terms conditions") + 16,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        terms.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green)),
            terms.indexOf("terms conditions"), terms.indexOf("terms conditions") + 16,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // privacy policy
        terms.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
                }
            },
            terms.indexOf("privacy"),
            terms.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        terms.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green)),
            terms.indexOf("privacy"), terms.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        // set text
        getDataBinding().tvCheckBoxText.movementMethod = LinkMovementMethod.getInstance();
        getDataBinding().tvCheckBoxText.text = terms
    }

    private fun setTextWatcherMobile() {

        getDataBinding().etFirstName.doAfterTextChanged {
            updateButton()
        }
        getDataBinding().etLastName.doAfterTextChanged {
            updateButton()
        }
        getDataBinding().etMobile.doAfterTextChanged {
            updateButton()
        }
        getDataBinding().etPassword.doAfterTextChanged {
            updateButton()
        }
        getDataBinding().etConfirmPassword.doAfterTextChanged {
            updateButton()
        }
        getDataBinding().etEmail.doAfterTextChanged {
            updateButton()
        }
        getDataBinding().etDateOfBirth.doAfterTextChanged {
            updateButton()
        }

        getDataBinding().cbSignUp.setOnCheckedChangeListener { compoundButton, b ->
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

    private fun setUpDatePicker() {
        context.apply {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val cyear = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    getDataBinding().etDateOfBirth.setText("" + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year)
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

            getDataBinding().tilDateOfBirth.setOnClickListener {
                datePickerDialog.show()
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            }
            getDataBinding().etDateOfBirth.setOnClickListener {
                datePickerDialog.show()
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            }


        }
    }

    private fun checkValidations(): Boolean {

        var isValidForm = true

        when {
            getDataBinding().etFirstName.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }
            getDataBinding().etFirstName.text.toString().length < 2 -> {
                isValidForm = false
            }
            getDataBinding().etLastName.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }
            getDataBinding().etLastName.text.toString().length < 2 -> {
                isValidForm = false
            }
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
            getDataBinding().etConfirmPassword.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }
            !getDataBinding().etConfirmPassword.text.toString().trim()
                .equals(getDataBinding().etPassword.text.toString().trim()) -> {
                isValidForm = false
            }
            getDataBinding().etEmail.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }
            !Validation.isEmailValid(getDataBinding().etEmail.text.toString().trim()) -> {
                isValidForm = false
            }
            getDataBinding().etDateOfBirth.text.toString().trim().isEmpty() -> {
                isValidForm = false
            }
            !getDataBinding().cbSignUp.isChecked -> {
                isValidForm = false
            }
        }
        if (isValidForm) {
            removeError(getDataBinding().tilFirtName)
            removeError(getDataBinding().tilLastName)
            removeError(getDataBinding().tilEmail)
            removeError(getDataBinding().tilPassword)
            removeError(getDataBinding().tilConfirmPass)
            removeError(getDataBinding().tilDateOfBirth)
            removeError(getDataBinding().tilMobile)
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