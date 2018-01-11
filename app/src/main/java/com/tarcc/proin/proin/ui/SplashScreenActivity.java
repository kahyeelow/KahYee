package com.tarcc.proin.proin.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivitySplashScreenBinding;
import com.tarcc.proin.proin.ui.login.LoginActivity;


public class SplashScreenActivity extends BaseActivity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        binding.appName.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.start(SplashScreenActivity.this);
                finish();
            }
        }, 2000);

    }
}
