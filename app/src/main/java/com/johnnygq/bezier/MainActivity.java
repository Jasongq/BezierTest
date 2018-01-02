package com.johnnygq.bezier;

import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void QuadTo(View view) {
        startActivity(new Intent(this,QuadToActivity.class));
    }

    public void CubicTo(View view) {
        startActivity(new Intent(this,CubicToActivity.class));
    }

    public void WaveTo(View view) {
        startActivity(new Intent(this,WaveToActivity.class));
    }
}
