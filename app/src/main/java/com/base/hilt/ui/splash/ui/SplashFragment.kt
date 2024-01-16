package com.base.hilt.ui.splash.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentSplashBinding
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
class SplashFragment : FragmentBase<ViewModelBase, FragmentSplashBinding>() {

    @Inject
    lateinit var pref:MyPreference

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel())
    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java


    override fun initializeScreenVariables() {
        Glide.with(requireContext()).load(R.drawable.splash_anim).into(DrawableImageViewTarget(getDataBinding().ivLogo));
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
