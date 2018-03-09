package cesarferreira.sneakpeek.model;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import cesarferreira.sneakpeek.SneakPeek;

/**
 * Created by cesarferreira on 09/03/2018.
 */


public class HoldAndReleaseView {
    private View view;
    private int position;
    protected Timer holdAndReleaseTimer;

    public HoldAndReleaseView(View view) {
        this.view = view;
        this.position = -1;
        this.holdAndReleaseTimer = new Timer();
    }

    public void startHoldAndReleaseTimer(@NonNull final SneakPeek peekAndPop, int position, long duration) {
        Timer holdAndReleaseTimer = new Timer();
        this.position = position;
        holdAndReleaseTimer.schedule(new TimerTask() {
            public void run() {
                peekAndPop.setCurrentHoldAndReleaseView(HoldAndReleaseView.this);
            }
        }, duration);
        this.holdAndReleaseTimer = holdAndReleaseTimer;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Timer getHoldAndReleaseTimer() {
        return this.holdAndReleaseTimer;
    }

    public void setHoldAndReleaseTimer(Timer holdAndReleaseTimer) {
        this.holdAndReleaseTimer = holdAndReleaseTimer;
    }
}