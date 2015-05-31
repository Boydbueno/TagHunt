package boyd.bueno.taghunt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball {
    private Canvas canvas;
    private Point position;
    private Point speed;
    private Paint color;

    public Ball(Canvas canvas, Point position, Point speed, Paint color) {
        this.canvas = canvas;
        this.position = position;
        this.speed = speed;
        this.color = color;
    }

    public void draw() {
        canvas.drawCircle(position.x, position.y, 10, color);
    }

    public void update() {
        position.set(position.x + speed.x, position.y + speed.y);
    }

}
