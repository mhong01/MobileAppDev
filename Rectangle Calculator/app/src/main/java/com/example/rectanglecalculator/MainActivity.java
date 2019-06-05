package com.example.rectanglecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private double widthVal = 0.0;
    private double heightVal = 0.0;

    private double areaVal = 0.0;
    private double perimeterVal = 0.0;

    private EditText widthText;
    private EditText heightText;
    private EditText areaText;
    private EditText perimeterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        handleEvents(this.widthText);
    }

    private void init(){
        widthText = (EditText) findViewById(R.id.txtWidth);
        heightText = (EditText) findViewById(R.id.txtWidth);
        areaText = (EditText) findViewById(R.id.txtWidth);
        perimeterText = (EditText) findViewById(R.id.txtWidth);


        widthText.setText(Double.toString(widthVal));
        heightText.setText(Double.toString(widthVal));
        areaText.setText(Double.toString(widthVal));
        perimeterText.setText(Double.toString(widthVal));

    }

    private void handleEvents(View view){
//        widthText.setKeyListener
    }
}
