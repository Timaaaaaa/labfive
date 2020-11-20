package com.thousand.labfive;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bounce ball = new Bounce(this);
        setContentView(ball);

        Toast.makeText(this, "Агай пожалуйста, можно хорошую оценку", Toast.LENGTH_SHORT).show();
    }


}