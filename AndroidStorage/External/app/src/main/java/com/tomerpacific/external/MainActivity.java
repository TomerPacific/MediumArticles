package com.tomerpacific.external;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private String FILENAME = "hello_world_file";
    private String inputToFile = "Hello From Internal Storage!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void saveFileExternalStorage(View view) {
        if (isExternalStorageWritable()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                writeFileToExternalStorage();
            } else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
            {
                writeFileToExternalStorage();
                break;
            }
        }
    }


    public void writeFileToExternalStorage() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_files");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {
            File file = new File(myDir, "myfile.txt");
            FileOutputStream out = new FileOutputStream(file);
            out.write(inputToFile.getBytes());
            out.close();
            Toast.makeText(getApplicationContext(),
                    "File myfile.txt" + " has been saved successfully to external storage",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
