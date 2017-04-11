package com.szchangliang.htmlfivewebview.ui.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.szchangliang.htmlfivewebview.R;


/**
 * Created by Ktoy on 16/6/20.
 */
public class VDHLayout extends FrameLayout {
    private ViewDragHelper mDragger;

    //    private View mDragView;
    private View mAutoBackView;
//    private View mEdgeTrackerView;

//    private Point mAutoBackOriginPos = new Point();


    int widthPixels;
    int widthPixelsHalf;


    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        widthPixelsHalf = widthPixels / 2;

        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //mEdgeTrackerView禁止直接移动
                //是否可以移动
                return child == mAutoBackView;
//                return child == mEdgeTrackerView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }


            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == mAutoBackView) {
                    int half = mAutoBackView.getWidth() / 2;
                    int bottom =getHeight()-mAutoBackView.getHeight()-10;
//                    WeLog.e("height:"+getHeight()+  "   :"+getBottom());
//                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);

                    int x = 0;
                    int y = 0;

//                    WeLog.e("bottom:"+bottom +"   getTop:"+mAutoBackView.getTop()+" heightPixels:"+getHeight()+"  getHeight:"+mAutoBackView.getHeight());

                    if (widthPixelsHalf - half > mAutoBackView.getLeft()) {
                        x =10;
                    } else {
                        x = widthPixels -mAutoBackView.getWidth()-10;
                    }

                    if(10>mAutoBackView.getTop()){
                        y=10;
                    }else if(bottom<mAutoBackView.getTop()){
                        y=bottom;
                    }else{
                        y= mAutoBackView.getTop();
                    }


                    mDragger.settleCapturedViewAt(x, y);
                    invalidate();
                }
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
//                super.onEdgeTouched(edgeFlags, pointerId);
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }
        });
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//
//        mAutoBackOriginPos.x = mAutoBackView.getLeft();
//        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        mDragView = getChildAt(0);
        //region because unuse this finish butoon
        mAutoBackView = findViewById(R.id.id_menu_btn);
        LayoutParams layoutParams = (LayoutParams)mAutoBackView.getLayoutParams();
        layoutParams.gravity= Gravity.BOTTOM| Gravity.RIGHT;
        layoutParams.setMargins(10,10,10,10);
        mAutoBackView.setLayoutParams(layoutParams);
        //endregion because unuse this finish butoon
//        mEdgeTrackerView = getChildAt(2);
    }
}
