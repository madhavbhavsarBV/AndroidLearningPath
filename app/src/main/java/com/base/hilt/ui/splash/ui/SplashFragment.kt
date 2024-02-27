package com.base.hilt.ui.splash.ui

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.base.hilt.BuildConfig
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentSplashBinding
import com.base.hilt.domain.model.ConfigInput
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.splash.viewmodel.SplashViewModel
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : FragmentBase<SplashViewModel, FragmentSplashBinding>() {

    @Inject
    lateinit var pref:MyPreference

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel())
    }

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java


    override fun initializeScreenVariables() {
        Glide.with(requireContext()).load(R.drawable.splash_anim).into(DrawableImageViewTarget(getDataBinding().ivLogo))

        viewModel.callConfigApi(ConfigInput(
            device_type = 2,
            app_version = BuildConfig.VERSION_NAME,
            version_code = BuildConfig.VERSION_CODE.toString()
        ))
        observeData()

    }

    private fun observeData() {

        viewModel.configLiveData.observe(viewLifecycleOwner){
            when(it){
                ResponseHandler.Loading -> {
                    Log.i("madconfig", "observeData: config loading")
                }
                is ResponseHandler.OnFailed -> {
                    Log.i("madconfig", "observeData: config failed ${it.message}")
                }
                is ResponseHandler.OnSuccessResponse -> {
                    Log.i("madconfig", "observeData: config succ ${it.response.data}")
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(3500)
            if (pref.getValueBoolean(PrefKey.IS_LOGINED,false)){
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

            }else if (pref.getValueBoolean(PrefKey.GETTING_STARTED,false)){
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

            }else{
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }
        }
    }

}
