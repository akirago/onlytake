package homelab.onlytake.cloth.list

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import homelab.onlytake.R
import homelab.onlytake.databinding.ActivityCosplayListBinding

class CosplayListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCosplayListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainRecyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)

        // Prepare the data
        val headerListData = listOf(
            HeaderListData(
                header = "Category 1",
                items = arrayListOf<CosplayData>(
                    CosplayData(R.drawable.maid1, "1"),
                    CosplayData(R.drawable.maid2, "1"),
                    CosplayData(R.drawable.maid3,"1"),
                    CosplayData(R.drawable.maid1, "1"),
                    CosplayData(R.drawable.maid2, "1"),
                    CosplayData(R.drawable.maid3, "1"),
                )
            ),
            HeaderListData(
                header = "Category 2",
                items = arrayListOf<CosplayData>(
                    CosplayData(R.drawable.uniform1, "未使用"),
                    CosplayData(R.drawable.uniform2, "2"),
                    CosplayData(R.drawable.uniform3, "1"),
                    CosplayData(R.drawable.uniform1, "未使用"),
                    CosplayData(R.drawable.uniform2, "2"),
                    CosplayData(R.drawable.uniform3, "1"),
                )
            )
        )

        // Set up the main RecyclerView
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = MainAdapter(headerListData)
    }
}

