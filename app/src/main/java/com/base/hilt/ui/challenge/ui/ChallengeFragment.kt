package com.base.hilt.ui.challenge.ui

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentChallengeBinding
import com.base.hilt.domain.model.ChallengeRequestModel
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.challenge.adapter.ChallengeViewPagerAdapter
import com.base.hilt.ui.challenge.interfaces.BtnNextValidations
import com.base.hilt.ui.challenge.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class ChallengeFragment : FragmentBase<ChallengeViewModel, FragmentChallengeBinding>(),
    BtnNextValidations {
    override fun getLayoutId(): Int = R.layout.fragment_challenge

    override fun setupToolbar() {

        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                type = 4,
                isBottomNavVisible = false
            )
        )
    }

    override fun initializeScreenVariables() {


        getDataBinding().viewmodel = viewModel

        // set up view pager
        setUpViewPagerAdapter()

        // set observers
        observerData()

        // set toolbar back button
        setUpBackButtonNavigation()
    }

    private fun setUpBackButtonNavigation() {
        (requireActivity() as MainActivity).binding.layToolbar.imgBackStep.setOnClickListener {
            when (getDataBinding().vpChallenges.currentItem) {
                0 -> {
                    findNavController().popBackStack()
                }

                1 -> getDataBinding().vpChallenges.setCurrentItem(0, true)
                2 -> getDataBinding().vpChallenges.setCurrentItem(1, true)
                3 -> getDataBinding().vpChallenges.setCurrentItem(2, true)
            }
        }
        (requireActivity() as MainActivity).binding.layToolbar.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun getViewModelClass(): Class<ChallengeViewModel> = ChallengeViewModel::class.java


    lateinit var createChallengeFragment: Fragment
    lateinit var createDetailFragment: Fragment
    lateinit var createDescriptionFragment: Fragment
    lateinit var reviewChallengeFragment: Fragment
    private fun setUpViewPagerAdapter() {

        createChallengeFragment = CreateChallengeFragment()
        (createChallengeFragment as CreateChallengeFragment).setInterface(this)
        createDetailFragment = ChallengeDetailFragment()
        createDescriptionFragment = ChallengeDescriptionFragment()//.setInterface(this)
        reviewChallengeFragment = ReviewChallengeFragment()//.setInterface(this)

        getDataBinding().vpChallenges.adapter =
            ChallengeViewPagerAdapter(
                requireActivity(),
                arrayListOf(
                    createChallengeFragment,
                    createDetailFragment,
                    createDescriptionFragment,
                    reviewChallengeFragment
                )
            )

        getDataBinding().vpChallenges.isUserInputEnabled = false

        getDataBinding().vpChallenges.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        getDataBinding().uHLine2.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey_2
                            )
                        )
                    }

                    1 -> {
                        getDataBinding().uHLine2.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green_4
                            )
                        )
                        getDataBinding().uHLine3.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey_2
                            )
                        )
                    }

                    2 -> {
                        getDataBinding().uHLine3.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green_4
                            )
                        )
                        getDataBinding().uHLine4.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey_2
                            )
                        )
                    }

                    3 -> {
                        getDataBinding().uHLine4.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green_4
                            )
                        )
                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

    }


    private fun observerData() {
        viewModel.apply {

            onBtnNextClick?.observe(viewLifecycleOwner) {

                when (getDataBinding().vpChallenges.currentItem) {
                    0 -> getDataBinding().vpChallenges.setCurrentItem(1, true)
                    1 -> {
                        if ((createDetailFragment as ChallengeDetailFragment).checkValidations()) {
                            getDataBinding().vpChallenges.setCurrentItem(2, true)
                        }

                    }

                    2 -> getDataBinding().vpChallenges.setCurrentItem(3, true)
                    3 -> {
                        findNavController().popBackStack()
                        Log.i("restapi", "observerData: here0")
                        callApi()
                    }

                }

            }


        }

        viewModel.createChallengeLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.showProgressBar(it is ResponseHandler.Loading)
            Log.i("restapi", "observerData: ${it}")
            when (it) {
                is ResponseHandler.OnFailed -> {
                    Log.i(
                        "restapi",
                        "observerData: ${it.code} ${it.message} ${it.messageCode} ${it.data}"
                    )
                }

                is ResponseHandler.OnSuccessResponse -> {
                    Log.i("restapi", "observerData: ${it.response}")
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.challenge_created_3),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {
                    Log.i("restapi", "observerData:else")
                }
            }
        })

    }

    private fun callApi() {

        val req = ChallengeRequestModel(
            title = "Hello1",
            description = "desc1",
            type = "1",
            amount = "0",
            start_at = "03-13-2024",
            end_at = "03-13-2024",
            accept_by = "03-13-2024",
            judge_id = "5424c1ee-dd8d-41cf-8fff-0a5a76dff642",
            participants = listOf("379746c7-ca5a-4e4e-9eaa-0c2bdec2f398"),
        )
        val requestBody = MultipartBody.Builder()
        requestBody.setType(MultipartBody.FORM)
        requestBody.addFormDataPart("title", req.title)
        requestBody.addFormDataPart("description", req.description)
        requestBody.addFormDataPart("type", req.type)
        requestBody.addFormDataPart("amount", req.amount)
        requestBody.addFormDataPart("start_at", req.start_at)
        requestBody.addFormDataPart("end_at", req.end_at)
        requestBody.addFormDataPart("judge_id", req.judge_id)
        requestBody.addFormDataPart("accept_by", req.accept_by)
        requestBody.addFormDataPart("participants", req.participants.toString())
//            .addFormDataPart(
//                "image",
//                "photoFile.name",
//                RequestBody.create("image/*".toMediaTypeOrNull(), "photoFile")
//            )
        val request = requestBody.build()

        viewModel.callCreateChallenge(request)

    }

    override fun btnEnabled(selectedContacts: Int) {
        getDataBinding().btnNext.isEnabled = selectedContacts != 0
    }


}

