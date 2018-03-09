package cesarferreira.sneakpeak.model

import android.view.View
import cesarferreira.sneakpeak.SneakPeak
import java.util.*

class LongHoldView(var view: View?, var isReceiveMultipleEvents: Boolean) {

    lateinit var longHoldTimer: Timer

    /**
     * Sets a timer on the long hold view that will send a long hold event after the duration
     * If receiveMultipleEvents is true, it will set another timer directly after for the duration * 1.5
     *
     * @param position
     * @param duration
     */
    fun startLongHoldViewTimer(sneakPeak: SneakPeak, position: Int, duration: Long) {
        val longHoldTimer = Timer()
        longHoldTimer.schedule(object : TimerTask() {
            override fun run() {
                sneakPeak.sendOnLongHoldEvent(view, position)
                if (isReceiveMultipleEvents) {
                    startLongHoldViewTimer(sneakPeak, position, duration)
                }
            }
        }, duration)

        this.longHoldTimer = longHoldTimer
    }
}
