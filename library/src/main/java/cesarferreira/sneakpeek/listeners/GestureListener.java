package cesarferreira.sneakpeek.listeners;

import android.content.res.Configuration;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cesarferreira.sneakpeek.SneakPeek;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private SneakPeek sneakPeek;
    private int position;
    private View view;

    public GestureListener(SneakPeek sneakPeek) {
        this.sneakPeek = sneakPeek;
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
        if (sneakPeek.onFlingToActionListener != null)
            return handleFling(velocityX, velocityY);
        else
            return true;
    }

    private boolean handleFling(float velocityX, float velocityY) {
        if (sneakPeek.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (velocityY < -SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowUpwardsFling) {
                flingToAction(SneakPeek.FLING_UPWARDS, velocityX, velocityY);
                return false;
            } else if (velocityY > SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowDownwardsFling) {
                flingToAction(SneakPeek.FLING_DOWNWARDS, velocityX, velocityY);
                return false;
            }
        } else if (sneakPeek.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (velocityX < -SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowUpwardsFling) {
                flingToAction(SneakPeek.FLING_UPWARDS, velocityX, velocityY);
                return false;
            } else if (velocityX > SneakPeek.FLING_VELOCITY_THRESHOLD && sneakPeek.allowDownwardsFling) {
                flingToAction(SneakPeek.FLING_DOWNWARDS, velocityX, velocityY);
                return false;
            }
        }
        return true;
    }

    private void flingToAction(@SneakPeek.FlingDirections int direction, float velocityX, float velocityY) {
        sneakPeek.onFlingToActionListener.onFlingToAction(view, position, direction);
        if (sneakPeek.animateFling) {
            if (direction == SneakPeek.FLING_UPWARDS) {
                sneakPeek.peekAnimationHelper.animateExpand(SneakPeek.ANIMATION_POP_DURATION, sneakPeek.popTime);
                sneakPeek.peekAnimationHelper.animateFling(velocityX, velocityY, SneakPeek.ANIMATION_POP_DURATION, sneakPeek.popTime, -SneakPeek.FLING_VELOCITY_MAX);
            } else {
                sneakPeek.peekAnimationHelper.animateFling(velocityX, velocityY, SneakPeek.ANIMATION_POP_DURATION, sneakPeek.popTime, SneakPeek.FLING_VELOCITY_MAX);
            }
        }
    }
}
