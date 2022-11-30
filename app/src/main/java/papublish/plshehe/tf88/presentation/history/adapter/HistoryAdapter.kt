package papublish.plshehe.tf88.presentation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import papublish.plshehe.tf88.databinding.PagerItemPlaceholderBinding
import papublish.plshehe.tf88.utils.Utils.Companion.getRandomNumbers
import papublish.plshehe.tf88.utils.widget.glideImage

class HistoryAdapter(var pagerImg: ArrayList<Int>
) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    inner class ViewHolder(val adapts: PagerItemPlaceholderBinding) :
        RecyclerView.ViewHolder(adapts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder =
            PagerItemPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.adapts.apply {
            casinoLogo.glideImage(pagerImg[position])
            rating.rating = getRandomNumbers(0, 5).toFloat()
        }

    }

    override fun getItemCount(): Int {
        return pagerImg.size
    }
}