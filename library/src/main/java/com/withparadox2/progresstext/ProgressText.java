package com.withparadox2.progresstext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by withparadox2 on 2014/8/4.
 */
public class ProgressText extends TextView {
    private int strokeColor;
    private int beforeProgressColor;
    private int afterProgressColor;
    private int strokeWidth;
    private BitmapShader shader;
    private Matrix matrix;

    private int default_before_progress_color = Color.CYAN;
    private int default_after_progress_color = Color.GREEN;
    private int default_stroke_color = Color.BLACK;
    private int default_stroke_width = 1;

    private float initialProcessPercentage = -1f;

    public ProgressText(Context context) {
        this(context, null, 0);
    }

    public ProgressText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressText, defStyle, 0);
        beforeProgressColor = typedArray.getColor(R.styleable.ProgressText_before_progress_color, default_before_progress_color);
        afterProgressColor = typedArray.getColor(R.styleable.ProgressText_after_progress_color, default_after_progress_color);
        strokeColor = typedArray.getColor(R.styleable.ProgressText_stroke_color, default_stroke_color);
        strokeWidth = (int) typedArray.getDimension(R.styleable.ProgressText_stroke_width, default_stroke_width);
        typedArray.recycle();

        matrix = new Matrix();
        shader = new BitmapShader(getShaderBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        shader.setLocalMatrix(matrix);
    }

    public void setBeforeProgressColor (int color) {
        this.beforeProgressColor = color;
        updateShader();
    }

    public void setAfterProgressColor (int color) {
        this.afterProgressColor = color;
        updateShader();
    }

    private void updateShader () {
        shader = new BitmapShader(getShaderBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        shader.setLocalMatrix(matrix);
        invalidate();
    }

    private Bitmap getShaderBitmap () {
        Bitmap bitmap = Bitmap.createBitmap(2, 1, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(beforeProgressColor);
        canvas.drawPoint(0.5f, 0.5f, paint);
        paint.setColor(afterProgressColor);
        canvas.drawPoint(1.5f, 0.5f, paint);
        return bitmap;
    }

    public void setProgressByPixels (int pixels) {
        matrix.setTranslate(pixels, 0);
        shader.setLocalMatrix(matrix);
        invalidate();
    }

    public void setProgressBypercentage (float percentage) {
        if (getWidth() == 0) {
            initialProcessPercentage = percentage;
        } else {
            setProgressByPixels((int) (getMeasuredWidth() * percentage));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (initialProcessPercentage > -1f) {
            matrix.setTranslate(initialProcessPercentage*getWidth(), 0);
            shader.setLocalMatrix(matrix);
            initialProcessPercentage = -1;
        }
        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setShader(shader);
        super.onDraw(canvas);

        getPaint().setShader(null);
        setTextColor(strokeColor);
        getPaint().setStrokeWidth(strokeWidth);
        getPaint().setStyle(Paint.Style.STROKE);
        super.onDraw(canvas);
    }
}
