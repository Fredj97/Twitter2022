package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SaveActivity extends AppCompatActivity {
    TextView tvSave;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_save);
        tvSave = findViewById(R.id.Save);
        String File = "myfile" + ".txt";
        FileInputStream fis = null;
        try {
            fis = openFileInput(File);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader= new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (  BufferedReader reader= new BufferedReader(inputStreamReader)){
            String line=reader.readLine();
            while (line!=null){
                stringBuilder.append(line).append("\n\n\n");
                line = reader.readLine();

            }


        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            String contents = stringBuilder.toString();
        }

        tvSave.setText(stringBuilder);
        }
    }

