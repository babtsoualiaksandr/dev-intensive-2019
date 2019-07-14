package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.opengl.ETC1.getHeight
import android.util.Log
import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import kotlin.math.roundToLong


fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.getCurrentFocus()
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
}

fun Activity.isKeyboardOpen1(): Boolean {

    var result: Boolean = false
    val contentView = RelativeLayout(this)
    contentView.getViewTreeObserver().addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
        val r = Rect()
        contentView.getWindowVisibleDisplayFrame(r)
        val screenHeight = contentView.getRootView().getHeight()

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - r.bottom



        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
            result = true
        } else {
            result = false
        }
    })



    val rootView = this.window.decorView // this = activity
    rootView.getWindowVisibleDisplayFrame(Rect())

    val heightView = rootView.getHeight()
    val widthView = rootView.getWidth()

    val screenHeight = rootView.getRootView().getHeight()

    return result
}


fun Activity.isKeyboardOpen2(): Boolean{
    val rootView = findViewById<View>(android.R.id.content)
    Log.d("M_Activity","****")
    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = rootView.height - visibleBounds.height()
    Log.d("M_Activity","rootView.height  ${rootView.height} visibleBounds.height ${visibleBounds.height()}")
   // val marginOfError = this.convertDpToPx(50F).roundToLong()

    val result: Boolean = (heightDiff > 100)

    return result
}

fun Activity.isKeyboardClosed2(): Boolean {
    return this.isKeyboardOpen().not()
}


fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}
fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}
fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = Math.round(this.convertDpToPx(50F))
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}
