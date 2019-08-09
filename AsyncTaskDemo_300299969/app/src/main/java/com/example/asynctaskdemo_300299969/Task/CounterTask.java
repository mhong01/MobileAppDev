package com.example.asynctaskdemo_300299969.Task;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * AsyncTask abstract class needs 3 arguments to initialize the object
 * when there is a class extends from it.
 * Argument 1: the input
 *              - This is the input received from the worker
 *              (in this case, the fragment Layout1)
 * Argument 2: the progress
 * Argument 3: the result
 *              - Since I want an Integer to be displayed on the UI thread,
 *              my result output will be type Integer.
 *
 */

public class CounterTask extends AsyncTask<Integer, Integer, Integer> {

    private TextView view = null;

    private String resultTxt = "";

    private boolean isExecuting = false;

    public void setTextView(TextView view ){
        this.view = view;
    }

    public String getResultTxt() {
        return resultTxt;
    }

    @Override
    protected Integer doInBackground(Integer... value) {

        int counter = 0;
        try{
            isExecuting = true;
            publishProgress(counter);
            for (int i = 0; i < 1000; i++){
                counter++;
                publishProgress(counter);
                Thread.sleep(1000);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        isExecuting = false;
        return counter;
    }


    @Override
    protected void onPreExecute() {
        Log.i("Task", "On PreExecute");
    }

    @Override
    protected void onPostExecute(Integer integer) {
        isExecuting = false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i("Progress", "Msg: " + values[0]);
        if (this.view != null){
            resultTxt = String.valueOf(values[0]);
            this.view.setText(resultTxt);
        }
    }

    public boolean isExecuting(){
        return isExecuting;
    }

}

