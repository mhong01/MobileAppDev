package com.example.asynctaskdemo_300299969;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Button msgBtn = null;
    private TextView msgDisplayTxt = null;
    private int counterFromLayout2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.msgBtn = (Button) findViewById(R.id.msgBtn);
        this.msgDisplayTxt = (TextView) findViewById(R.id.msgDisplayTxt);

        this.msgBtn.setOnClickListener(this);

        if (savedInstanceState != null
                && savedInstanceState.containsKey("msgDisplayTxt")){
            this.msgDisplayTxt.setText(savedInstanceState.getString("msgDisplayTxt"));
            this.counterFromLayout2 = savedInstanceState.getInt("counterFromLayout2");
        } else {
            this.msgDisplayTxt.setText("Click the button to show the attemps!");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.i("Main", "Re-attach frag");
        if (fragment.getRetainInstance()){

        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.i("Main", "Resume frag");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.msgBtn){
            counterFromLayout2++;
            msgDisplayTxt.setText("Attempt to click the button: "
                    + counterFromLayout2);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("msgDisplayTxt", msgDisplayTxt.getText().toString());
        outState.putInt("counterFromLayout2", counterFromLayout2);
    }
}
