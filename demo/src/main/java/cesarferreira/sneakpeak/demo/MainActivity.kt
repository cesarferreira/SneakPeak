package cesarferreira.sneakpeak.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import cesarferreira.faker.Faker
import cesarferreira.sneakpeak.SneakPeak
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sneakPeak: SneakPeak
    private lateinit var adapter: ListItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        sneakPeak = initializePeekAndPop()

        adapter = ListItemsAdapter(getFakeItems() as ArrayList<ItemViewModel>, sneakPeak)

        recyclerView.adapter = adapter
    }

    private fun getFakeItems() = (0..500).map { ItemViewModel(it, "title $it", Faker.getRandomImage(300, 400)) }.toList()

    private fun initializePeekAndPop(): SneakPeak {
        return SneakPeak.Builder(this)
                .blurBackground(true)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(recyclerView)
                .build()
    }

    public override fun onDestroy() {
        super.onDestroy()
        sneakPeak.destroy()
    }

}
