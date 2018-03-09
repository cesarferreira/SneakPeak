package cesarferreira.sneakpeek

import android.content.Context
import android.util.TypedValue
import android.view.View


class DimensionUtil {
    companion object {

        fun convertDpToPx(context: Context, dp: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
        }

        fun pointInViewBounds(view: View, x: Int, y: Int): Boolean {
            val l = IntArray(2)
            view.getLocationOnScreen(l)
            val viewX = l[0]
            val viewY = l[1]
            val viewWidth = view.width
            val viewHeight = view.height

            return !(x < viewX || x > viewX + viewWidth || y < viewY || y > viewY + viewHeight)
        }
    }

}
