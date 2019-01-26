package com.quansu.utils;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

import javax.security.auth.callback.Callback;

public class AnimateUtils {

    /**
     * @param v
     */
    public void button_click(View v, View.OnClickListener listener) {
        ViewCompat.animate(v)
                .setDuration(100)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setInterpolator(new CycleInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        if (listener != null) {
                            listener.onClick(view);
                        }

                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).withLayer()
                .start();

    }


    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }


}
