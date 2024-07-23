package test.application.grafic.base

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import test.application.grafic.R
import test.application.grafic.shared.enums.MessageType
import test.application.grafic.support.extensions.hideKeyboard

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    open val viewModel: BaseViewModel? = null

    private var progressDialog: DialogProgressBar? = null

    private var alertDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        if (isAdded) {
            viewModel?.viewModelScope?.launch {
                viewModel?.errorApiResult?.collect {
                    showMessage(it.message)
                }
            }
            viewModel?.viewModelScope?.launch {
                viewModel?.progressBarState?.collect {
                    try {
                        if (it) {
                            if (progressDialog == null) {
                                progressDialog = DialogProgressBar()
                                progressDialog?.show(childFragmentManager, "")
                            }
                        } else {
                            progressDialog?.dismissAllowingStateLoss()
                            progressDialog = null
                        }
                    } catch (e: Exception) { }
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showMessage(message: String?, messageType: MessageType = MessageType.ERROR) {
        if (message != null && view != null && context != null && isAdded) {
            hideKeyboard()
            try {
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).apply {
                    val snackBarView = this.view
                    val color = when (messageType) {
                        MessageType.WARNING -> {
                            setTextColor(ContextCompat.getColor(context, R.color.black))
                            Color.YELLOW
                        }

                        MessageType.ERROR -> ContextCompat.getColor(context, R.color.red)
                        else -> ContextCompat.getColor(context, R.color.color_green)
                    }
                    snackBarView.setBackgroundColor(color)
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }.show()
            } catch (e: Exception) { }
        }
    }
}