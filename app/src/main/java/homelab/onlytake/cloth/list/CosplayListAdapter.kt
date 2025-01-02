package homelab.onlytake.cloth.list

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.processing.SurfaceProcessorNode.In
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import homelab.onlytake.CustomApplication
import homelab.onlytake.CustomApplication.Companion.context
import homelab.onlytake.R
import homelab.onlytake.databinding.ViewholderCosplayListBinding


data class CosplayData(val id: Int, val bitmap: Bitmap, val title: String)

class CosplayListAdapter(private val itemList: List<CosplayData>, private val listener: (CosplayData) -> Unit) :
    RecyclerView.Adapter<CosplayListAdapter.CosplayViewHolder>() {

    class CosplayViewHolder(val binding: ViewholderCosplayListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosplayViewHolder {
        val binding = ViewholderCosplayListBinding.inflate(LayoutInflater.from(parent.context))
        return CosplayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CosplayViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.imageView.setImageBitmap(item.bitmap)
        holder.binding.title.text = item.title
        holder.binding.root.setOnClickListener {
            AlertDialog.Builder(holder.binding.root.context)
                .setTitle("消去しますか")
                .setNegativeButton("しない") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("消去する") { dialog, which ->
                    listener(item)
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount() = itemList.size
}