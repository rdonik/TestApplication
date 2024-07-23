package test.application.grafic.ui.activity

import android.os.Bundle
import test.application.grafic.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import test.application.grafic.R
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        if (navController == null) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            navController?.navInflater?.inflate(R.navigation.nav_graph)?.apply {
                setStartDestination(R.id.mainFragment)
                navController?.setGraph(this, intent.extras)
            }
        }
    }
}


