package cesarferreira.sneakpeek.model

import android.view.View
import cesarferreira.sneakpeek.SneakPeek
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
    fun startLongHoldViewTimer(sneakPeek: SneakPeek, position: Int, duration: Long) {
        val longHoldTimer = Timer()
        longHoldTimer.schedule(object : TimerTask() {
            override fun run() {
                sneakPeek.sendOnLongHoldEvent(view, position)
                if (isReceiveMultipleEvents) {
                    startLongHoldViewTimer(sneakPeek, position, duration)
                }
            }
        }, duration)

        this.longHoldTimer = longHoldTimer
    }
}
