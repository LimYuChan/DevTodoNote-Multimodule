package com.note.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import com.note.auth.presentation.databinding.FragmentAuthBinding
import com.note.core.common.Logg
import com.note.core.navigator.RouteEvent
import com.note.feature_base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment: BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth){

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        collectEvents()
    }

    private fun setupUI() {
        with(binding){
            model = viewModel
            btnAuth.setClickListener {
                viewModel.onClickLogin()
            }
        }
    }

    private fun collectEvents() {
        collectLifecycleFlow(flow = viewModel.event) { event ->
            when(event) {
                is AuthEvent.CreateLoginUri -> {
                    if(isAdded){
                        CustomTabsIntent.Builder().build().also {
                            it.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            it.launchUrl(requireContext(), event.uri)
                        }
                    }
                }
                is AuthEvent.LoginSuccess -> {
                    navigator.navigate(RouteEvent.AuthToHome)
                }

                is AuthEvent.Error -> {
                    showToast(event.error.asString(context))
                }
            }
        }
    }

    fun onAuthResultHandle(stateKey: String?, code: String?) {
        viewModel.getAccessToken(stateKey, code)
    }
}