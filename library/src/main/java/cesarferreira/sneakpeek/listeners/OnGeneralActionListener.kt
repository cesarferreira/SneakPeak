package cesarferreira.sneakpeek.listeners

import android.view.View

interface OnGeneralActionListener {
    fun onPeek(longClickView: View, position: Int)
    fun onPop(longClickView: View, position: Int)
}
