package com.tomerpacific.viewvisibility

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout

class MyCustomView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val TAG = MyCustomView::class.java.simpleName

    init {
        setBackgroundColor(Color.GREEN)
    }

    public override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Log.d(TAG, "View $changedView changed visibility to $visibility")
    }

    public override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.d(TAG, "Window visibility changed to $visibility")
    }

}