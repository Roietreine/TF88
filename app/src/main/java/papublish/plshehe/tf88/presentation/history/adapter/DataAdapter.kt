package papublish.plshehe.tf88.presentation.history.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import papublish.plshehe.tf88.common.model.DataModel
import papublish.plshehe.tf88.databinding.HistoryDetailsBinding
import papublish.plshehe.tf88.utils.HistoryInterface

class DataAdapter(val listener : HistoryInterface)
    :RecyclerView.Adapter<DataAdapter.ViewHolder>(){

    private var descriptionList = emptyList<DataModel>()
    inner class ViewHolder (val adapts : HistoryDetailsBinding) : RecyclerView.ViewHolder(adapts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = HistoryDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(descriptionList[position]){
                adapts.description.text = this.descriptions
                adapts.description.ellipsize = TextUtils.TruncateAt.MARQUEE
                adapts.description.isSelected = true
                adapts.onItemClicked.setOnClickListener {
                    listener.onItemClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return descriptionList.size
    }


    fun setAdapter (setAdapt : List<DataModel>){
        this.descriptionList = setAdapt
    }


}