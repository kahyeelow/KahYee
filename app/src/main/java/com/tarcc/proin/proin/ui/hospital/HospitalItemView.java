package com.tarcc.proin.proin.ui.hospital;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ViewHospitalItemBinding;
import com.tarcc.proin.proin.model.Hospital;



public class HospitalItemView extends FrameLayout {
    private ViewHospitalItemBinding binding;

    public HospitalItemView(@NonNull Context context) {
        super(context);
        if (!isInEditMode()) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.view_hospital_item,
                    this,
                    true);
        }
    }

    public void update(Hospital hospital) {
        binding.name.setText(hospital.getName());
        binding.address.setText(hospital.getAddress());
    }
}
