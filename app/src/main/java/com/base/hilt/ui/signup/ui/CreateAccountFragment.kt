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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.api.Optional
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentCreateAccountBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.SignUpInput
import com.base.hilt.ui.signup.viewmodel.CreateAccountViewModel
import com.base.hilt.utils.CommonDialogs
import com.base.hilt.utils.Validation
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class CreateAccountFragment : FragmentBase<CreateAccountViewModel, FragmentCreateAccountBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_create_account

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = getString(R.string.create_account1),
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


        viewModel.onBtnNextClick?.observe(viewLifecycleOwner) {
            if (checkValidations()) {
                viewModel.signUpApi(
                    SignUpInput(
                        first_name = Optional.Present(
                            getDataBinding().etFirstName.text.toString().trim()
                        ),
                        last_name = Optional.Present(
                            getDataBinding().etLastName.text.toString().trim()
                        ),
                        mobile_number = Optional.Present(
                            getString(R.string.plaus1) + getDataBinding().etMobile.text.toString()
                                .trim().filter { it.isDigit() }),
                        alias = Optional.Present(
                            getDataBinding().etAlias.text.toString().trim()
                        ),
                        password = Optional.Present(
                            getDataBinding().etPassword.text.toString().trim()
                        ),
                        confirm_password = Optional.Present(
                            getDataBinding().etConfirmPassword.text.toString().trim()
                        ),
                        email = Optional.Present(
                            getDataBinding().etEmail.text.toString().trim()
                        ),
                        dob = Optional.Present(
                            dobString
                        ),
                        referral_code = Optional.Present(
                            getDataBinding().etReferralCode.text.toString().trim()
                        ),
                        device_id = Optional.Present(""),
                        device_type = Optional.Present(getString(R.string._one1)),
                        ip_address = Optional.Present(getString(R.string._192_168_1_45)),
                        user_timezone = Optional.Present(getString(R.string.asia_culcutta))

                    )
                )
            }
        }

        viewModel.signUpLiveData.observe(viewLifecycleOwner) {
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
                    // uuid mobile otp
                    if (it.response.data!=null){
                        val email = it.response.data?.signup?.data?.email
                        val mobile =it.response.data?.signup?.data?.mobile_number
                        val otp = it.response.data?.signup?.data?.otp
                        val type = getString(R.string._one1)
                        val uuid = it.response.data?.signup?.data?.uuid

                        val otpData= arrayOf(email,mobile,otp,type,uuid)

                        val action = CreateAccountFragmentDirections.actionCreateAccountFragmentToOtpFragment(otpData)
                        findNavController().navigate(action)
                    }

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
            terms.indexOf(getString(R.string.terms_conditions)),
            terms.indexOf(getString(R.string.terms_conditions)) + 16,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        terms.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green)),
            terms.indexOf(getString(R.string.terms_conditions)),
            terms.indexOf(getString(R.string.terms_conditions)) + 16,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // privacy policy
        terms.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
                }
            },
            terms.indexOf(getString(R.string.privacy)),
            terms.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        terms.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green)),
            terms.indexOf(getString(R.string.privacy)), terms.length,
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

    var dobString = ""

    private fun setUpDatePicker() {
        context.apply {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val cyear = year
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal[Calendar.MONTH] = monthOfYear
                    cal[Calendar.DAY_OF_MONTH] = dayOfMonth
                    cal[Calendar.YEAR] = year
                    val myDate: Date = cal.time
                    val str = SimpleDateFormat("MMM dd yyyy").format(myDate)
                    getDataBinding().etDateOfBirth.setText(str)

                    dobString = SimpleDateFormat("MM-dd-yyyy").format(myDate)

                    if (cyear - year < 18) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.you_must_be_18_years_old_or_above),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

            getDataBinding().tilDateOfBirth.setOnClickListener {
                datePickerDialog.show()
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(Color.BLACK)
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(Color.BLACK)
            }
            getDataBinding().etDateOfBirth.setOnClickListener {
                datePickerDialog.show()
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(Color.BLACK)
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(Color.BLACK)
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