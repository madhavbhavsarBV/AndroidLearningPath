package com.base.hilt.ui.challenge.ui


import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentCreateChallengeBinding
import com.base.hilt.ui.challenge.adapter.ContactsRecyclerAdapter
import com.base.hilt.ui.challenge.interfaces.BtnNextValidations
import com.base.hilt.ui.challenge.model.ContactsModel
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChallengeFragment :FragmentBase<ChallengeViewModel, FragmentCreateChallengeBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_create_challenge

    lateinit var btnNextEnabled : BtnNextValidations

    var contactSelected:Int =0

    override fun setupToolbar() {

    }

    override fun initializeScreenVariables() {
        // set up contacts recycler view
        setUpContactsRecyclerView()
    }

    private fun setUpContactsRecyclerView() {
        val adapter = ContactsRecyclerAdapter(requireContext(),
            arrayListOf(ContactsModel(),ContactsModel(),ContactsModel(),ContactsModel()),
            onCheckClick = {b->
                if (b) contactSelected++
                else contactSelected--

                btnNextEnabled.btnEnabled(contactSelected)
            }
        )

        getDataBinding().rcvContacts.adapter = adapter
        getDataBinding().rcvContacts.layoutManager=LinearLayoutManager(requireContext())

    }

    fun setInterface(i: BtnNextValidations){
        btnNextEnabled = i
    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java

}