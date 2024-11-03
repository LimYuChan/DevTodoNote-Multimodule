package com.note.feature_base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.note.core.navigator.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class BaseFragment<T: ViewDataBinding>(
    @LayoutRes val layoutRes: Int
): Fragment() {

    private var _binding: T? = null

    @Inject
    lateinit var navigator: Navigator

    protected val binding: T
        get() = _binding ?: throw IllegalStateException("뷰의 생명주기 밖에서 Binding에 접근하고 있습니다.")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showDialogFragment(fragment: DialogFragment?){
        fragment ?: return
        if (isAdded && !fragment.isAdded) {
            val tag = fragment.javaClass.simpleName
            if (childFragmentManager.findFragmentByTag(tag) == null) {
                fragment.show(childFragmentManager, tag)
            }
        }
    }

    protected fun safeDismiss(fragment: DialogFragment?) {
        if(fragment?.isAdded == true){
            fragment.dismiss()
        }
    }

    protected fun <T> collectLatestLifecycleFlow(lifecycleState: Lifecycle.State = Lifecycle.State.STARTED, flow: Flow<T>, collect: suspend  (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycleState) {
                flow.collectLatest(collect)
            }
        }
    }


    protected fun <T> collectLifecycleFlow(lifecycleState: Lifecycle.State = Lifecycle.State.STARTED, flow: Flow<T>, collect: suspend  (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycleState) {
                flow.collect(collect)
            }
        }
    }

    protected fun showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        message ?: return
        Toast.makeText(context, message, duration).show()
    }
}