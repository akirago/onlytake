package homelab.onlytake

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import homelab.onlytake.databinding.ActivityMainBinding
import homelab.onlytake.genre.RegisterGenreActivity
import homelab.onlytake.cloth.list.CosplayListActivity
import homelab.onlytake.cloth.register.TakeClothesActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkCos.setOnClickListener {
            startActivity(Intent(this, CosplayListActivity::class.java))
        }

        binding.registerCos.setOnClickListener {
            startActivity(Intent(this, TakeClothesActivity::class.java))
        }

        binding.registerGenreButton.setOnClickListener {
            startActivity(Intent(this, RegisterGenreActivity::class.java))
        }
    }
}
