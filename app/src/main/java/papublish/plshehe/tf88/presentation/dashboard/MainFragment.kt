package papublish.plshehe.tf88.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.databinding.FragmentMainBinding
import papublish.plshehe.tf88.presentation.dashboard.adapter.CarouselAdapter
import papublish.plshehe.tf88.utils.StaticData
import papublish.plshehe.tf88.utils.Utils


class MainFragment : Fragment() , View.OnClickListener {

    private var _binding : FragmentMainBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this


        _binding = FragmentMainBinding.inflate(inflater,container,false)
        onItemClick()
        carouselView()
        return binding.root
    }
    private fun carouselView() {
        binding.carouselViewholder.apply {
            isUserInputEnabled = false
            adapter = CarouselAdapter(StaticData.dataImg)
            Utils.setTimer(this, StaticData.dataImg.size)
        }
    }

    private fun onItemClick(){
        binding.homeButton.setOnClickListener(this)
        binding.historyButton.setOnClickListener(this)
        binding.minigameButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view){
            binding.homeButton -> findNavController().navigate(R.id.action_mainFragment_to_homeFragment)
            binding.historyButton -> findNavController().navigate(R.id.action_mainFragment_to_historyFragment)
            binding.minigameButton -> findNavController().navigate(R.id.action_mainFragment_to_ticTacToeFragment)
        }
    }
}