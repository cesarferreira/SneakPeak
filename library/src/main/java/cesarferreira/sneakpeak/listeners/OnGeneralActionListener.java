package cesarferreira.sneakpeak.listeners;

import android.view.View;

/**
 * Created by cesarferreira on 06/03/2018.
 */
public interface OnGeneralActionListener {
    void onPeek(View longClickView, int position);

    void onPop(View longClickView, int position);
}
