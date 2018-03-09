package cesarferreira.sneakpeek.model

import android.view.View
import cesarferreira.sneakpeek.SneakPeek
import java.util.*


class HoldAndReleaseView(var view: View?) {

    var position: Int = 0

    var holdAndReleaseTimer: Timer

    init {
        this.position = -1
        this.holdAndReleaseTimer = Timer()
    }

    fun startHoldAndReleaseTimer(sneakPeek: SneakPeek, position: Int, duration: Long) {
        val holdAndReleaseTimer = Timer()
        this.position = position

        holdAndReleaseTimer.schedule(object : TimerTask() {
            override fun run() {
                sneakPeek.setCurrentHoldAndReleaseView(this@HoldAndReleaseView)
                sneakPeek.triggerOnHoldEvent(view!!, position)
            }
        }, duration)

        this.holdAndReleaseTimer = holdAndReleaseTimer
    }
}
