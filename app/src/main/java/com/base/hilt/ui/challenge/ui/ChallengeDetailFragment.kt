package com.base.hilt.ui.challenge.ui

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentChallengeDetailBinding
import com.base.hilt.ui.challenge.viewmodel.ChallengeDetailViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class ChallengeDetailFragment : FragmentBase<ChallengeDetailViewModel, FragmentChallengeDetailBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_challenge_detail

    override fun setupToolbar() {

    }

    override fun initializeScreenVariables() {
        getDataBinding().viewmodel = viewModel

        //set AcceptedBy and EndBy Date
        setUpDate()

        // observe data
        observeData()

    }

    private fun setUpDate() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        getDataBinding().etAcceptedBy.setText(dateFormat.format(Date()))
        getDataBinding().etEndBy.setText(dateFormat.format(Date()))
    }

    private fun observeData() {
        viewModel.apply {

            acceptedByClick?.observe(viewLifecycleOwner){
                setUpDatePickerListener(getDataBinding().etAcceptedBy,"1")
            }

            endByClick?.observe(viewLifecycleOwner){
                setUpDatePickerListener(getDataBinding().etEndBy, "2")
            }
        }

    }

    private fun setUpDatePickerListener(et: TextInputEditText,s:String) {



        context.apply {
            val c = et.text.toString().split(Regex("/"))
            val year = c[2].toInt()
            val month = c[1].toInt()
            val day = c[0].toInt()

            Toast.makeText(requireContext(), "$year ${month} ${day}", Toast.LENGTH_SHORT).show()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    et.setText("" +dayOfMonth + "/" + (monthOfYear + 1)  + "/" + year)
                },
                year,
                month,
                day
            )
            datePickerDialog.updateDate(year,month-1,day)

//            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)


        }
    }

    override fun getViewModelClass(): Class<ChallengeDetailViewModel> = ChallengeDetailViewModel::class.java

    fun checkValidations(): Boolean {
        var isValidForm = true
        getDataBinding().apply {

            tilNameChallenge.isErrorEnabled = false
            tilAmount.isErrorEnabled = false

            if (etNameChallenge.text.toString().isEmpty()) {
                tilNameChallenge.error = resources.getString(R.string.val_chall_name)
                isValidForm = false
            } else if (etAmount.text.toString().isEmpty()) {
                tilAmount.error = resources.getString(R.string.val_chall_amt)
                isValidForm = false
            }
        }

        if (isValidForm) {
            removeError(getDataBinding().tilNameChallenge)
            removeError(getDataBinding().tilAmount)
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