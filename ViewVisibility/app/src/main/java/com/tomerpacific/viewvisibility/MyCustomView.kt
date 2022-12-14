package com.tomerpacific.viewvisibility

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.LinearLayout
import android.view.Gravity
import android.view.View
import android.widget.TextView

class MyCustomView(context: Context?) : LinearLayout(context) {
    private val TAG = MyCustomView::class.java.simpleName

    public override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Log.d(TAG, "View $changedView changed visibility to $visibility")
    }

    public override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.d(TAG, "Window visibility changed to $visibility")
    }

    init {
        setBackgroundColor(Color.GREEN)
        this.gravity = Gravity.CENTER
        val myTextView = TextView(context)
        myTextView.text = "My Custom View"
        addView(myTextView)
    }
}