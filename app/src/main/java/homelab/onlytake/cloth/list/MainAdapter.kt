package homelab.onlytake.cloth.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import homelab.onlytake.R

data class HeaderListData(
    val header: String,
    val items: List<CosplayData>
)


class MainAdapter(private val data: List<HeaderListData>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerListView: HeaderListView = view.findViewById(R.id.headerListView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item_list, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val headerListData = data[position]

        // Set the header and list data for the HeaderListView
        holder.headerListView.setHeader(headerListData.header)
        holder.headerListView.setListData(headerListData.items)
    }

    override fun getItemCount(): Int = data.size
}
