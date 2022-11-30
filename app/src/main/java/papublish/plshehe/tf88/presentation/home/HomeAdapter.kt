package papublish.plshehe.tf88.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import papublish.plshehe.tf88.common.model.DataModel
import papublish.plshehe.tf88.databinding.HomeDetailsBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var emptyDescriptionList = emptyList<DataModel>()
    class ViewHolder(val adapts : HomeDetailsBinding): RecyclerView.ViewHolder(adapts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = HomeDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(emptyDescriptionList[position]){
                adapts.homeDescription.text = this.descriptions
            }
        }
    }

    override fun getItemCount(): Int {
       return emptyDescriptionList.size
    }

    fun setAdapter (setAdapt : List<DataModel>){
        this.emptyDescriptionList = setAdapt
    }
}