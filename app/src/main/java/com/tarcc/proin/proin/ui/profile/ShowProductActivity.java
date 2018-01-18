package com.tarcc.proin.proin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ContentShowProductBinding;
import com.tarcc.proin.proin.databinding.ActivityShowProductBinding;
import com.tarcc.proin.proin.ui.BaseActivity;

public class ShowProductActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ShowProductActivity.class);
        context.startActivity(starter);
    }

    private SharedPreferences data;
    private SharedPreferences.Editor editor;

private ActivityShowProductBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_product);
        initToolbar();
        data = PreferenceManager.getDefaultSharedPreferences(this);
        editor = data.edit();

        String coverage = data.getString("coverage","");
        String premium = data.getString("premium","");
        String status = data.getString("status","");
        String expireDate = data.getString("expireDate","");
        String totPaymentYear = data.getString("totPaymentYear","");

        binding.textViewCoverage.setText("Coverage : " + coverage);
        binding.textViewPremium.setText("Premium(RM): " + premium);
        binding.textViewStatus.setText("Payment Mode : " + status);
        binding.textViewExpireDate.setText("Insurance Expired Date : " +expireDate);
        binding.textViewTotPayYear.setText("Total Payment Year : " + totPaymentYear + " years");

    }

    private void initToolbar() {
        setSupportActionBar(binding.actionTool);
        binding.actionTool.setNavigationOnClickListener(new View.OnClickListener() {
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
