package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ViewProductDetailsHeaderBinding;
import com.tarcc.proin.proin.model.Product;


public class ProductDetailsHeaderView extends FrameLayout {

    private ViewProductDetailsHeaderBinding binding;

    public ProductDetailsHeaderView(@NonNull Context context) {
        super(context);
        if (!isInEditMode()) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.view_product_details_header,
                    this,
                    true);
        }
    }

    public void update(Product product) {
        binding.name.setText(product.getName());
        binding.desc.setText(product.getDescription());
    }
}
