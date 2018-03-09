package cesarferreira.sneakpeak.model

import android.view.View
import cesarferreira.sneakpeak.SneakPeak
import java.util.*


class HoldAndReleaseView(var view: View?) {

    var position: Int = 0

    var holdAndReleaseTimer: Timer

    init {
        this.position = -1
        this.holdAndReleaseTimer = Timer()
    }

    fun startHoldAndReleaseTimer(sneakPeak: SneakPeak, position: Int, duration: Long) {
        val holdAndReleaseTimer = Timer()
        this.position = position

        holdAndReleaseTimer.schedule(object : TimerTask() {
            override fun run() {
                sneakPeak.setCurrentHoldAndReleaseView(this@HoldAndReleaseView)
                sneakPeak.triggerOnHoldEvent(view!!, position)
            }
        }, duration)

        this.holdAndReleaseTimer = holdAndReleaseTimer
    }
}
