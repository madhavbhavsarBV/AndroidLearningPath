package com.base.hilt

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.base.hilt.base.LocaleManager
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.ActivityMainBinding
import com.base.hilt.ui.account.ui.AccountFragment
import com.base.hilt.ui.home.ui.HomeFragment
import com.base.hilt.ui.login.ui.LoginFragment
import com.base.hilt.ui.messages.ui.MessagesFragment
import com.base.hilt.ui.notifications.ui.NotificationsFragment
import com.base.hilt.ui.universe.ui.UniverseFragment
import com.base.hilt.utils.DebugLog
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import com.base.hilt.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var dialog: Dialog? = null
    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController
    lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var mPref: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        DebugLog.e("onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //   val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*   val appBarConfiguration = AppBarConfiguration(
               setOf(
                   R.id.navigation_home, R.id.navigation_dashboard
               )
           )*/
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.layToolbar.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * Show Progress dialog
     * @param t: true show progress bar
     *
     *  */
    fun displayProgress(t: Boolean) {
        // binding.loading = t
        if (t) {
            if (dialog == null) {
                dialog = Utils.progressDialog(this)
            }
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }

    /**
     * This method is used to hide the keyboard.
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Toolbar manages items and visibility according to
     */
//    fun toolBarManagement(toolbarModel: ToolbarModel?) {
//        if (toolbarModel != null) {
////            when {
////                toolbarModel.isVisible -> {
////                    binding.layToolbar.appBar.visibility = View.VISIBLE
////                    binding.layToolbar.toolbarTitle.text = toolbarModel.title
////                }
////                else -> {
////                    binding.layToolbar.appBar.visibility = View.GONE
////                }
////            }
////
////            when {
////                toolbarModel.loginVisible -> {
////                    binding.layToolbar.toolbarLogin.visibility = View.VISIBLE
////                }
////                else -> {
////                    binding.layToolbar.toolbarLogin.visibility = View.INVISIBLE
////                }
////            }
////            when {
////                toolbarModel.backBtnVisible -> {
////                    binding.layToolbar.imgBack.visibility = View.VISIBLE
////                }
////                else -> {
////                    binding.layToolbar.imgBack.visibility = View.INVISIBLE
////                }
////            }
//            if (toolbarModel.isBottomNavVisible) {
//                binding.navView.visibility = View.VISIBLE
//                binding.imgBrandLogo.visibility = View.VISIBLE
//            } else {
//                binding.navView.visibility = View.GONE
//                binding.imgBrandLogo.visibility = View.GONE
//            }
//
//        }
//    }


    fun toolBarManagement(toolbarModel: ToolbarModel?) {
        if (toolbarModel != null) {
            binding.layToolbar.toolbar = toolbarModel

            if (toolbarModel.isBottomNavVisible) {
                binding.navView.visibility = View.VISIBLE
                binding.imgBrandLogo.visibility = View.VISIBLE
            }else {
                binding.navView.visibility = View.GONE
                binding.imgBrandLogo.visibility = View.GONE
            }
        }
    }

    /**
     * Override method for fragments attach
     */
    override fun attachBaseContext(base: Context) {
        DebugLog.e("attachBaseContext")
        // super.attachBaseContext(base);
        val languageCode = MyApp.applicationContext().mPref.getValueString(
            PrefKey.SELECTED_LANGUAGE,
            PrefKey.EN_CODE
        )
        useCustomConfig(base, languageCode)?.let { super.attachBaseContext(it) }
    }


    /**
     * Method called when language changes done
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }

    /**
     * After applied locale changes override method called
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val languageCode = mPref.getValueString(PrefKey.SELECTED_LANGUAGE, PrefKey.EN_CODE)
        window.decorView.layoutDirection =
            if (languageCode == PrefKey.AR_CODE) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun useCustomConfig(context: Context, languageCode: String?): Context? {
        Locale.setDefault(Locale(languageCode!!))
        return if (Build.VERSION.SDK_INT >= 17) {
            val config = Configuration()
            config.setLocale(Locale(languageCode))
            context.createConfigurationContext(config)
        } else {
            val res: Resources = context.resources
            val config =
                Configuration(res.configuration)
            config.locale = Locale(languageCode)
            res.updateConfiguration(config, res.displayMetrics)
            context
        }
    }

    /**
     *
     */
    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    override fun onBackPressed() {
        when (navHostFragment.childFragmentManager.fragments[0]) {
            is MessagesFragment, is NotificationsFragment, is AccountFragment ,is UniverseFragment-> {
                navHostFragment.navController.navigate(R.id.homeFragment)
            }

            is LoginFragment, is HomeFragment -> {
                finish()
            }

            else -> {
                navHostFragment.navController.popBackStack()
            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.black)
        window.setBackgroundDrawableResource(R.drawable.bg_account)
    }

    fun backGroundColorBlack() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.black)
    }

}