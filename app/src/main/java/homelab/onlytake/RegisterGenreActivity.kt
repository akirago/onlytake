package homelab.onlytake

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import homelab.onlytake.database.AppDatabase
import homelab.onlytake.database.Genre
import homelab.onlytake.databinding.ActivityRegisterGenreBinding
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
