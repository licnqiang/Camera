package ohos.samples.camera;

import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;

/**
 * 圆形按钮
 */
public class TypeButton extends Component implements Component.DrawTask {
    /**
     * TYPE CANCEL
     */
    public static final int TYPE_CANCEL = 0x001;

    /**
     * TYPE CONFIRM
     */
    public static final int TYPE_CONFIRM = 0x002;
    private int mButton_type;
    private int mButton_size;

    private float mCenter_X;
    private float mCenter_Y;
    private float mButton_radius;

    private Paint mPaint;
    private Path mPath;
    private float mStrokeWidth;

    private float mIndex;
    private RectFloat mRectF;

    public TypeButton(Context context) {
        super(context);
    }

    public TypeButton(Context context, int type, int size) {
        super(context);
        this.mButton_type = type;
        mButton_size = size;
        mButton_radius = size / 2.0f;
        mCenter_X = size / 2.0f;
        mCenter_Y = size / 2.0f;

        mPaint = new Paint();
        mPath = new Path();
        mStrokeWidth = size / 50f;
        mIndex = mButton_size / 12f;
        mRectF = new RectFloat(mCenter_X, mCenter_Y - mIndex, mCenter_X + mIndex * 2, mCenter_Y + mIndex);
        addDrawTask(this);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        // 如果类型为取消，则绘制内部为返回箭头
        if (mButton_type == TYPE_CANCEL) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(new Color(0xEEDCDCDC));

            mPaint.setStyle(Paint.Style.FILL_STYLE);
            canvas.drawCircle(mCenter_X, mCenter_Y, mButton_radius, mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE_STYLE);
            mPaint.setStrokeWidth(mStrokeWidth);

            mPath.moveTo(mCenter_X - mIndex / 7, mCenter_Y + mIndex);
            mPath.lineTo(mCenter_X + mIndex, mCenter_Y + mIndex);

            mPath.arcTo(mRectF, 90, -180);
            mPath.lineTo(mCenter_X - mIndex, mCenter_Y - mIndex);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.FILL_STYLE);
            mPath.reset();
            mPath.moveTo(mCenter_X - mIndex, (float) (mCenter_Y - mIndex * 1.5));
            mPath.lineTo(mCenter_X - mIndex, (float) (mCenter_Y - mIndex / 2.3));
            mPath.lineTo((float) (mCenter_X - mIndex * 1.6), mCenter_Y - mIndex);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
        // 如果类型为确认，则绘制绿色勾
        if (mButton_type == TYPE_CONFIRM) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(new Color(0xFFFFFFFF));
            mPaint.setStyle(Paint.Style.FILL_STYLE);
            canvas.drawCircle(mCenter_X, mCenter_Y, mButton_radius, mPaint);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE_STYLE);
            mPaint.setColor(new Color(0xFF00CC00));
            mPaint.setStrokeWidth(mStrokeWidth);

            mPath.moveTo(mCenter_X - mButton_size / 6f, mCenter_Y);
            mPath.lineTo(mCenter_X - mButton_size / 21.2f, mCenter_Y + mButton_size / 7.7f);
            mPath.lineTo(mCenter_X + mButton_size / 4.0f, mCenter_Y - mButton_size / 8.5f);
            mPath.lineTo(mCenter_X - mButton_size / 21.2f, mCenter_Y + mButton_size / 9.4f);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }
}
