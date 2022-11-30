package papublish.plshehe.tf88.presentation.dashboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.findNavController
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var exit = false

    companion object{
        fun getStartIntent(context : Context) = Intent(context, MainActivity::class.java)
    }
    private val currentFragment by lazy {
        findNavController(R.id.fragmentView)
    }

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onBackPressed() {
        if (currentFragment.currentDestination?.id == R.id.mainFragment) {
            if (exit) {
                finishAffinity()
                return
            }
            exit = true
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ exit = false }, 2000)
        } else {
            findNavController(R.id.fragmentView).navigateUp()
        }
    }
}