package com.wuxiaolong.wewin.draglayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;
import com.xiaomolongstudio.wewin.R;

public class DragLayout extends FrameLayout {

    private boolean isShowShadow = true;

    private GestureDetectorCompat mGestureDetectorCompat;
    private ViewDragHelper mViewDragHelper;
    private DragListener mDragListener;
    private int mDragRange;
    private int mMenuWidth;
    private int mMenuHeight;
    private int mMainLeftRange;
    private Context context;
    private ImageView iv_shadow;
    private RelativeLayout mMenuLayout;
    private MainLayout mMainLayout;
    private Status mStatus = Status.Close;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetectorCompat = new GestureDetectorCompat(context, new MyOnGestureListener());//处理手势识别
        mViewDragHelper = ViewDragHelper.create(this, dragHelperCallback);
    }

    class MyOnGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            return Math.abs(dy) <= Math.abs(dx);
        }
    }

    private ViewDragHelper.Callback dragHelperCallback = new ViewDragHelper.Callback() {

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mMainLeftRange + dx < 0) {
//                Log.d("wxl", "当前菜单关闭，还在向左滑动");
                return 0;
            } else if (mMainLeftRange + dx > mDragRange) {
//                Log.d("wxl", "当前菜单打开，还在向右滑动");
                return mDragRange;
            } else {
//                Log.d("wxl", "滑动中");
                return left;
            }
        }

        //何时开始检测触摸事件
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainLayout == child;//如果当前child是mMainLayout时开始检测
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mMenuWidth;
        }

        //拖动结束后调用
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
//            Log.d("wxl", "onViewReleased=mMainLeftRange=" + mMainLeftRange + ",mDragRange==" + mDragRange * 0.3 + ",xvel=" + xvel + ",yvel=" + yvel);
            if (releasedChild == mMainLayout && xvel > 0 && mMainLeftRange > mDragRange * 0.3) {
                open();
            } else {
                close();
            }
        }

        //位置改变时回调，常用于滑动是更改scale进行缩放等效果
        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if (changedView == mMainLayout) {
                mMainLeftRange = left;
            } else {
                mMainLeftRange = mMainLeftRange + left;
            }
            if (mMainLeftRange < 0) {
                mMainLeftRange = 0;
            } else if (mMainLeftRange > mDragRange) {
                mMainLeftRange = mDragRange;
            }

            if (isShowShadow) {
                iv_shadow.layout(mMainLeftRange, 0, mMainLeftRange + mMenuWidth, mMenuHeight);
            }
            if (changedView == mMenuLayout) {
                mMenuLayout.layout(0, 0, mMenuWidth, mMenuHeight);
                mMainLayout.layout(mMainLeftRange, 0, mMainLeftRange + mMenuWidth, mMenuHeight);
            }

            dragEvent(mMainLeftRange);
        }
    };

    /**
     * 滑动时松手后以一定速率继续自动滑动下去并逐渐停止，类似于扔东西或者松手后自动滑动到指定位置
     */
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public interface DragListener {
        public void onOpen();

        public void onClose();

        public void onDrag(float percent);
    }

    public void setmDragListener(DragListener mDragListener) {
        this.mDragListener = mDragListener;
    }

    /**
     * 加载完布局文件后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isShowShadow) {
            iv_shadow = new ImageView(context);
            iv_shadow.setImageResource(R.drawable.shadow);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(iv_shadow, 1, lp);
        }
        mMenuLayout = (RelativeLayout) getChildAt(0);
        mMainLayout = (MainLayout) getChildAt(isShowShadow ? 2 : 1);
        mMainLayout.setDragLayout(this);
        mMenuLayout.setClickable(true);
        mMainLayout.setClickable(true);
    }

    /**
     * 获取View宽度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMenuWidth = mMenuLayout.getMeasuredWidth();
        mMenuHeight = mMenuLayout.getMeasuredHeight();
        mDragRange = (int) (mMenuWidth * 0.6f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mMenuLayout.layout(0, 0, mMenuWidth, mMenuHeight);
        mMainLayout.layout(mMainLeftRange, 0, mMainLeftRange + mMenuWidth, mMenuHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev) && mGestureDetectorCompat.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        try {
            mViewDragHelper.processTouchEvent(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void dragEvent(int mainLeft) {
        if (mDragListener == null) {
            return;
        }
        float percent = mainLeft / (float) mDragRange;
        animateView(percent);
        Status lastStatus = mStatus;
        if (lastStatus != getmStatus() && mStatus == Status.Close) {
            mDragListener.onClose();
        } else if (lastStatus != getmStatus() && mStatus == Status.Open) {
            mDragListener.onOpen();
        } else {
            mDragListener.onDrag(percent);
        }
    }


    private void animateView(float percent) {
        float f1 = 1 - percent * 0.3f;
        ViewHelper.setScaleX(mMainLayout, f1);
        ViewHelper.setScaleY(mMainLayout, f1);
//        ObjectAnimator ObjectAnimator1 = ObjectAnimator.ofFloat(mMainLayout, "scaleX", f1);
//        ObjectAnimator ObjectAnimator2 = ObjectAnimator.ofFloat(mMainLayout, "scaleY", f1);
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.playTogether(ObjectAnimator1,ObjectAnimator2);
//        animationSet.start();

        ViewHelper.setTranslationX(mMenuLayout, mMenuLayout.getWidth() / 2.3f * percent - mMenuLayout.getWidth() / 2.3f);
        ViewHelper.setScaleX(mMenuLayout, 0.5f + 0.5f * percent);
        ViewHelper.setScaleY(mMenuLayout, 0.5f + 0.5f * percent);

        ViewHelper.setAlpha(mMenuLayout, percent);

        if (isShowShadow) {
            ViewHelper.setScaleX(iv_shadow, f1 * 1.4f * (1 - percent * 0.12f));
            ViewHelper.setScaleY(iv_shadow, f1 * 1.85f * (1 - percent * 0.12f));
        }
        if (getBackground() != null)
            getBackground().setColorFilter(evaluate(percent, Color.BLACK, Color.TRANSPARENT), Mode.SRC_OVER);
    }

    private Integer evaluate(float fraction, Object startValue, Integer endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }


    public enum Status {
        Drag, Open, Close
    }

    public Status getmStatus() {
        if (mMainLeftRange == 0) {
            mStatus = Status.Close;
        } else if (mMainLeftRange == mDragRange) {
            mStatus = Status.Open;
        } else {
            mStatus = Status.Drag;
        }
        return mStatus;
    }

    public void open() {
        open(true);
    }

    public void open(boolean animate) {
        if (animate) {
            //手指抬起后缓慢移动到指定位置
            if (mViewDragHelper.smoothSlideViewTo(mMainLayout, mDragRange, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mMainLayout.layout(mDragRange, 0, mDragRange * 2, mMenuHeight);
            dragEvent(mDragRange);
        }
    }

    public void close() {
        close(true);
    }

    public void close(boolean animate) {
        if (animate) {
            if (mViewDragHelper.smoothSlideViewTo(mMainLayout, 0, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mMainLayout.layout(0, 0, mMenuWidth, mMenuHeight);
            dragEvent(0);
        }
    }

    public ViewGroup getmMainLayout() {
        return mMainLayout;
    }

    public ViewGroup getmMenuLayout() {
        return mMenuLayout;
    }
}
