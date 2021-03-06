package com.tangotkk.gesturelock.sample;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tangotkk.gesturelock.library.LockPatternView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GestureAuthActivity extends AppCompatActivity implements LockPatternView.OnPatternListener{

    private static final int CAN_INPUT_COUNT = 5;  //总共可以输入五次
    private static final String SUCCESS_INFO = "手势密码正确";
    private static final String ERROR_PATTERN = "密码错误，还可以输入%d次";

    TextView vForgetGesture;

    LockPatternView vLockPatternView;

    TextView vInfoTv;

    String gesture;

    int count = CAN_INPUT_COUNT;

    @ColorInt int normalColor = Color.parseColor("#666666");
    @ColorInt int errorColor = Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_auth);
        vForgetGesture = (TextView) findViewById(R.id.forget_gesture);
        vInfoTv = (TextView) findViewById(R.id.info_tv);
        vLockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        gesture = GesturePref.getGesturePassword(this);
        if(TextUtils.isEmpty(gesture)){
            goToMain();
        }
        vLockPatternView.setOnPatternListener(this);
        vForgetGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 这里应该验证密码或者跳转到登录页
                goToMain();
            }
        });
    }

    @Override
    public void onPatternStart() {

    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        if(TextUtils.equals(toString(pattern), gesture)){
            vInfoTv.setText(SUCCESS_INFO);
            vInfoTv.setTextColor(normalColor);
            goToMain();
        } else {
            count --;
            if(count <= 0){
                //TODO 跳转到登录页面
                finish();
                return;
            }

            vInfoTv.setText(String.format(Locale.getDefault(), ERROR_PATTERN, count));
            vInfoTv.setTextColor(errorColor);
            vLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            vLockPatternView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    vLockPatternView.clearPattern();
                }
            }, 500);
        }
    }

    protected void goToMain(){
        startActivity(new Intent(GestureAuthActivity.this, MainActivity.class));
        finish();
    }


    protected String toString(List<LockPatternView.Cell> pattern){
        return Arrays.toString(pattern.toArray());
    }
}
