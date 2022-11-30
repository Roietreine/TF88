package papublish.plshehe.tf88.presentation.history.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.viewpager.widget.PagerAdapter
import papublish.plshehe.tf88.R
import papublish.plshehe.tf88.common.model.DataModel
import papublish.plshehe.tf88.databinding.FragmentHistoryBinding
import papublish.plshehe.tf88.presentation.history.adapter.DataAdapter
import papublish.plshehe.tf88.presentation.history.adapter.HistoryAdapter
import papublish.plshehe.tf88.presentation.viewmodel.DataViewModel
import papublish.plshehe.tf88.utils.HistoryInterface
import papublish.plshehe.tf88.utils.StaticData.Companion.pagerImage
import papublish.plshehe.tf88.utils.animation.PagerTransformer


class HistoryFragment : Fragment(), HistoryInterface {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var viewModelProvider = DataViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        viewModelProvider = ViewModelProvider(this)[DataViewModel::class.java]

        pagerInitFun()
        displayData()
        return binding.root
    }

    private fun displayData() {
        viewModelProvider.displayFun()
        val adapts = DataAdapter(this)
        viewModelProvider.displayLiveData.observe(viewLifecycleOwner){
            if (it != null){
                adapts.setAdapter(it)
                binding.historyRecycler.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context,HORIZONTAL,false)
                    adapter = adapts
                }
            }
        }
    }

    private fun pagerInitFun() {
        binding.pagerItems.apply {
            val pagerLimit = pagerImage.size
            adapter = HistoryAdapter(pagerImage)
            offscreenPageLimit = pagerLimit
            clipToPadding = false
            setPageTransformer(PagerTransformer(pagerLimit))
        }
        binding.previousPicture.setOnClickListener {
            pagerState(isNext = false)
        }
        binding.nextPicture.setOnClickListener {
            pagerState(isNext = true)
        }
    }

    private fun pagerState(isNext: Boolean) {
        binding.pagerItems.apply {
            when (isNext) {
                true -> currentItem += 1
                false -> currentItem -= 1
            }
        }
    }

    override fun onItemClick(data: DataModel) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_content)
        dialog.window?.attributes?.windowAnimations = R.style.DialogRotationAnim
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val content: TextView = dialog.findViewById(R.id.content)
        val close : ImageView = dialog.findViewById(R.id.closeDialog)

        content.text = data.descriptions
        close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}