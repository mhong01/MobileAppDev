package com.example.rectanglecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements OnEditorActionListener, View.OnClickListener {


    private double widthVal = 0.0;
    private double heightVal = 0.0;

    private double areaVal = 0.0;
    private double perimeterVal = 0.0;

    private EditText widthText;
    private EditText heightText;
    private TextView areaText;
    private TextView perimeterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        widthText = (EditText) findViewById(R.id.txtWidth);
        heightText = (EditText) findViewById(R.id.txtHeight);
        areaText = (TextView) findViewById(R.id.lblDisplayArea);
        perimeterText = (TextView) findViewById(R.id.lblDisplayPerimeter);


        widthText.setText(Double.toString(widthVal));
        heightText.setText(Double.toString(heightVal));
        areaText.setText(Double.toString(areaVal));
        perimeterText.setText(Double.toString(perimeterVal));

        widthText.setOnEditorActionListener(this);
        heightText.setOnEditorActionListener(this);

    }

    @Override
    public void onClick(View view){

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            //TODO: Calculate the Rectangle
            widthVal = Double.parseDouble(widthText.getText().toString());
            heightVal = Double.parseDouble(heightText.getText().toString());
            Log.i("log", "Width, height : " + widthVal + " " + heightVal);
            calcArea(widthVal, heightVal);
            calcPerimeter(widthVal, heightVal);
        }
        return false;
    }

    private void calcArea(double width, double height){
        areaVal = width * height;
        areaText.setText(roundUp(areaVal));
    }

    private void calcPerimeter(double width, double height){
        perimeterVal = 2*width + 2*height;
        perimeterText.setText(roundUp(perimeterVal));
    }

    private String roundUp(double value){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }
}
