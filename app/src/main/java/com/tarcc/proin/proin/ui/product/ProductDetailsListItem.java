package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ViewProductDetailsItemBinding;
import com.tarcc.proin.proin.model.Benefit;
import com.tarcc.proin.proin.model.Product;



public class ProductDetailsListItem extends FrameLayout {

    private ViewProductDetailsItemBinding binding;

    public ProductDetailsListItem(@NonNull Context context) {
        super(context);
        if (!isInEditMode()) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.view_product_details_item,
                    this,
                    true);
        }
    }

    public void update(Benefit benefit) {
        binding.image.setImageResource(benefit.getDrawableId());
        binding.name.setText(benefit.getName());
        binding.desc.setText(benefit.getDescription());
    }
}
