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
 *     desc   : 二阶贝塞尔曲线
 *     version: 1.0
 * </pre>
 */
public class QuadToBezierView extends View {
    private Paint mPaintBezier;

    //辅助点
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;
    private float mAuxiliaryX;
    private float mAuxiliaryY;
    //起始点
    private float mStartPointX;
    private float mStartPointY;
    //结束点
    private float mEndPointX;
    private float mEndPointY;

    private Path mPath = new Path();

    public QuadToBezierView(Context context) {
        super(context);
    }

    public QuadToBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintView();
    }


    public QuadToBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        canvas.drawPoint(mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
        canvas.drawText("控制点",mAuxiliaryX,mAuxiliaryY,mPaintAuxiliaryText);
        canvas.drawText("起始点",mStartPointX,mStartPointY,mPaintAuxiliaryText);
        canvas.drawText("结束点",mEndPointX,mEndPointY,mPaintAuxiliaryText);
        //辅助线
        canvas.drawLine(mStartPointX,mStartPointY,mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
        canvas.drawLine(mEndPointX,mEndPointY,mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
        //二阶贝塞尔曲线
        mPath.quadTo(mAuxiliaryX,mAuxiliaryY,mEndPointX,mEndPointY);//基于绝对坐标
//        mPath.rQuadTo(mAuxiliaryX,mAuxiliaryY,mEndPointX,mEndPointY);//基于相对坐标
        canvas.drawPath(mPath,mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mAuxiliaryX = event.getX();
                mAuxiliaryY = event.getY();
                invalidate();
        }
        return true;
    }
}
