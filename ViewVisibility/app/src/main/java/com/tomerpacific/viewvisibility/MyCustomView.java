package com.tomerpacific.viewvisibility;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.CENTER;

public class MyCustomView extends LinearLayout {

    private final String TAG = MyCustomView.class.getSimpleName();

    public MyCustomView(Context context) {
        super(context);
        this.setBackgroundColor(Color.GREEN);
        this.setGravity(CENTER);
        TextView myTextView = new TextView(context);
        myTextView.setText("My Custom View");
        addView(myTextView);
    }

    @Override
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        Log.d(TAG, "View " + changedView + " changed visibility to " + visibility);
    }

    @Override
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        Log.d(TAG, "Window visibility changed to " + visibility);
    }

}
