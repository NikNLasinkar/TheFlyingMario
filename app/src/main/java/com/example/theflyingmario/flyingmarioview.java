package com.example.theflyingmario;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyingmarioview extends View
{

    private Bitmap mario[] = new Bitmap[2];
    private int marioX = 10;
    private int marioY;
    private int marioSpeed;

    private int canvasWidth,canvasHeight;

    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    private int score, lifeCounterOfmario;

    private boolean touch = false;

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    public flyingmarioview(Context context)
    {
        super(context);

        mario[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mario1);
        mario[1] = BitmapFactory.decodeResource(getResources(), R.drawable.mario2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(60);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        marioY = 550;
        score = 0;
        lifeCounterOfmario = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();



        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY = mario[0].getHeight();
        int maxFishY = canvasHeight - mario[0].getHeight() * 3;
        marioY = marioY + marioSpeed;

        if (marioY < minFishY)
        {
            marioY = minFishY;
        }
        if (marioY > maxFishY)
        {
            marioY = maxFishY;
        }
        marioSpeed = marioSpeed + 2;
        if(touch)
        {
            canvas.drawBitmap(mario[1],marioX,marioY,null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(mario[0], marioX, marioY,null);
        }

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX,yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }
        canvas.drawCircle(yellowX, yellowY,25,yellowPaint);


        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX,greenY))
        {
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int)Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }
        canvas.drawCircle(greenX, greenY,25,greenPaint);

        redX = redX - redSpeed;

        if (hitBallChecker(redX,redY))
        {
            redX = -100;
            lifeCounterOfmario--;

            if(lifeCounterOfmario == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                gameOverIntent.putExtra("score",score);

                getContext().startActivity(gameOverIntent);

            }
        }

        if (redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }
        canvas.drawCircle(redX, redY,35,redPaint);

        canvas.drawText("Score : " + score,20,60,scorePaint);


        for(int i=0; i<3; i++)
        {
            int x = (int) (380 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < lifeCounterOfmario)
            {
                canvas.drawBitmap(life[0] , x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1] , x,y,null);
            }
        }






    }

    public boolean hitBallChecker(int x, int y)
    {
        if (marioX < x && x < (marioX + mario[0].getWidth())  && marioY < y && y < (marioY + mario[0].getHeight()))
        {
            return true;
        }
        return false;
    }






    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;

            marioSpeed = -22;
        }
        return true;
    }
}
