package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityTabLayoutPagerBinding;
import com.tarcc.proin.proin.ui.BaseActivity;
import com.tarcc.proin.proin.model.Product;
import com.tarcc.proin.proin.widget.ViewPagerAdapter;



public class ProductDetailsActivity extends BaseActivity {

    public static void start(Context context, Product product) {
        Intent starter = new Intent(context, ProductDetailsActivity.class);
        starter.putExtra("product", product.toJson());
        context.startActivity(starter);
    }

    private ActivityTabLayoutPagerBinding binding;
    private ViewPagerAdapter viewPagerAdapter;
    private Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_layout_pager);
        product = Product.deserialize(getIntent().getStringExtra("product"));

        initToolbar();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ProductOverviewFragment.newInstance(product), getString(R.string.overview));
        viewPagerAdapter.addFragment(ProductPurchaseFragment.newInstance(product), getString(R.string.purchase));

        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
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
            getSupportActionBar().setTitle(product.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }
}
