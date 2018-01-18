package com.tarcc.proin.proin.ui.hospital;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.bumptech.glide.Glide;
import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityHospitalDetailsBinding;
import com.tarcc.proin.proin.model.Hospital;
import com.tarcc.proin.proin.ui.BaseActivity;



public class HospitalDetailsActivity extends BaseActivity {

    public static void start(Context context, Hospital hospital) {
        Intent starter = new Intent(context, HospitalDetailsActivity.class);
        starter.putExtra("hospital", hospital.toJson());
        context.startActivity(starter);
    }

    private ActivityHospitalDetailsBinding binding;
    private Hospital hospital;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hospital_details);
        hospital = Hospital.deserialize(getIntent().getStringExtra("hospital"));

        initToolbar();

        Glide.with(this)
                .load(hospital.getImageUrl())
                .into(binding.image);

        populateHospital();
    }


    private void populateHospital() {
        binding.name.setText(hospital.getName());
        binding.desc.setText(hospital.getDescription());
        binding.address.setText(hospital.getAddress());
        binding.hours.setText(hospital.getOperationHours());
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
            getSupportActionBar().setTitle(hospital.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }










}

