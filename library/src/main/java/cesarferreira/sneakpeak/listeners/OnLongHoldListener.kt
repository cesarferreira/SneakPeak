package cesarferreira.sneakpeak.listeners

import android.view.View

interface OnLongHoldListener {
    fun onEnter(view: View, position: Int)
    fun onLongHold(view: View, position: Int)
}
