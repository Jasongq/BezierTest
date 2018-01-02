package com.johnnygq.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/01/02
 *     desc   : 三阶贝塞尔曲线
 *     version: 1.0
 * </pre>
 */
public class CubicToBezierView extends View {
    private Paint mPaintBezier;

    //辅助点
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;
    private float mAuxiliary1X;
    private float mAuxiliary1Y;

    private float mAuxiliary2X;
    private float mAuxiliary2Y;
    //起始点
    private float mStartPointX;
    private float mStartPointY;
    //结束点
    private float mEndPointX;
    private float mEndPointY;

    private boolean isSecondPoint = false;

    private Path mPath = new Path();

    public CubicToBezierView(Context context) {
        super(context);
    }

    public CubicToBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintView();
    }


    public CubicToBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaintView() {
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);//绘制时抗锯齿
        mPaintBezier.setStyle(Paint.Style.STROKE);//描边
        mPaintBezier.setStrokeWidth(8);


        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);//描边
        mPaintAuxiliary.setStrokeWidth(2);

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);//描边
        mPaintAuxiliaryText.setTextSize(14);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2 - 200;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);
        //辅助点
        canvas.drawPoint(mAuxiliary1X,mAuxiliary1Y,mPaintAuxiliary);
        canvas.drawText("控制点1",mAuxiliary1X,mAuxiliary1Y,mPaintAuxiliaryText);
        canvas.drawText("控制点2",mAuxiliary2X,mAuxiliary2Y,mPaintAuxiliaryText);
        canvas.drawText("起始点",mStartPointX,mStartPointY,mPaintAuxiliaryText);
        canvas.drawText("结束点",mEndPointX,mEndPointY,mPaintAuxiliaryText);
        //辅助线
        canvas.drawLine(mStartPointX,mStartPointY,mAuxiliary1X,mAuxiliary1Y,mPaintAuxiliary);
        canvas.drawLine(mAuxiliary1X,mAuxiliary1Y,mAuxiliary2X,mAuxiliary2Y,mPaintAuxiliary);
        canvas.drawLine(mEndPointX,mEndPointY,mAuxiliary2X,mAuxiliary2Y,mPaintAuxiliary);
        //三阶贝塞尔曲线
        mPath.cubicTo(mAuxiliary1X,mAuxiliary1Y,mAuxiliary2X,mAuxiliary2Y,mEndPointX,mEndPointY);//基于绝对坐标
//        mPath.cubicTo(mAuxiliary1X,mAuxiliary1Y,mAuxiliary2X,mAuxiliary2Y,mEndPointX,mEndPointY);//基于相对坐标
        canvas.drawPath(mPath,mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isSecondPoint=true;
                break;
            case MotionEvent.ACTION_MOVE:
                mAuxiliary1X = event.getX(0);
                mAuxiliary1Y = event.getY(0);
                if (isSecondPoint){
                    mAuxiliary2X = event.getX(1);
                    mAuxiliary2Y = event.getY(1);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSecondPoint=false;
                break;
        }
        return true;
    }
}
