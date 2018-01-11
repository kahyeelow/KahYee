package com.tarcc.proin.proin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityAboutUsBinding;
import com.tarcc.proin.proin.ui.BaseActivity;



public class AboutUsActivity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, AboutUsActivity.class);
        context.startActivity(starter);
    }

    private ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
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
