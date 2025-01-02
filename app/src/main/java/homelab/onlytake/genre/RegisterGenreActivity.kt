package homelab.onlytake.genre

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import homelab.onlytake.CustomApplication
import homelab.onlytake.database.Genre
import homelab.onlytake.databinding.ActivityRegisterGenreBinding
import kotlinx.coroutines.launch

class RegisterGenreActivity : AppCompatActivity() {

    private val genreViewModel: RegisterGenreViewModel by viewModels {
        RegisterGenreViewModelFactory((application as CustomApplication).registerGenreRepository)
    }

    private lateinit var binding: ActivityRegisterGenreBinding
    private lateinit var adapter: GenreAdapter

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
            genreViewModel.allGenre.collect { genres ->
                if (!this@RegisterGenreActivity::adapter.isInitialized) {
                    adapter = GenreAdapter(genres, genreViewModel)
                    binding.genreList.adapter = adapter
                    binding.genreList.layoutManager =
                        LinearLayoutManager(this@RegisterGenreActivity)
                } else {
                    adapter.updateGenres(genres)
                }
            }
        }
    }

    private fun saveGenreToDatabase(genreName: String) {
        val genre = Genre(id = 0, name = genreName)

        lifecycleScope.launch {
            genreViewModel.addGenre(genre)
            binding.etGenreName.text.clear()
        }
    }
}
