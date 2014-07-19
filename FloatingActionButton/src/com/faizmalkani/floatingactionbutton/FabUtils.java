package com.faizmalkani.floatingactionbutton;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by yangfeng on 14-7-19.
 */
public class FabUtils {
    private FabUtils() {
        // no instance
    }

    private static final boolean isAfterIcsVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static void animateY(View view, float current, float target) {
        if (isAfterIcsVersion()) {
            ObjectAnimator mHideAnimation = ObjectAnimator.ofFloat(view, "Y", target);
            mHideAnimation.setInterpolator(new AccelerateInterpolator());
            mHideAnimation.start();
        } else {
            TranslateAnimation anim;
            if (target > current) {
                anim = new TranslateAnimation(0, 0, 0, target - current);
            } else {
                anim = new TranslateAnimation(0, 0, current - target, 0);
            }
            anim.setDuration(300); //这个值越大动画就越卡，越容易花屏
            anim.setInterpolator(view.getContext(), android.R.anim.accelerate_decelerate_interpolator);
            anim.setFillAfter(true);
            view.startAnimation(anim);
        }
    }

    public static void setAlphaForView(View v, float alpha) {
        if (isAfterIcsVersion()) {
            v.setAlpha(alpha);
        } else {
            AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            v.startAnimation(animation);
        }
    }

    public static void setLayerTypeForView(View view) {
        if (isAfterIcsVersion()) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public static float getYForView(View view) {
        if (isAfterIcsVersion()) {
            return view.getY();
        } else {
            float currentY;
            int[] loc = new int[2];
            view.getLocationInWindow(loc);
            currentY = loc[1];
            currentY -= view.getHeight();
            return currentY;
        }
    }
}
