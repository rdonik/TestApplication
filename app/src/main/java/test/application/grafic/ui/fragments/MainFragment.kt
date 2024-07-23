package test.application.grafic.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import test.application.grafic.R
import test.application.grafic.base.BaseFragment
import test.application.grafic.databinding.FragmentMainBinding
import test.application.grafic.support.extensions.goto
import test.application.grafic.support.extensions.hideKeyboard
import test.application.grafic.support.isNumeric
import test.application.grafic.ui.activity.MainViewModel

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnGo.setOnClickListener {
            hideKeyboard()
            if (binding.etPoints.text?.isNotEmpty() == true && binding.etPoints.text.toString().isNumeric()) {
                viewModel.fetchPoints(binding.etPoints.text.toString().toInt()).observe(viewLifecycleOwner) {
                    goto(R.id.graphicFragment)
                }
            } else {
                Toast.makeText(requireContext(), "Нужно выбрать количество точек", Toast.LENGTH_LONG).show()
            }
        }
    }
}
