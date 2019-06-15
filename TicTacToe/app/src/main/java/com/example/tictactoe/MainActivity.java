package com.example.tictactoe;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn1 = null;
    Button btn2 = null;
    Button btn3 = null;
    Button btn4 = null;
    Button btn5 = null;
    Button btn6 = null;
    Button btn7 = null;
    Button btn8 = null;
    Button btn9 = null;

    Button btnNewGame = null;

    TextView winner = null;

    boolean isXPlayer = true;

    final String X_PLAYER = "X";
    final String O_PLAYER = "O";
    final String EMPTY_TEXT = "";

    List<Button> buttons = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Put buttons label
        for (Button b : buttons){
            outState.putString(Integer.toString(b.getId()), b.getText().toString());
        }
        outState.putBoolean("isXPlayer", isXPlayer);
        outState.putInt("isWinner", isWinner());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Retrieve button labels
        for(Button b : buttons){
            b.setText(savedInstanceState.getString(Integer.toString(b.getId())));
        }

        isXPlayer = savedInstanceState.getBoolean("isXPlayer");
        int isWinner = savedInstanceState.getInt("isWinner");
        setMessage();
        checkWinner(isWinner);

    }

    private void init (){
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        winner = (TextView) findViewById(R.id.lblMessage);

        buttons = new ArrayList<>();
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);

        for (Button b : buttons){
            b.setText(EMPTY_TEXT);
            b.setOnClickListener(this);
        }
        btnNewGame.setOnClickListener(this);
        setMessage();
    }

    private void setMessage() {
        if (isXPlayer){
            winner.setText("Player X's turn");
        } else {
            winner.setText("Player O's turn");
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnNewGame){
            for (Button b : buttons){
                b.setClickable(true);
                b.setText(EMPTY_TEXT);
                isXPlayer = true;
                setMessage();
            }
            return;
        }

        if (isXPlayer){
            switch (v.getId()){
                case R.id.btn1:
                    btn1.setText(X_PLAYER);
                    break;
                case R.id.btn2:
                    btn2.setText(X_PLAYER);
                    break;
                case R.id.btn3:
                    btn3.setText(X_PLAYER);
                    break;
                case R.id.btn4:
                    btn4.setText(X_PLAYER);
                    break;
                case R.id.btn5:
                    btn5.setText(X_PLAYER);
                    break;
                case R.id.btn6:
                    btn6.setText(X_PLAYER);
                    break;
                case R.id.btn7:
                    btn7.setText(X_PLAYER);
                    break;
                case R.id.btn8:
                    btn8.setText(X_PLAYER);
                    break;
                case R.id.btn9:
                    btn9.setText(X_PLAYER);
                    break;
            }
            isXPlayer = false;
        } else {
            switch (v.getId()){
                case R.id.btn1:
                    btn1.setText(O_PLAYER);
                    break;
                case R.id.btn2:
                    btn2.setText(O_PLAYER);
                    break;
                case R.id.btn3:
                    btn3.setText(O_PLAYER);
                    break;
                case R.id.btn4:
                    btn4.setText(O_PLAYER);
                    break;
                case R.id.btn5:
                    btn5.setText(O_PLAYER);
                    break;
                case R.id.btn6:
                    btn6.setText(O_PLAYER);
                    break;
                case R.id.btn7:
                    btn7.setText(O_PLAYER);
                    break;
                case R.id.btn8:
                    btn8.setText(O_PLAYER);
                    break;
                case R.id.btn9:
                    btn9.setText(O_PLAYER);
                    break;
            }
            isXPlayer = true;
        }
        setMessage();
        checkWinner(isWinner());
    }

    private void checkWinner( int isWinner){
        if (isWinner == 1){
            if (!isXPlayer){
                winner.setText("X wins!");
            } else {
                winner.setText("O wins!");
            }
            for (Button b : buttons){
                b.setClickable(false);
            }
        } else if (isWinner == 3){
            winner.setText("The game is tie");
            for (Button b : buttons){
                b.setClickable(false);
            }
        }
    }

    private int isWinner(){
       if (isTied()){
           return 3;
       } else {
           return getWinner();
       }
    }

    private int getWinner (){
//        Horizontal win
        if (checkString(btn1.getText().toString(), btn2.getText().toString())
                && checkString(btn1.getText().toString(), btn3.getText().toString())){
            return 1;
        }

        if (checkString(btn4.getText().toString(), btn5.getText().toString())
                && checkString(btn4.getText().toString(), btn6.getText().toString())){
            return 1;
        }
        if (checkString(btn7.getText().toString(), btn8.getText().toString())
                && checkString(btn7.getText().toString(), btn9.getText().toString())){
            return 1;
        }
//        Vertical win
        if (checkString(btn1.getText().toString(), btn4.getText().toString())
                && checkString(btn1.getText().toString(), btn7.getText().toString())){
            return 1;
        }

        if (checkString(btn2.getText().toString(), btn5.getText().toString())
                && checkString(btn2.getText().toString(), btn8.getText().toString())){
            return 1;
        }

        if (checkString(btn3.getText().toString(), btn6.getText().toString())
                && checkString(btn3.getText().toString(), btn9.getText().toString())){
            return 1;
        }
//        Diagonal win
        if (checkString(btn1.getText().toString(), btn5.getText().toString())
                && checkString(btn1.getText().toString(), btn9.getText().toString())){
            return 1;
        }
        if (checkString(btn3.getText().toString(), btn5.getText().toString())
                && checkString(btn3.getText().toString(), btn7.getText().toString())){
            return 1;
        }

        return 2;
    }

    private boolean checkString(String s1, String s2){
        if (s1!= null && s2!= null
            && !s1.isEmpty() && !s2.isEmpty()
            && (s1.equals(s2))){
            return true;
        }
        return false;
    }

    private boolean isTied (){
        if (!isEmptyButton() && getWinner() == 2){
            return true;
        }
        return false;
    }

    private boolean isEmptyButton(){
        for (Button b : buttons){
            String str = b.getText().toString();
           if (str == null || str.isEmpty()){
               return true;
           }
        }
        return false;
    }
}
