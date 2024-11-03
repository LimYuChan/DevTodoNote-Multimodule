package com.note.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.note.core.navigator.RouteEvent
import com.note.core.ui.dialog.loading.LoadingDialog
import com.note.core.ui.paging.PagingLoadStateAdapter
import com.note.feature_base.BaseFragment
import com.note.home.domain.exception.UnauthorizedException
import com.note.home.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val repositoryAdapter: UserRepositoryAdapter by lazy {
        UserRepositoryAdapter { repository ->
            val routeEvent = RouteEvent.HomeToTask(repository.id, repository.name)
            navigator.navigate(routeEvent)
        }.apply {
            withLoadStateFooter(footer = PagingLoadStateAdapter(this))
        }
    }

    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog()
        }
        showDialogFragment(loadingDialog)
        setupAdapter()
        collectRepositories()
    }

    private fun setupAdapter() {
        with(binding) {
            rvRepositories.adapter = repositoryAdapter
            repositoryAdapter.addLoadStateListener { loadStates->
                val refreshState = loadStates.refresh

                if (refreshState is LoadState.Error) {
                    val exception = refreshState.error
                    if (exception is UnauthorizedException) {
                        showToast(getString(R.string.unauthorized))
                        navigator.navigate(RouteEvent.Unauthorized)
                    } else {
                        showToast(exception.localizedMessage)
                    }
                }
            }
            swipeRefreshLayout.setOnRefreshListener { repositoryAdapter.refresh() }
        }
    }

    private fun collectRepositories() {
        collectLifecycleFlow(flow = viewModel.repositories) { repositories ->
            safeDismiss(loadingDialog)
            repositoryAdapter.submitData(repositories)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog = null
    }
}