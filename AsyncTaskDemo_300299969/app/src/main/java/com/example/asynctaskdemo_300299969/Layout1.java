package com.example.asynctaskdemo_300299969;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asynctaskdemo_300299969.Task.CounterTask;

import java.util.concurrent.ExecutionException;


public class Layout1 extends Fragment {

    private int result = 0;
    private int initValue = 0;

    private String msg = "";

    private TextView counterMsg = null;
    private CounterTask task = null;

    private boolean isExecuting = false;

    public Layout1() {

    }

    interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute();
    }

    public static TaskCallbacks mCallbacks;


    public static Layout1 newInstance(String param1, String param2) {
        Layout1 fragment = new Layout1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout1 = inflater.inflate(R.layout.fragment_layout1, container, false);
        counterMsg = (TextView) layout1.findViewById(R.id.counterTxt);
        counterMsg.setText("Initialized Text on Fragment 1");
        initValue = 0;

        if (task != null){
            task.setTextView(null);
        }

        if (savedInstanceState != null
                && savedInstanceState.containsKey("counterMsg")
                && task != null){
            counterMsg.setText(savedInstanceState.getString("counterMsg"));
            task.setTextView(counterMsg);
        }

        return layout1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Frag", "On Start");
        initializeTask();
        if (task != null && !task.isExecuting()){
            task.setTextView(counterMsg);
            task.execute(initValue);
        }

    }

    public void initializeTask(){
        if (task == null){
            task = new CounterTask();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        counterMsg.setText(task.getResultTxt());
//        task.setTextView(counterMsg);
//        Log.i("Frag", "On Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counterMsg", counterMsg.getText().toString());
    }
}
