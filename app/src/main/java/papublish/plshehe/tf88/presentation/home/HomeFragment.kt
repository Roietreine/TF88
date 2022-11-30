package papublish.plshehe.tf88.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.databinding.FragmentHomeBinding
import papublish.plshehe.tf88.presentation.viewmodel.DataViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var viewModelProvider = DataViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModelProvider = ViewModelProvider(this)[DataViewModel::class.java]
        setContentView()
        return binding.root
    }

    private fun setContentView() {
        viewModelProvider.displayFun()
        val adapts = HomeAdapter()
        viewModelProvider.displayLiveData.observe(viewLifecycleOwner) {
            if (it != null){
                adapts.setAdapter(it)
                binding.homeRecyclerview.apply {
                    setHasFixedSize(true)
                    adapter = adapts
                }
            }
        }
    }
}