package cesarferreira.sneakpeak.listeners;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import cesarferreira.sneakpeak.SneakPeak;

public class PeekAndPopOnTouchListener implements View.OnTouchListener {

    private SneakPeak sneakPeak;
    private int position;
    private Runnable longHoldRunnable;
    private boolean peekShown;

    public PeekAndPopOnTouchListener(SneakPeak sneakPeak, int position) {
        this.sneakPeak = sneakPeak;
        this.position = position;
    }

    @Override
    public boolean onTouch(final View view, MotionEvent event) {
        if (!sneakPeak.enabled) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            peekShown = false;
            cancelPendingTimer(view);
            startTimer(view);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            cancelPendingTimer(view);
        }

        if (peekShown) {
            sneakPeak.handleTouch(view, event, position);
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
        sneakPeak.longHoldTimer.cancel();
        if (longHoldRunnable != null) {
            longHoldRunnable = new Runnable() {
                @Override
                public void run() {
                    peekShown = false;
                    sneakPeak.pop(view, position);
                    longHoldRunnable = null;
                }
            };
            sneakPeak.builder.activity.runOnUiThread(longHoldRunnable);
        }
    }

    /**
     * Start the longHoldTimer, if it reaches the long hold duration, peek
     *
     * @param view
     */
    private void startTimer(@NonNull final View view) {
        sneakPeak.longHoldTimer = new Timer();
        sneakPeak.longHoldTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                peekShown = true;
                longHoldRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (peekShown) {
                            sneakPeak.peek(view, position);
                            longHoldRunnable = null;
                        }
                    }
                };
                sneakPeak.builder.activity.runOnUiThread(longHoldRunnable);
            }
        }, SneakPeak.LONG_CLICK_DURATION);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
