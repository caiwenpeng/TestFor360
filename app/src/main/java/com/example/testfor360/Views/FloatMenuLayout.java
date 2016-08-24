package com.example.testfor360.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.testfor360.R;

/**
 * Created by 蔡大爷 on 2016/8/18.
 */
public class FloatMenuLayout extends LinearLayout {

    LinearLayout menuLayout;
    TranslateAnimation openTranslateAnimation;
    TranslateAnimation closeTranslateAnimation;

    public FloatMenuLayout(Context context) {
        super(context);

        View root = View.inflate(getContext(), R.layout.float_menu_view, null);
        menuLayout = (LinearLayout) root.findViewById(R.id.menuLayout);


        openTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        openTranslateAnimation.setDuration(500);
        openTranslateAnimation.setFillAfter(true);

        closeTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,1.0f);
        closeTranslateAnimation.setDuration(500);
        closeTranslateAnimation.setFillAfter(true);

        addView(root);

    }

    public FloatMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void openAnimation() {

        menuLayout.setAnimation(openTranslateAnimation);
        openTranslateAnimation.start();

    }

    public void closeAnimation() {
        menuLayout.setAnimation(closeTranslateAnimation);
        closeTranslateAnimation.start();
    }
}
