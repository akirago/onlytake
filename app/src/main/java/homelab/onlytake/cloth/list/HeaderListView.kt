package homelab.onlytake.cloth.list

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import homelab.onlytake.R

class HeaderListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val headerTextView: TextView
    private val recyclerView: RecyclerView

    init {
        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.view_header_list, this, true)
        headerTextView = findViewById(R.id.headerTextView)
        recyclerView = findViewById(R.id.recyclerView)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val marginDecoration = MarginItemDecoration(16) // 16dp margin
        recyclerView.addItemDecoration(marginDecoration)
    }

    // Method to set the header text
    fun setHeader(header: String) {
        headerTextView.text = header
    }

    // Method to set the list data
    fun setListData(items: List<CosplayData>) {
        recyclerView.adapter = CosplayListAdapter(items).apply {
            setListener(listener)
        }
    }

    private var listener: (CosplayData) -> Unit = {}

    fun setListener(doEvent: (CosplayData) -> Unit) {
        listener = doEvent
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