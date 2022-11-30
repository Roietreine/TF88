package papublish.plshehe.tf88.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import papublish.plshehe.tf88.databinding.CarouselViewholderBinding

class CarouselAdapter(val dataImg : ArrayList<Int>)
    : RecyclerView.Adapter<CarouselAdapter.Viewholder>() {
    class Viewholder (val adapts : CarouselViewholderBinding) : RecyclerView.ViewHolder(adapts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val carouselViewholderBinding = CarouselViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(carouselViewholderBinding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
       holder.adapts.apply {
           carouselPlaceholder.setBackgroundResource(dataImg[position])
       }

    }
    override fun getItemCount(): Int {
        return dataImg.size
    }
}