package com.tarcc.proin.proin.ui.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityForgotPasswordBinding;



public class ForgotPasswordActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgotPasswordActivity.class);
        context.startActivity(starter);
    }

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.actionToolbar);
        binding.actionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }
}
