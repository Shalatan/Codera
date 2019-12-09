package com.parse.starter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

class MFabButtons {
    private FloatingActionButton fab_button_1, fab_button_2, fab_button_3, fab_button_4;
    private OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    private int fabTranslationY = 100;
    private boolean isFabMenuOpen = false;
    private int speed = 250;
    private Context context;

    MFabButtons(Context context, FloatingActionButton fab_button_1, FloatingActionButton fab_button_2, FloatingActionButton fab_button_3, FloatingActionButton fab_button_4) {
        this.fab_button_1 = fab_button_1;
        this.fab_button_2 = fab_button_2;
        this.fab_button_3 = fab_button_3;
        this.fab_button_4 = fab_button_4;
        this.context = context;

        setFabTranslationY();
        setFabClicks();
    }

    private void setFabTranslationY() {
        fab_button_2.setAlpha(0f);
        fab_button_3.setAlpha(0f);
        fab_button_4.setAlpha(0f);

        fab_button_2.setTranslationY(fabTranslationY);
        fab_button_3.setTranslationY(fabTranslationY);
        fab_button_4.setTranslationY(fabTranslationY);
    }

    private void setFabClicks() {
        fab_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuCheck();
//                new Activity
//                context.startActivity(new Intent(context, YourActivity));
            }
        });

        fab_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Pressed","Button 1");
                menuCheck();
            }
        });

        fab_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Pressed","Button 2");
                menuCheck();
            }
        });

        fab_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Pressed","Button 3");

                menuCheck();
            }
        });
    }

    private void fabMenuOpen() {
        isFabMenuOpen = !isFabMenuOpen;
        fabMainAnimation(true);
        fabOpenAnimation(fab_button_2);
        fabOpenAnimation(fab_button_3);
        fabOpenAnimation(fab_button_4);
    }

    private void fabMenuClose() {
        isFabMenuOpen = !isFabMenuOpen;
        fabMainAnimation(false);
        fabCloseAnimation(fab_button_2);
        fabCloseAnimation(fab_button_3);
        fabCloseAnimation(fab_button_4);
    }

    private void fabCloseAnimation(FloatingActionButton fab) {
        fab.animate().translationY(fabTranslationY).alpha(0f).setInterpolator(overshootInterpolator).setDuration(speed).start();
    }

    private void fabOpenAnimation(FloatingActionButton fab) {
        fab.animate().translationY(0f).alpha(1f).setInterpolator(overshootInterpolator).setDuration(speed).start();
    }

    private void fabMainAnimation(boolean isOpen) {
        if (isOpen) {
            fab_button_1.animate().setInterpolator(overshootInterpolator).rotation(45f).setDuration(speed).start();
        } else {
            fab_button_1.animate().setInterpolator(overshootInterpolator).rotation(0f).setDuration(speed).start();
        }
    }

    private void menuCheck() {
        if (isFabMenuOpen) {
            fabMenuClose();
        } else {
            fabMenuOpen();
        }
    }
}
