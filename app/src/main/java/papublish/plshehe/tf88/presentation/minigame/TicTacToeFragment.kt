package papublish.plshehe.tf88.presentation.minigame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.databinding.FragmentTicTacToeBinding


class TicTacToeFragment : Fragment() {

    private var _binding : FragmentTicTacToeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicTacToeBinding.inflate(inflater,container,false)
        hideSystemBars()
        btnStartGameOnClick()
        return binding.root

    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(requireActivity().window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun btnStartGameOnClick() {
        binding.btnStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_ticTacToeFragment_to_gameFragment)
        }
    }
}