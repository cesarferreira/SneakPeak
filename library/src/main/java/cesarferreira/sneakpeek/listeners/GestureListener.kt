package cesarferreira.sneakpeek.listeners

import android.content.res.Configuration
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

import cesarferreira.sneakpeek.SneakPeek

class GestureListener(private val sneakPeek: SneakPeek) : GestureDetector.SimpleOnGestureListener() {
    private var position: Int = 0
    private var view: View? = null

    fun setView(view: View) {
        this.view = view
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onFling(firstEvent: MotionEvent?, secondEvent: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        return if (sneakPeek.onFlingToActionListener != null)
            handleFling(velocityX, velocityY)
        else
            true
    }

    private fun handleFling(velocityX: Float, velocityY: Float): Boolean {
        if (sneakPeek.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (velocityY < -SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowUpwardsFling) {
                flingToAction(SneakPeek.FLING_UPWARDS, velocityX, velocityY)
                return false
            } else if (velocityY > SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowDownwardsFling) {
                flingToAction(SneakPeek.FLING_DOWNWARDS, velocityX, velocityY)
                return false
            }
        } else if (sneakPeek.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (velocityX < -SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowUpwardsFling) {
                flingToAction(SneakPeek.FLING_UPWARDS, velocityX, velocityY)
                return false
            } else if (velocityX > SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowDownwardsFling) {
                flingToAction(SneakPeek.FLING_DOWNWARDS, velocityX, velocityY)
                return false
            }
        }
        return true
    }

    private fun flingToAction(@SneakPeek.FlingDirections direction: Int, velocityX: Float, velocityY: Float) {
        sneakPeek.onFlingToActionListener.onFlingToAction(view!!, position, direction)
        if (sneakPeek.animateFling) {
            if (direction == SneakPeek.FLING_UPWARDS) {
                sneakPeek.peekAnimationHelper.animateExpand(SneakPeek.ANIMATION_POP_DURATION, sneakPeek.popTime)
                sneakPeek.peekAnimationHelper.animateFling(velocityX, velocityY, SneakPeek.ANIMATION_POP_DURATION, sneakPeek.popTime, -SneakPeek.FLING_VELOCITY_MAX)
            } else {
                sneakPeek.peekAnimationHelper.animateFling(velocityX, velocityY, SneakPeek.ANIMATION_POP_DURATION, sneakPeek.popTime, SneakPeek.FLING_VELOCITY_MAX)
            }
        }
    }
}
