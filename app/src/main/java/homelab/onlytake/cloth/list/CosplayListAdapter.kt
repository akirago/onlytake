package homelab.onlytake.cloth.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import homelab.onlytake.databinding.ViewholderCosplayListBinding


data class CosplayData(val resId: Int, val useNumber: String)

class CosplayListAdapter(private val itemList: List<CosplayData>) : RecyclerView.Adapter<CosplayListAdapter.CosplayViewHolder>() {

    class CosplayViewHolder(val binding: ViewholderCosplayListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosplayViewHolder {
        val binding = ViewholderCosplayListBinding.inflate(LayoutInflater.from(parent.context))
        return CosplayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CosplayViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.imageView.setImageResource(item.resId)
        holder.binding.useTime.text = item.useNumber.toString()
    }

    override fun getItemCount() = itemList.size
}