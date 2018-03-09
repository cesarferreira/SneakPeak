package cesarferreira.sneakpeek.listeners;

import android.view.View;

/**
 * Created by cesarferreira on 06/03/2018.
 */
public interface OnHoldAndReleaseListener {
    void onHold(View view, int position);

    void onLeave(View view, int position);

    void onRelease(View view, int position);
}
