package app.capgo.capacitor.camera.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Draws corner brackets (targeting frame) on top of the camera preview.
 * Used for QR code scanning to show the user where to aim.
 */
public class TargetingFrameView extends View {

    private final Paint bracketPaint;
    private static final float BRACKET_LENGTH_DP = 24f;
    private static final float BRACKET_STROKE_DP = 3f;
    private static final float COVERAGE = 0.7f;

    public TargetingFrameView(Context context) {
        super(context);
        bracketPaint = new Paint();
        bracketPaint.setColor(Color.WHITE);
        bracketPaint.setStyle(Paint.Style.STROKE);
        bracketPaint.setStrokeCap(Paint.Cap.ROUND);
        bracketPaint.setAntiAlias(true);
        bracketPaint.setShadowLayer(8f, 0f, 0f, 0x80000000);
        float density = context.getResources().getDisplayMetrics().density;
        bracketPaint.setStrokeWidth(BRACKET_STROKE_DP * density);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0) return;

        // Target rect is COVERAGE of the smaller dimension, centered
        float size = Math.min(w, h) * COVERAGE;
        float left = (w - size) / 2f;
        float top = (h - size) / 2f;
        float right = left + size;
        float bottom = top + size;

        float density = getContext().getResources().getDisplayMetrics().density;
        float len = BRACKET_LENGTH_DP * density;

        // Top-left bracket
        canvas.drawLine(left, top, left + len, top, bracketPaint);
        canvas.drawLine(left, top, left, top + len, bracketPaint);
        // Top-right bracket
        canvas.drawLine(right, top, right - len, top, bracketPaint);
        canvas.drawLine(right, top, right, top + len, bracketPaint);
        // Bottom-left bracket
        canvas.drawLine(left, bottom, left + len, bottom, bracketPaint);
        canvas.drawLine(left, bottom, left, bottom - len, bracketPaint);
        // Bottom-right bracket
        canvas.drawLine(right, bottom, right - len, bottom, bracketPaint);
        canvas.drawLine(right, bottom, right, bottom - len, bracketPaint);
    }
}
