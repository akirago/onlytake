package homelab.onlytake.cloth.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import homelab.onlytake.CustomApplication
import homelab.onlytake.R
import homelab.onlytake.databinding.ActivityCosplayListBinding

class CosplayListActivity : AppCompatActivity() {

    private val viewModel: CosplayListViewModel by viewModels {
        CosplayListViewModelFactory((application as CustomApplication).cosplayListRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCosplayListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainRecyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)
        viewModel.fetchHeaderListData {
            // Set up the main RecyclerView
            mainRecyclerView.layoutManager = LinearLayoutManager(this)
            mainRecyclerView.adapter = MainAdapter(it) { cloth ->
                println("testtest ${cloth.id}")
                viewModel.deleteClothData(cloth.id)
            }
        }
    }
}

