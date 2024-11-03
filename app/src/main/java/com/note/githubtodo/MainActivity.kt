package com.note.githubtodo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.note.auth.presentation.AuthFragment
import com.note.auth.presentation.AuthViewModel
import com.note.core.common.Logg
import com.note.core.navigator.Navigator
import com.note.core.navigator.RouteEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator
    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private var isLoadingBaseInfo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSplashScreen()
        setupNavigation()
        collectEvents()
        collectRouteEvents()
    }

    private fun setupSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition { isLoadingBaseInfo }
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun collectEvents() {
        lifecycleScope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    is MainEvent.LoadFinish -> handleLoadFinish(event.isLoggedIn)
                }
            }
        }
    }

    private fun handleLoadFinish(isLoggedIn: Boolean) {
        setupStartDestination(isLoggedIn)
        lifecycleScope.launch {
            delay(100) // 이 지연이 필요한지 확인 필요
            isLoadingBaseInfo = false
        }
    }

    private fun setupStartDestination(isLoggedIn: Boolean) {
        val destinationGraphId = if (isLoggedIn) {
            com.note.home.presentation.R.id.home_nav_graph
        } else {
            com.note.auth.presentation.R.id.auth_nav_graph
        }
        setStartDestination(destinationGraphId)
    }

    private fun setStartDestination(@IdRes destinationGraphId: Int) {
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph).apply {
            setStartDestination(destinationGraphId)
        }
        navController.graph = navGraph
    }

    private fun collectRouteEvents() {
        lifecycleScope.launch {
            navigator.routeEvent.collect { routeEvent ->
                handleRouteEvent(routeEvent)
            }
        }
    }

    private fun handleRouteEvent(routeEvent: RouteEvent) {
        when (routeEvent) {
            is RouteEvent.NavigateUp -> navController.navigateUp()
            is RouteEvent.AuthToHome -> navController.navigate(R.id.action_auth_to_home)
            is RouteEvent.HomeToTask -> navigateToHomeToTask(routeEvent)
            is RouteEvent.Unauthorized -> navigateToLoginScreen()
            else -> Unit
        }
    }

    private fun navigateToHomeToTask(routeEvent: RouteEvent.HomeToTask) {
        val bundle = bundleOf(
            "repositoryId" to routeEvent.repositoryId,
            "repositoryName" to routeEvent.repositoryName
        )
        navController.navigate(R.id.action_home_to_auth, bundle)
    }

    private fun navigateToLoginScreen() {
        navController.navigate(
            com.note.auth.presentation.R.id.auth_nav_graph,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.main_nav_graph, true)
                .build()
        )
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager?.primaryNavigationFragment
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val intentDataUri = intent.data ?: return
        if (intentDataUri.toString().startsWith(BuildConfig.GITHUB_REDIRECT_CALLBACK)) {
            (getCurrentFragment() as? AuthFragment)?.onAuthResultHandle(
                stateKey = intentDataUri.getQueryParameter(AuthViewModel.AUTH_RESULT_STATE_KEY),
                code = intentDataUri.getQueryParameter("code")
            )
        }
    }
}