package com.example.slidingpuzzle;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int X = 4;
    private final int Y = 4;

    private int margin = 2;
    private int moveCount = 0;

    private ImageView[] imageViews = null;

    private TextView textView = null;

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
        imageViews = new ImageView[X*Y];
        view.setWeightSum(X);
        int counter = 0;
        LinearLayout rowLayout = null;
        for (int i = 0; i < imageViews.length ; i++){
            if(counter == 0){
                rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            }
            if (counter < 4){
                counter++;
                imageViews[i] = initImageView(imageViews[i]);
                int id = i + 1 ;
                imageViews[i].setId(id);
                rowLayout.setWeightSum(Y);
                rowLayout.addView(imageViews[i]);
            }

            if (counter == 4){
                view.addView(rowLayout);
                counter = 0;
            }
        }
        newGame(imageViews);
    }

    private void solveGame(ImageView[] imageViews){
        if (imageViews == null){
            return;
        }
        int counter = 0;
        for (int i = 0; i < imageViews.length; i++){
            if (imageViews[i] == null){
                return;
            }
            counter++;
            imageViews[i].setImageResource(getDrawableResource(counter));
            imageViews[i].setTag(getDrawableResource(counter));
        }
    }

    private void newGame(ImageView[] imageViews){
        moveCount = 0;
        textView.setText("Moves so far: ");
        if (imageViews == null){
            return;
        }
        int counter = 0;
        int[] randoms = new int[16];

        for (int i = 0; i < imageViews.length; i++){
            if (imageViews[i] == null){
                return;
            }
            counter++;
            int random = new Random().nextInt(16);
            for (int x : randoms){
                if (i == x);
                
            }
            imageViews[i].setImageResource(getDrawableResource(random));
            imageViews[i].setTag(getDrawableResource(random));
        }
    }

    private int getDrawableResource(int counter){
        switch (counter){
            case 1: return R.drawable.img1;
            case 2: return R.drawable.img2;
            case 3: return R.drawable.img3;
            case 4: return R.drawable.img4;
            case 5: return R.drawable.img5;
            case 6: return R.drawable.img6;
            case 7: return R.drawable.img7;
            case 8: return R.drawable.img8;
            case 9: return R.drawable.img9;
            case 10: return R.drawable.img10;
            case 11: return R.drawable.img11;
            case 12: return R.drawable.img12;
            case 13: return R.drawable.img13;
            case 14: return R.drawable.img14;
            case 15: return R.drawable.img15;
//            case 16: return R.drawable.img16;
            default: return R.drawable.blank;
        }
    }

    private ImageView initImageView(ImageView imgV) throws Exception {
        if (imgV == null){
            imgV = new ImageView(this);
        }
        imgV.setBackgroundColor(Color.YELLOW);
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
        moveCount++;
        textView.setText("Moves so far: " + moveCount);
        swapBlock(v);
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
        ImageView currentImg = (ImageView) findViewById(currentBlock);
        ImageView blankImg = (ImageView) findViewById(blankBlock);

        Drawable temp = currentImg.getDrawable();
        Object tag = currentImg.getTag();

        currentImg.setImageDrawable(blankImg.getDrawable());
        currentImg.setTag(blankImg.getTag());

        blankImg.setImageDrawable(temp);
        blankImg.setTag(tag);
    }
}
