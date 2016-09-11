package com.tangotkk.gesturelock.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.tangotkk.gesturelock.library.GestureIndicatorView;
import com.tangotkk.gesturelock.library.LockPatternView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestureSetupActivity extends AppCompatActivity implements LockPatternView.OnPatternListener{

    private static final int STEP_1 = 0; //初始化状态
    private static final int STEP_2 = 1;  //已经输入了一次
    private static final int STEP_3 = 2; //输入了第二次，输入完成，可以退出

    private static final String INPUT = "请绘制手势密码";
    private static final String INPUT_AGAIN = "请再次绘制手势密码";
    private static final String INPUT_SUCCESS = "手势密码设置成功";
    private static final String CONFIRM_NOT_EQUAL = "两次密码绘制不一致";

    GestureIndicatorView vIndicatorView;

    LockPatternView vLockPatternView;


    int step = STEP_1;

    private List<LockPatternView.Cell> choosePattern = new ArrayList<>();

    TextView vTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_setup);
        vIndicatorView = (GestureIndicatorView) findViewById(R.id.indicator);
        vLockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        vTv = (TextView) findViewById(R.id.tv);
        vLockPatternView.setOnPatternListener(this);
        render();
    }


    protected void render(){
        vTv.setTextColor(Color.parseColor("#222222"));
        switch (step){
            case STEP_1:
                choosePattern.clear();
                vIndicatorView.clear();
                vLockPatternView.clearPattern();
                vLockPatternView.enableInput();
                vTv.setText(INPUT);
                break;
            case STEP_2:
                vIndicatorView.setPattern(choosePattern);
                vLockPatternView.clearPattern();
                vLockPatternView.enableInput();
                vTv.setText(INPUT_AGAIN);
                break;
            case STEP_3:
                vIndicatorView.setPattern(choosePattern);
                vLockPatternView.disableInput();
                vTv.setText(INPUT_SUCCESS);
                break;
        }
    }

    protected void setPattern(List<LockPatternView.Cell> pattern){
        choosePattern.clear();
        choosePattern.addAll(pattern);
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

        switch (step){
            case STEP_1:
                if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
                    vTv.setTextColor(Color.RED);
                    vTv.setText("至少绘制四个点");
                    vLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                } else {
                    setPattern(pattern);
                    step = STEP_2;
                    render();
                }
                break;
            case STEP_2:
                if(!equal(choosePattern, pattern)){
                    vTv.setTextColor(Color.RED);
                    vTv.setText(CONFIRM_NOT_EQUAL);
                    vLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                } else {
                    step = STEP_3;
                    render();
                    GesturePref.storeGesturePassword(this, Arrays.toString(choosePattern.toArray()));
                    Toast.makeText(this, "手势密码设置成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


    protected boolean equal(List<LockPatternView.Cell> pattern1, List<LockPatternView.Cell> pattern2){
        String pattern1Str = Arrays.toString(pattern1.toArray());
        String pattern2Str = Arrays.toString(pattern2.toArray());
        return TextUtils.equals(pattern1Str, pattern2Str);
    }
}
