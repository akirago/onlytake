package homelab.onlytake.genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import homelab.onlytake.database.Genre
import homelab.onlytake.databinding.ViewholderRegisterResultBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GenreAdapter(
    private var itemList: List<Genre>,
    private val genreViewModel: RegisterGenreViewModel
) :
    ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreComparator()) {

    @SuppressLint("NotifyDataSetChanged")
    fun updateGenres(newGenres: List<Genre>) {
        itemList = newGenres
        notifyDataSetChanged()
    }

    class GenreViewHolder(val binding: ViewholderRegisterResultBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ViewholderRegisterResultBinding.inflate(LayoutInflater.from(parent.context))
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.genreName.text = item.name
        holder.binding.genreDeleteButton.setOnClickListener {
            GlobalScope.launch {
                genreViewModel.deleteGenre(item)
            }
        }
    }

    class GenreComparator : DiffUtil.ItemCallback<Genre>() {
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }
}