package homelab.onlytake.list

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import homelab.onlytake.R
import homelab.onlytake.databinding.ActivityCosplayListBinding

class CosplayListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCosplayListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val maidList = arrayListOf<CosplayData>(
            CosplayData(R.drawable.maid1, "1"),
            CosplayData(R.drawable.maid2, "1"),
            CosplayData(R.drawable.maid3,"1"),
            CosplayData(R.drawable.maid1, "1"),
            CosplayData(R.drawable.maid2, "1"),
            CosplayData(R.drawable.maid3, "1"),
        )
        val uniformList = arrayListOf<CosplayData>(
            CosplayData(R.drawable.uniform1, "未使用"),
            CosplayData(R.drawable.uniform2, "2"),
            CosplayData(R.drawable.uniform3, "1"),
            CosplayData(R.drawable.uniform1, "未使用"),
            CosplayData(R.drawable.uniform2, "2"),
            CosplayData(R.drawable.uniform3, "1"),
        )
        binding.maidRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.maidRecyclerView.adapter = CosplayListAdapter(maidList)
        binding.maidRecyclerView.addItemDecoration(MarginItemDecoration(8))
        binding.uniformRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.uniformRecyclerView.adapter = CosplayListAdapter(uniformList)
        binding.uniformRecyclerView.addItemDecoration(MarginItemDecoration(8))
    }
}

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = spaceHeight
        outRect.left = spaceHeight
        outRect.right = spaceHeight
        outRect.bottom = spaceHeight
    }
}
