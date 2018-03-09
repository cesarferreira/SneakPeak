package cesarferreira.sneakpeek.listeners;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import cesarferreira.sneakpeek.SneakPeek;

public class PeekAndPopOnTouchListener implements View.OnTouchListener {

    private SneakPeek sneakPeek;
    private int position;
    private Runnable longHoldRunnable;
    private boolean peekShown;

    public PeekAndPopOnTouchListener(SneakPeek sneakPeek, int position) {
        this.sneakPeek = sneakPeek;
        this.position = position;
    }

    @Override
    public boolean onTouch(final View view, MotionEvent event) {
        if (!sneakPeek.enabled) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            peekShown = false;
            cancelPendingTimer(view);
            startTimer(view);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            cancelPendingTimer(view);
        }

        if (peekShown) {
            sneakPeek.handleTouch(view, event, position);
        }
        return peekShown;
    }

    /**
     * Cancel pending timer and if the timer has already activated, run another runnable to
     * pop the view.
     *
     * @param view
     */
    private void cancelPendingTimer(@NonNull final View view) {
        sneakPeek.longHoldTimer.cancel();
        if (longHoldRunnable != null) {
            longHoldRunnable = new Runnable() {
                @Override
                public void run() {
                    peekShown = false;
                    sneakPeek.pop(view, position);
                    longHoldRunnable = null;
                }
            };
            sneakPeek.builder.activity.runOnUiThread(longHoldRunnable);
        }
    }

    /**
     * Start the longHoldTimer, if it reaches the long hold duration, peek
     *
     * @param view
     */
    private void startTimer(@NonNull final View view) {
        sneakPeek.longHoldTimer = new Timer();
        sneakPeek.longHoldTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                peekShown = true;
                longHoldRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (peekShown) {
                            sneakPeek.peek(view, position);
                            longHoldRunnable = null;
                        }
                    }
                };
                sneakPeek.builder.activity.runOnUiThread(longHoldRunnable);
            }
        }, SneakPeek.LONG_CLICK_DURATION);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
