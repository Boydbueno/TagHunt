package boyd.bueno.taghunt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Credits extends SurfaceView implements SurfaceHolder.Callback {

    private GraphThread thread;

    private Random rand = new Random();

    private int ballsCount = 300;

    private boolean isStarted = false;

    private List<Ball> balls = new ArrayList<Ball>();

    public Credits(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GraphThread(getHolder(), this);
    }

    public void onDraw(Canvas canvas) {

        if (!isStarted)
        {
            for(int i = 0; i < ballsCount; i++) {
                Paint randomPaintColor = new Paint();
                randomPaintColor.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                balls.add(new Ball(canvas, new Point(rand.nextInt(400), rand.nextInt(400)), new Point(rand.nextInt(10), rand.nextInt(10)), randomPaintColor));
            }
            isStarted = true;
        }

        for (Ball ball : balls) {
            ball.draw();
            ball.update();
        }

    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

}
