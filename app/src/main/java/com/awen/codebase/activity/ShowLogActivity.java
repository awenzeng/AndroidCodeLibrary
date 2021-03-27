package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import com.awen.codebase.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Describe:
 * Created by AwenZeng on 2021/03/03
 */
public class ShowLogActivity extends Activity {

    private TextView mContentTv;
    private StringBuilder mStringBuilder;
    public static final String FILE_PAHT = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_log);
        mContentTv = findViewById(R.id.content);
        String path = getIntent().getStringExtra(FILE_PAHT);
        mStringBuilder = new StringBuilder();
        loadData(path);
    }

    private void loadData(String path) {
        File file = new File(path);
        String str = null;
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                mStringBuilder.append(str);
                mStringBuilder.append("\n");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mContentTv.setText(mStringBuilder.toString());
    }
}
