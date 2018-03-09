package cesarferreira.sneakpeak.listeners;

import android.content.res.Configuration;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cesarferreira.sneakpeak.SneakPeak;

/**
 * Created by cesarferreira on 06/03/2018.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private SneakPeak sneakPeak;
    private int position;
    private View view;

    public GestureListener(SneakPeak sneakPeak) {
        this.sneakPeak = sneakPeak;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent firstEvent, MotionEvent secondEvent, float velocityX, float velocityY) {
        if (sneakPeak.onFlingToActionListener != null)
            return handleFling(velocityX, velocityY);
        else
            return true;
    }

    private boolean handleFling(float velocityX, float velocityY) {
        if (sneakPeak.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (velocityY < -SneakPeak.FLING_VELOCITY_THRESHOLD && sneakPeak.allowUpwardsFling) {
                flingToAction(SneakPeak.FLING_UPWARDS, velocityX, velocityY);
                return false;
            } else if (velocityY > SneakPeak.FLING_VELOCITY_THRESHOLD && sneakPeak.allowDownwardsFling) {
                flingToAction(SneakPeak.FLING_DOWNWARDS, velocityX, velocityY);
                return false;
            }
        } else if (sneakPeak.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (velocityX < -SneakPeak.FLING_VELOCITY_THRESHOLD && sneakPeak.allowUpwardsFling) {
                flingToAction(SneakPeak.FLING_UPWARDS, velocityX, velocityY);
                return false;
            } else if (velocityX > SneakPeak.FLING_VELOCITY_THRESHOLD && sneakPeak.allowDownwardsFling) {
                flingToAction(SneakPeak.FLING_DOWNWARDS, velocityX, velocityY);
                return false;
            }
        }
        return true;
    }

    private void flingToAction(@SneakPeak.FlingDirections int direction, float velocityX, float velocityY) {
        sneakPeak.onFlingToActionListener.onFlingToAction(view, position, direction);
        if (sneakPeak.animateFling) {
            if (direction == SneakPeak.FLING_UPWARDS) {
                sneakPeak.peekAnimationHelper.animateExpand(SneakPeak.ANIMATION_POP_DURATION, sneakPeak.popTime);
                sneakPeak.peekAnimationHelper.animateFling(velocityX, velocityY, SneakPeak.ANIMATION_POP_DURATION, sneakPeak.popTime, -SneakPeak.FLING_VELOCITY_MAX);
            } else {
                sneakPeak.peekAnimationHelper.animateFling(velocityX, velocityY, SneakPeak.ANIMATION_POP_DURATION, sneakPeak.popTime, SneakPeak.FLING_VELOCITY_MAX);
            }
        }
    }
}
