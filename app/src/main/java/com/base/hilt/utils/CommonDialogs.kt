package com.base.hilt.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.base.hilt.R
import com.base.hilt.databinding.DialogErrorOkBinding

object CommonDialogs {


    fun showOkDialog(
        mContext: Context,
        message: String?
    ) {
        val dialog = Dialog(mContext, R.style.CustomDialog)
        val binding = DialogErrorOkBinding.inflate(LayoutInflater.from(mContext))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        binding.lblError.text = message
        binding.btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}