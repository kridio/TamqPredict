package tw.gov.epa.taqmpredict.view;

/**
 * Created by user on 2017/2/13.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import tw.gov.epa.taqmpredict.R;

public class CircularPercentageView extends View {
    private Paint paint;
    private int circleWidth;
    private int roundBackgroundColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private float progress = 0;
    private int[] colors = {0xffff4639, 0xffCDD513, 0xff3CDF5F};
    private int radius;
    private RectF oval;
    private Paint mPaintText;
    private int maxColorNumber = 100;
    private float singlPoint = 9;
    private float lineWidth = 0.3f;
    private int circleCenter;
    private SweepGradient sweepGradient;
    private boolean isLine;

    /**
     * 分割的數量
     *
     * @param maxColorNumber 數量
     */
    public void setMaxColorNumber(int maxColorNumber) {
        this.maxColorNumber = maxColorNumber;
        singlPoint = (float) 360 / (float) maxColorNumber;
        invalidate();
    }

    /**
     * 是否是線條
     *
     * @param line true 是 false否
     */
    public void setLine(boolean line) {
        isLine = line;
        invalidate();
    }

    public int getCircleWidth() {
        return circleWidth;
    }

    public CircularPercentageView(Context context) {
        this(context, null);
    }

    public CircularPercentageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircularPercentageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularRing);
        maxColorNumber = mTypedArray.getInt(R.styleable.CircularRing_circleNumber, 40);
        circleWidth = mTypedArray.getDimensionPixelOffset(R.styleable.CircularRing_circleWidth, getDpValue(180));
        roundBackgroundColor = mTypedArray.getColor(R.styleable.CircularRing_roundColor, 0xffdddddd);
        textColor = mTypedArray.getColor(R.styleable.CircularRing_circleTextColor, 0xff999999);
        roundWidth = mTypedArray.getDimension(R.styleable.CircularRing_circleRoundWidth, 40);
        textSize = mTypedArray.getDimension(R.styleable.CircularRing_circleTextSize, getDpValue(8));
        colors[0] = mTypedArray.getColor(R.styleable.CircularRing_circleColor1, 0xffff4639);
        colors[1] = mTypedArray.getColor(R.styleable.CircularRing_circleColor2, 0xffcdd513);
        colors[2] = mTypedArray.getColor(R.styleable.CircularRing_circleColor3, 0xff3cdf5f);
        initView();
        mTypedArray.recycle();
    }


    /**
     * 空白出颜色背景
     *
     * @param roundBackgroundColor
     */
    public void setRoundBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
        paint.setColor(roundBackgroundColor);
        invalidate();
    }

    /**
     * 刻度字體颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        mPaintText.setColor(textColor);
        invalidate();
    }

    /**
     * 刻度字體大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaintText.setTextSize(textSize);
        invalidate();
    }

    /**
     * 漸變顏色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("colors length < 2");
        }
        this.colors = colors;
        sweepGradientInit();
        invalidate();
    }


    /**
     * 間隔角度大小
     *
     * @param lineWidth
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }


    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 圓環寬度
     *
     * @param roundWidth 寬度
     */
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
        if (roundWidth > circleCenter) {
            this.roundWidth = circleCenter;
        }
        radius = (int) (circleCenter - this.roundWidth / 2); // 圓環的半徑
        oval.left = circleCenter - radius;
        oval.right = circleCenter + radius;
        oval.bottom = circleCenter + radius;
        oval.top = circleCenter - radius;
        paint.setStrokeWidth(this.roundWidth);
        invalidate();
    }

    /**
     * 圓環的直徑
     *
     * @param circleWidth 直徑
     */
    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
        circleCenter = circleWidth / 2;

        if (roundWidth > circleCenter) {
            roundWidth = circleCenter;
        }
        setRoundWidth(roundWidth);
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋轉 不然是從0度開始漸變
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }

    /**
     * 漸變初始化
     */
    public void sweepGradientInit() {
        //漸變顏色
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋轉 不然是從0度開始漸變
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }

    public void initView() {
        circleCenter = circleWidth / 2;//半徑
        singlPoint = (float) 360 / (float) maxColorNumber;
        radius = (int) (circleCenter - roundWidth / 2); // 圓環的半徑
        sweepGradientInit();
        mPaintText = new Paint();
        mPaintText.setColor(textColor);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(textSize);
        mPaintText.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(roundBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);

        // 用於定義的圓弧的形狀和大小的界限
        oval = new RectF(circleCenter - radius, circleCenter - radius, circleCenter + radius, circleCenter + radius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //背景漸變顏色
        paint.setShader(sweepGradient);
        canvas.drawArc(oval, -90, (float) (progress * 3.6), false, paint);
        paint.setShader(null);

        //是否是線條模式
        if (!isLine) {
            float start = -90f;
            float p = ((float) maxColorNumber / (float) 100);
            p = (int) (progress * p);
            for (int i = 0; i < p; i++) {
                paint.setColor(roundBackgroundColor);
                canvas.drawArc(oval, start + singlPoint - lineWidth, lineWidth, false, paint); // 绘制间隔快
                start = (start + singlPoint);
            }
        }
        //繪製剩下的空白區域
        paint.setColor(roundBackgroundColor);
        canvas.drawArc(oval, -90, (float) (-(100 - progress) * 3.6), false, paint);

        //繪製文字刻度
        for (int i = 1; i <= 10; i++) {
            canvas.save();//保存當前畫布
            canvas.rotate(360 / 10 * i, circleCenter, circleCenter);
            canvas.drawText(i * 10 + "", circleCenter, circleCenter - radius + roundWidth / 2 + getDpValue(4) + textSize, mPaintText);
            canvas.restore();//
        }
    }


    OnProgressScore onProgressScore;

    public interface OnProgressScore {
        void setProgressScore(float score);

    }

    public synchronized void setProgress(final float p) {
        progress = p;
        postInvalidate();
    }

    /**
     * @param p
     */
    public synchronized void setProgress(final float p, OnProgressScore onProgressScore) {
        this.onProgressScore = onProgressScore;
        progress = p;
        postInvalidate();
    }

}
