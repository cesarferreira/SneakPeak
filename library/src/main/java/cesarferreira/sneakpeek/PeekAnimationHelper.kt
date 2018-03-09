package cesarferreira.sneakpeek

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

/**
 *
 *
 * Helper class for animating the SneakPeek views
 */
class PeekAnimationHelper(private val context: Context, private val peekLayout: View, private val peekView: View) {

    /**
     * Occurs on on long hold.
     *
     *
     * Animates the peek view to fade in and scale to it's full size.
     * Also fades the peek background layout in.
     */
    fun animatePeek(duration: Int) {
        peekView.alpha = 1f
        val animatorLayoutAlpha = ObjectAnimator.ofFloat(peekLayout, "alpha", 1f)
        animatorLayoutAlpha.interpolator = OvershootInterpolator(1.2f)
        animatorLayoutAlpha.duration = duration.toLong()
        val animatorScaleX = ObjectAnimator.ofFloat(peekView, "scaleX", 1f)
        animatorScaleX.duration = duration.toLong()
        val animatorScaleY = ObjectAnimator.ofFloat(peekView, "scaleY", 1f)
        animatorScaleY.duration = duration.toLong()
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = OvershootInterpolator(1.2f)
        animatorSet.play(animatorScaleX).with(animatorScaleY)

        animatorSet.start()
        animatorLayoutAlpha.start()
    }

    /**
     * Occurs on touch up.
     *
     *
     * Animates the peek view to return to it's original position and shrink.
     * Also animate the peek background layout to fade out.
     */
    fun animatePop(animatorListener: Animator.AnimatorListener, duration: Int) {
        val animatorLayoutAlpha = ObjectAnimator.ofFloat(peekLayout, "alpha", 0f)
        animatorLayoutAlpha.duration = duration.toLong()
        animatorLayoutAlpha.addListener(animatorListener)
        animatorLayoutAlpha.interpolator = DecelerateInterpolator(1.5f)

        animatorLayoutAlpha.start()
        animateReturn(duration)
    }

    /**
     * Occurs when the peek view is dragged but not flung.
     *
     *
     * Animate the peek view back to it's original position and shrink it.
     */
    fun animateReturn(duration: Int) {
        val animatorTranslate: ObjectAnimator
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            animatorTranslate = ObjectAnimator.ofFloat(peekView, "translationY", 0f)
        } else {
            animatorTranslate = ObjectAnimator.ofFloat(peekView, "translationX", 0f)
        }
        val animatorShrinkY = ObjectAnimator.ofFloat(peekView, "scaleY", 0.75f)
        val animatorShrinkX = ObjectAnimator.ofFloat(peekView, "scaleX", 0.75f)
        animatorShrinkX.interpolator = DecelerateInterpolator()
        animatorShrinkY.interpolator = DecelerateInterpolator()
        animatorTranslate.interpolator = DecelerateInterpolator()
        animatorShrinkX.duration = duration.toLong()
        animatorShrinkY.duration = duration.toLong()
        animatorTranslate.duration = duration.toLong()
        animatorShrinkX.start()
        animatorShrinkY.start()
        animatorTranslate.start()
    }

    /**
     * Occurs when the peek view is flung.
     *
     *
     * Animate the peek view to expand slightly.
     */
    fun animateExpand(duration: Int, popTime: Long) {
        val timeDifference = System.currentTimeMillis() - popTime
        val animatorExpandY = ObjectAnimator.ofFloat(peekView, "scaleY", 1.025f)
        val animatorExpandX = ObjectAnimator.ofFloat(peekView, "scaleX", 1.025f)
        animatorExpandX.interpolator = DecelerateInterpolator()
        animatorExpandY.interpolator = DecelerateInterpolator()
        animatorExpandX.duration = Math.max(0, duration - timeDifference)
        animatorExpandY.duration = Math.max(0, duration - timeDifference)
        animatorExpandX.start()
        animatorExpandY.start()
    }


    /**
     * Occurs when the peek view is flung.
     *
     *
     * Animate the peek view up towards the top of the screen.
     * The duration of the animation is the same as the pop animate, minus
     * the time since the pop occurred.
     */
    fun animateFling(velocityX: Float, velocityY: Float, duration: Int, popTime: Long, flingVelocityMax: Float) {
        val timeDifference = System.currentTimeMillis() - popTime
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val translationAmount = Math.max(velocityY / 8, flingVelocityMax)
            val animatorTranslateY = ObjectAnimator.ofFloat(peekView, "translationY", translationAmount)
            animatorTranslateY.interpolator = DecelerateInterpolator()
            animatorTranslateY.duration = Math.max(0, duration - timeDifference)
            animatorTranslateY.start()
        } else if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val translationAmount = Math.max(velocityX / 8, flingVelocityMax)
            val animatorTranslateX = ObjectAnimator.ofFloat(peekView, "translationX", translationAmount)
            animatorTranslateX.interpolator = DecelerateInterpolator()
            animatorTranslateX.duration = Math.max(0, duration - timeDifference)
            animatorTranslateX.start()
        }
    }
}
