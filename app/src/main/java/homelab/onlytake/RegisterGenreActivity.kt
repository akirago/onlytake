package homelab.onlytake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import homelab.onlytake.database.AppDatabase
import homelab.onlytake.database.Genre
import homelab.onlytake.databinding.ActivityRegisterGenreBinding
import homelab.onlytake.databinding.ViewholderRegisterResultBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterGenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegisterGenre.setOnClickListener {
            val genreName = binding.etGenreName.text.toString()
            if (genreName.isNotEmpty()) {
                saveGenreToDatabase(genreName)
            } else {
                Toast.makeText(this, "Please enter a genre name", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            lifecycleScope.launch(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build()
                val genres = db.genreDao().getAllGenres()
                runOnUiThread {
                    binding.genreList.adapter = GenreAdapter(genres)
                    binding.genreList.layoutManager =
                        LinearLayoutManager(this@RegisterGenreActivity)
                }
            }
        }
    }

    private fun saveGenreToDatabase(genreName: String) {
        val genre = Genre(id = 0, name = genreName)

        lifecycleScope.launch {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "app_database"
            ).build()
            db.genreDao().insert(genre)
            runOnUiThread {
                Toast.makeText(
                    this@RegisterGenreActivity,
                    "Genre registered successfully",
                    Toast.LENGTH_SHORT
                ).show()
                binding.etGenreName.text.clear()
            }
        }
    }
}

class GenreAdapter(private val itemList: List<Genre>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {


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
    }
}