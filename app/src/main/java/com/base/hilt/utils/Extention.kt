package com.base.hilt.utils

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.base.hilt.R
import com.google.android.material.textfield.TextInputLayout

object Extention {

    fun View.hideKeyboard() {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
    }

    fun TextInputLayout.removeError(){
        this.isErrorEnabled = false
    }
    fun TextInputLayout.setError(s: String){
        this.error = s
        this.isErrorEnabled = true
        this.setErrorIconDrawable(0)
        this.boxStrokeErrorColor = ColorStateList.valueOf(resources.getColor(R.color.black))
    }
}