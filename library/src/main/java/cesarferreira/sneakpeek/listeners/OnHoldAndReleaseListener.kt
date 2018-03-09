package cesarferreira.sneakpeek.listeners

import android.view.View


interface OnHoldAndReleaseListener {
    fun onHold(view: View, position: Int)
    fun onLeave(view: View, position: Int)
    fun onRelease(view: View, position: Int)
}
