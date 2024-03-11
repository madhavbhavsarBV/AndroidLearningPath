package com.base.hilt.ui.account.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentAccountBinding
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.account.adapter.AccountRecyclerViewAdapter
import com.base.hilt.ui.account.model.AccountModel
import com.base.hilt.ui.account.viewmodel.AccountViewModel
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : FragmentBase<AccountViewModel, FragmentAccountBinding>() {

    @Inject
    lateinit var pref: MyPreference

    override fun getLayoutId(): Int = R.layout.fragment_account

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, isVisible = false))
    }

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel = viewModel

        viewModel.onUserDataApiCall()

        // observe live data
        observeData()

        //status bar color change
        (requireActivity() as MainActivity).backGroundColor()

        // set up recycler adapter
        setUpRecyclerAdapter()

    }

    private fun setUpRecyclerAdapter() {

        val list: ArrayList<AccountModel?> = arrayListOf(
            AccountModel(getString(R.string.add_balance)),
            AccountModel(getString(R.string.invite_friend)),
            AccountModel(getString(R.string.settings)),
            AccountModel(getString(R.string.transaction_history)),
            AccountModel(getString(R.string.support)),
            AccountModel(getString(R.string.faqs)),
            AccountModel(getString(R.string.transfer_friend)),
            AccountModel(getString(R.string.scan_code)),
            AccountModel(getString(R.string.withdrow_balance)),
            AccountModel(getString(R.string.blocked_users)),
            AccountModel(getString(R.string.tandcs)),
            AccountModel(getString(R.string.privacy_policy)),
        )
        getDataBinding().rcvAccount.adapter =
            AccountRecyclerViewAdapter(requireContext(), list, onClick = {
                if (it == getString(R.string.settings)) {
                    findNavController().navigate(R.id.settingsFragment)
                }
            })
        getDataBinding().rcvAccount.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun observeData() {

        viewModel.apply {

            onLogOutClick?.observe(viewLifecycleOwner) {
                logoutApi()
            }


            onLogOutLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    ResponseHandler.Loading -> {
                        viewModel.showProgressBar(true)
                    }

                    is ResponseHandler.OnFailed -> {
                        viewModel.showProgressBar(false)
                    }

                    is ResponseHandler.OnSuccessResponse -> {
                        viewModel.showProgressBar(false)
                        findNavController().navigate(R.id.action_accountsFragment_to_loginFragment)
                        pref.setBeanValue(PrefKey.IS_LOGINED, false)
                    }
                }
            }

            userDataLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    ResponseHandler.Loading -> {
                        viewModel.showProgressBar(true)
                    }

                    is ResponseHandler.OnFailed -> {
                        viewModel.showProgressBar(true)
                    }

                    is ResponseHandler.OnSuccessResponse -> {
                        viewModel.showProgressBar(false)
                    }
                }
            }

        }

    }

    override fun onDestroyView() {

        //setStatus bar color black
        (requireActivity() as MainActivity).backGroundColorBlack()
        super.onDestroyView()
    }

    override fun getViewModelClass(): Class<AccountViewModel> = AccountViewModel::class.java

//    fun fun

    fun logoutApi() {
        viewModel.onLogoutApi()
    }
}