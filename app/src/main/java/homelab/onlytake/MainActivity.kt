package homelab.onlytake

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import homelab.onlytake.databinding.ActivityMainBinding
import homelab.onlytake.list.CosplayListActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkCos.setOnClickListener {
            startActivity(Intent(this, CosplayListActivity::class.java))
        }

        binding.registerCos.setOnClickListener {
//            startActivity(Intent(this, ))
        }
    }
}
