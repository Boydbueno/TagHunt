package boyd.bueno.taghunt;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Bas, I 'borrowed' your GraphThread, I hope you don't mind
 */
public class GraphThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Credits credits;
    private boolean _run = false;

    public GraphThread(SurfaceHolder surfaceHolder, Credits panel) {
        this.surfaceHolder = surfaceHolder;
        credits = panel;
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public void setRunning(boolean run) {
        _run = run;
    }

    @Override
    public void run() {
        Canvas c;
        while (_run) {
            c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    credits.onDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}