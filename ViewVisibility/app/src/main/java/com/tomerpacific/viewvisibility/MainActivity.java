package com.tomerpacific.viewvisibility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button addCustomViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCustomViewBtn = (Button) findViewById(R.id.addCustomViewBtn);

        addCustomViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                MyCustomView myCustomView = new MyCustomView(getApplicationContext());
                myCustomView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                mainLayout.addView(myCustomView);
            }
        });
    }
}
