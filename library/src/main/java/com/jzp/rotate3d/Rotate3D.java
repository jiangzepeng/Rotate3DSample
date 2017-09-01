package com.jzp.rotate3d;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * Description : 旋转3D动画
 * Author : jzp
 * Date   : 17/8/31
 */

public class Rotate3D {
    private Context context;
    private View parentView;
    private View positiveView;
    private View negativeView;

    private int centerX;
    private int centerY;
    private int depthZ;
    private int duration;
    private Rotate3dAnimation openAnimation;
    private Rotate3dAnimation closeAnimation;

    private boolean isOpen = false;

    private Rotate3D(Builder builder) {
        this.context = builder.context;
        this.parentView = builder.parentView;
        this.positiveView = builder.positiveView;
        this.negativeView = builder.negativeView;
        this.duration = builder.duration;
        this.depthZ = builder.depthZ;
    }

    public View getParentView() {
        return parentView;
    }

    public View getPositiveView() {
        return positiveView;
    }

    public View getNegativeView() {
        return negativeView;
    }

    public void transform() {
        //以旋转对象的中心点为旋转中心点，这里主要不要再onCreate方法中获取，因为视图初始绘制时，获取的宽高为0
        centerX = parentView.getWidth() / 2;
        centerY = parentView.getHeight() / 2;
        if (openAnimation == null) {
            initOpenAnim();
            initCloseAnim();
        }

        //用作判断当前点击事件发生时动画是否正在执行
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
            return;
        }
        if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) {
            return;
        }

        //判断动画执行
        parentView.startAnimation(isOpen ? closeAnimation : openAnimation);

        isOpen = !isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 卡牌文本介绍打开效果：注意旋转角度
     */
    private void initOpenAnim() {
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(context, 0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                positiveView.setVisibility(View.GONE);
                negativeView.setVisibility(View.VISIBLE);

                //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(context, 270, 360, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                parentView.startAnimation(rotateAnimation);
            }
        });
    }

    /**
     * 卡牌文本介绍关闭效果：旋转角度与打开时逆行即可
     */
    private void initCloseAnim() {
        closeAnimation = new Rotate3dAnimation(context, 360, 270, centerX, centerY, depthZ, true);
        closeAnimation.setDuration(duration);
        closeAnimation.setFillAfter(true);
        closeAnimation.setInterpolator(new AccelerateInterpolator());
        closeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                positiveView.setVisibility(View.VISIBLE);
                negativeView.setVisibility(View.GONE);

                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(context, 90, 0, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                parentView.startAnimation(rotateAnimation);
            }
        });
    }

    public static class Builder {
        private int depthZ = 400;
        private int duration = 400;
        private Context context;
        private View parentView;
        private View positiveView;
        private View negativeView;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDepthZ(int depthZ) {
            this.depthZ = depthZ;
            return this;
        }

        public Builder bindParentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public Builder bindPositiveView(View positiveView) {
            this.positiveView = positiveView;
            return this;
        }

        public Builder bindNegativeView(View negativeView) {
            this.negativeView = negativeView;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Rotate3D create() {
            Rotate3D rotate3D = new Rotate3D(this);
            if (rotate3D.getParentView() == null) {
                throw new NullPointerException("Please set ParentView");
            }
            if (rotate3D.getPositiveView() == null) {
                throw new NullPointerException("Please set PositiveView");
            }
            if (rotate3D.getNegativeView() == null) {
                throw new NullPointerException("Please set NegativeView");
            }

            return rotate3D;
        }
    }

}
