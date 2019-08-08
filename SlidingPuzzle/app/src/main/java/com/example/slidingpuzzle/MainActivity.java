package com.example.slidingpuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int X = 4;
    private static final int Y = 4;
    private static final int LENGTH = 16;
    public static final String MOVES_SO_FAR = "Moves so far: ";

    private int margin = 2;
    private int moveCount = 0;

    private ImageView[] imageViews = null;
    private List<Integer> drawableIds = null;

    private TextView textView = null;
    private TextView txtWinMsg = null;

    private Button btnNewGame = null;
    private Button btnSolve = null;

    SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            LinearLayout layoutPuzzle = (LinearLayout) findViewById(R.id.layoutPuzzle);
            initialize(layoutPuzzle);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initialize(LinearLayout view) throws Exception {
        if(view == null){
            return;
        }

        textView = (TextView) findViewById(R.id.textView);
        txtWinMsg = (TextView) findViewById(R.id.txtWinMessage);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnSolve = (Button) findViewById(R.id.btnSolve);

        btnNewGame.setOnClickListener(this);
        btnSolve.setOnClickListener(this);

        initializeButtons(view);
        initDrawableList();
        newGame(imageViews);
    }

    private void initializeButtons(LinearLayout view) throws Exception {
        imageViews = new ImageView[LENGTH];
        view.setWeightSum(X);
        int counter = 0;
        LinearLayout rowLayout = null;
        for (int i = 0; i < imageViews.length ; i++){
            if(counter == 0){
                rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setWeightSum(Y);
            }
            if (counter < X){
                counter++;
                imageViews[i] = initImageView(imageViews[i]);
                int id = i + 1 ;
                imageViews[i].setId(id);
                rowLayout.addView(imageViews[i]);
            }
            if (counter == X){
                view.addView(rowLayout);
                counter = 0;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("moveCountMsg", textView.getText().toString());
        outState.putInt("moveCount", moveCount);
        outState.putString("txtWinMessage", txtWinMsg.getText().toString());
        for (ImageView img : imageViews) {
            outState.putInt(String.valueOf(img.getId()), (Integer) img.getTag());
            outState.putBoolean(img.getId()+"isEnabled", img.isEnabled());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        textView.setText(savedInstanceState.getString("moveCountMsg"));
        moveCount = savedInstanceState.getInt("moveCount");
        txtWinMsg.setText(savedInstanceState.getString("txtWinMessage"));

        for(ImageView img : imageViews){
            img.setImageResource(savedInstanceState.getInt(String.valueOf(img.getId())));
            img.setTag(savedInstanceState.getInt(String.valueOf(img.getId())));
            img.setEnabled(savedInstanceState.getBoolean(img.getId()+"isEnabled"));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveState();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(MainActivity.class.getSimpleName(), "On Destroy");
        preferences = getPreferences();
        preferences.edit().clear();
    }

    private SharedPreferences getPreferences(){
        if (preferences == null){
            preferences = getSharedPreferences("slidingPuzzle", Context.MODE_PRIVATE);
        }
        return preferences;
    }

    private void saveState(){
        preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("moveCountMsg", textView.getText().toString());
        editor.putInt("moveCount", moveCount);
        editor.putString("txtWinMessage", txtWinMsg.getText().toString());
        for (ImageView img : imageViews) {
            editor.putInt(String.valueOf(img.getId()), (Integer) img.getTag());
            editor.putBoolean(img.getId()+"isEnabled", img.isEnabled());
        }
        editor.commit();
    }

    private void loadState(){
        preferences = getPreferences();
        textView.setText(preferences.getString("moveCountMsg", MOVES_SO_FAR));
        moveCount = preferences.getInt("moveCount", 0);
        txtWinMsg.setText(preferences.getString("txtWinMessage", ""));
        for(ImageView img : imageViews){
            img.setImageResource(preferences.getInt(String.valueOf(img.getId()), -1));
            img.setTag(preferences.getInt(String.valueOf(img.getId()), -1));
            img.setEnabled(preferences.getBoolean(img.getId()+"isEnabled", false));
        }
    }

    private void solveGame(ImageView[] imageViews){
        if (imageViews == null){
            return;
        }
        for (int i = 0; i < imageViews.length; i++){
            if (imageViews[i] == null){
                return;
            }
            imageViews[i].setImageResource(drawableIds.get(i));
            imageViews[i].setTag(drawableIds.get(i));
            imageViews[i].setEnabled(false);
        }
        txtWinMsg.setText("");
    }

    private void newGame(ImageView[] imageViews){
        if (imageViews == null){
            return;
        }
        moveCount = 0;
        textView.setText(MOVES_SO_FAR + moveCount);
        txtWinMsg.setText("");
        List randoms = new LinkedList<>();
        randoms = getRandomNumber(randoms,LENGTH);
        for (int i = 0; i < imageViews.length; i++){
            if (imageViews[i] == null){
                return;
            }
            imageViews[i].setImageResource(drawableIds.get((int) randoms.get(i)));
            imageViews[i].setTag(drawableIds.get((int) randoms.get(i)));
            imageViews[i].setEnabled(true);
        }
    }

    private List getRandomNumber(List randoms, int bound){
        int random = new Random().nextInt(bound);
        if(!randoms.contains(random)){
            randoms.add(random);
            if (randoms.size() >= LENGTH){
                return randoms;
            }
            getRandomNumber(randoms, bound);
        } else {
            if (randoms.size() >= LENGTH){
                return randoms;
            }
            getRandomNumber(randoms, bound);
        }
        return randoms;
    }

    private ImageView initImageView(ImageView imgV) throws Exception {
        if (imgV == null){
            imgV = new ImageView(this);
        }
//        imgV.setBackgroundColor(Color.YELLOW);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(margin, margin, margin, margin);
        imgV.setLayoutParams(params);
        imgV.setOnClickListener(this);
        return imgV;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNewGame){
            newGame(imageViews);
        } else if (v.getId() == R.id.btnSolve){
            solveGame(imageViews);
        } else {
            swapBlock(v);
            textView.setText(MOVES_SO_FAR + moveCount);
            if (checkWin()){
                txtWinMsg.setText("You won!");
            }
        }

    }

    private void swapBlock (View v){
        int currentBlock = v.getId();
        int prevBlock = -1 ;
        int nextBlock = -1;
        int aboveBlock = -1;
        int belowBlock = -1;
        if (currentBlock != 1
                && currentBlock != 5
                && currentBlock != 9
                && currentBlock != 13){
            prevBlock = currentBlock - 1;
        }
        if (currentBlock != 4
                && currentBlock != 8
                && currentBlock != 12
                && currentBlock != 16){
            nextBlock = currentBlock + 1;
        }
        if (currentBlock != 1
                && currentBlock != 2
                && currentBlock != 3
                && currentBlock != 4){
            aboveBlock = currentBlock - 4;
        }
        if (currentBlock != 13
                && currentBlock != 14
                && currentBlock != 15
                && currentBlock != 16){
            belowBlock = currentBlock + 4;
        }

        int blankBlock = getBlankBlock(prevBlock, nextBlock, aboveBlock, belowBlock);
        if (blankBlock != -1){
            swap(blankBlock, currentBlock);

        } else {
            Toast.makeText(this, "Unable to move!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getBlankBlock (int... blocks){
        int temp = -1;
        for (ImageView img : imageViews) {
            if (img.getTag().equals(R.drawable.blank)) {
                temp = img.getId();
            }
        }
        for (int i : blocks){
            if (i == temp){
                return i;
            }
        }
        return -1;
    }

    private void swap(int blankBlock, int currentBlock){
        moveCount++;

        ImageView currentImg = (ImageView) findViewById(currentBlock);
        ImageView blankImg = (ImageView) findViewById(blankBlock);

        Drawable temp = currentImg.getDrawable();
        Object tag = currentImg.getTag();

        currentImg.setImageDrawable(blankImg.getDrawable());
        currentImg.setTag(blankImg.getTag());

        blankImg.setImageDrawable(temp);
        blankImg.setTag(tag);
    }

    private void initDrawableList(){
        drawableIds = new LinkedList<>();
        drawableIds.add(R.drawable.img1);
        drawableIds.add(R.drawable.img2);
        drawableIds.add(R.drawable.img3);
        drawableIds.add(R.drawable.img4);
        drawableIds.add(R.drawable.img5);
        drawableIds.add(R.drawable.img6);
        drawableIds.add(R.drawable.img7);
        drawableIds.add(R.drawable.img8);
        drawableIds.add(R.drawable.img9);
        drawableIds.add(R.drawable.img10);
        drawableIds.add(R.drawable.img11);
        drawableIds.add(R.drawable.img12);
        drawableIds.add(R.drawable.img13);
        drawableIds.add(R.drawable.img14);
        drawableIds.add(R.drawable.img15);
        drawableIds.add(R.drawable.blank);
    }

    private boolean checkWin(){
        for (int i = 0; i < imageViews.length; i++){
            if (!imageViews[i].getTag().equals(drawableIds.get(i))) {
                return false;
            }
        }
        return true;
    }
}
