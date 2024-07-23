package test.application.grafic.support.extensions

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

inline fun Fragment?.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    this?.viewLifecycleOwner?.lifecycleScope?.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

fun Fragment.goto(direction: NavDirections) {
    activity?.let {
        findNavController().navigate(direction)
    }
}

fun Fragment.goto(id: Int) {
    activity?.let {
        findNavController().navigate(id)
    }
}

fun Fragment.goto(id: Int, bundle: Bundle) {
    activity?.let {
        findNavController().navigate(id, bundle)
    }
}

fun Fragment.popBackStack() {
    activity?.let {
        findNavController().popBackStack()
    }
}