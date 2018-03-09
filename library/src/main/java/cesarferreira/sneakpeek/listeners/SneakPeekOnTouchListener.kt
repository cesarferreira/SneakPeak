package cesarferreira.sneakpeek.listeners

import android.view.MotionEvent
import android.view.View
import cesarferreira.sneakpeek.SneakPeek
import java.util.*

class SneakPeekOnTouchListener(private val sneakPeek: SneakPeek,
                               private var position: Int) : View.OnTouchListener {
    private var longHoldRunnable: Runnable? = null
    private var peekShown: Boolean = false

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (!sneakPeek.enabled) return false

        if (event.action == MotionEvent.ACTION_DOWN) {
            peekShown = false
            cancelPendingTimer(view)
            startTimer(view)
        } else if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            cancelPendingTimer(view)
        }

        if (peekShown) {
            sneakPeek.handleTouch(view, event, position)
        }
        return peekShown
    }

    /**
     * Cancel pending timer and if the timer has already activated, run another runnable to
     * pop the view.
     *
     * @param view
     */
    private fun cancelPendingTimer(view: View) {
        sneakPeek.longHoldTimer.cancel()
        if (longHoldRunnable != null) {
            longHoldRunnable = Runnable {
                peekShown = false
                sneakPeek.pop(view, position)
                longHoldRunnable = null
            }
            sneakPeek.builder.activity.runOnUiThread(longHoldRunnable)
        }
    }

    /**
     * Start the longHoldTimer, if it reaches the long hold duration, peek
     *
     * @param view
     */
    private fun startTimer(view: View) {
        sneakPeek.longHoldTimer = Timer()
        sneakPeek.longHoldTimer.schedule(object : TimerTask() {
            override fun run() {
                peekShown = true
                longHoldRunnable = Runnable {
                    if (peekShown) {
                        sneakPeek.peek(view, position)
                        longHoldRunnable = null
                    }
                }
                sneakPeek.builder.activity.runOnUiThread(longHoldRunnable)
            }
        }, SneakPeek.LONG_CLICK_DURATION)
    }

    fun setPosition(position: Int) {
        this.position = position
    }
}
