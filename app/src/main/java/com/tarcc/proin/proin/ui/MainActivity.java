package com.tarcc.proin.proin.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityMainBinding;
import com.tarcc.proin.proin.ui.hospital.HospitalFragment;
import com.tarcc.proin.proin.ui.product.ProductFragment;
import com.tarcc.proin.proin.ui.profile.ProfileFragment;
import com.tarcc.proin.proin.widget.ViewPagerAdapter;

public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        //Here is the same as the code that i comment out
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private ActivityMainBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ProductFragment.newInstance(), "");
        viewPagerAdapter.addFragment(HospitalFragment.newInstance(), "");
        viewPagerAdapter.addFragment(ProfileFragment.newInstance(), "");
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(onPageChangeListener);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    binding.bottomNavigation.setSelectedItemId(R.id.action_product);
                    break;
                case 1:
                    binding.bottomNavigation.setSelectedItemId(R.id.action_map);
                    break;
                case 2:
                    binding.bottomNavigation.setSelectedItemId(R.id.action_profile);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_product:
                    binding.viewPager.setCurrentItem(0);
                    break;
                case R.id.action_map:
                    binding.viewPager.setCurrentItem(1);
                    break;
                case R.id.action_profile:
                    binding.viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        }
    };
}
