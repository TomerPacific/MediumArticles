package com.tomerpacific.androidinternalstorage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String FILENAME = "hello_world_file";
    private String inputToFile = "Hello From Internal Storage!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void saveFileInternalStorage(View view) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write(inputToFile.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(),
                    "File " + FILENAME + " has been saved successfully",
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "File " + FILENAME + " has not been saved successfully due to an exception " + e.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "File " + FILENAME + " has not been saved successfully due to an exception " + e.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
