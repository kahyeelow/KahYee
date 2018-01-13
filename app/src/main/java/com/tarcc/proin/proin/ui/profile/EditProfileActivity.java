package com.tarcc.proin.proin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityEditProfileBinding;
import com.tarcc.proin.proin.model.User;
import com.tarcc.proin.proin.ui.BaseActivity;



public class EditProfileActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, EditProfileActivity.class);
        context.startActivity(starter);
    }

    private ActivityEditProfileBinding binding;
    //Okok here havent start do update project
    private SharedPreferences data;
    private SharedPreferences.Editor editor;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        initToolbar();

        data = PreferenceManager.getDefaultSharedPreferences(this);
        editor = data.edit();
        String userJson = data.getString("user_details", "");
        //if user details empty then will stop user continue in this page
        if(TextUtils.isEmpty(userJson)){
            finish();
        }
        user = User.deserialize(userJson);
        //so this user object will have all the user info
        user.getNric();
        //something like this
        //owhhh got it
        //so in the edit password,
        //user.getPassword()?
        //ya so for the my services the data return should be like this
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
