package com.withparadox2.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.withparadox2.progresstext.ProgressText;

public class MyActivity extends Activity {
    ProgressText textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        textView = (ProgressText) findViewById(R.id.progress_text);
        textView.setText("绿岛小夜曲");
        textView.setProgressBypercentage(0.68f);
    }
}
