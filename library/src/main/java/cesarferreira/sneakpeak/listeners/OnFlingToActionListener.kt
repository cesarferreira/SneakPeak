package cesarferreira.sneakpeak.listeners

import android.view.View

interface OnFlingToActionListener {
    fun onFlingToAction(longClickView: View, position: Int, direction: Int)
}
