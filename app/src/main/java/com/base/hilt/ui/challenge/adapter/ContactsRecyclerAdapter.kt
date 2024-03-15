package com.base.hilt.ui.challenge.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.base.hilt.R
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.RowContactsBinding
import com.base.hilt.ui.challenge.model.ContactsModel

class ContactsRecyclerAdapter(val mContext: Context, data:ArrayList<ContactsModel?>,
   var onCheckClick:(b:Boolean)-> Unit
):GenericRecyclerViewAdapter<ContactsModel,RowContactsBinding>(mContext,data) {

    override val layoutResId: Int = R.layout.row_contacts
    override fun getLayoutRes(model: ContactsModel): Int {
        return layoutResId
    }

    override fun onBindData(model: ContactsModel, position: Int, dataBinding: RowContactsBinding) {

        dataBinding.cbInvite.setOnCheckedChangeListener { compoundButton, b ->
            onCheckClick.invoke(b)
            if (b){
                dataBinding.cbInvite.setTextColor(ContextCompat.getColor(mContext,R.color.black))
                dataBinding.cbInvite.setText(R.string.invited)
            }else{
                dataBinding.cbInvite.setTextColor(ContextCompat.getColor(mContext,R.color.white))
                dataBinding.cbInvite.setText(R.string.invite)
            }

        }

    }

    override fun onItemClick(model: ContactsModel, position: Int, dataBinding: RowContactsBinding) {

    }
}