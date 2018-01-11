package com.tarcc.proin.proin.ui.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.FragmentProfileBinding;
import com.tarcc.proin.proin.ui.login.LoginActivity;



public class ProfileFragment extends Fragment implements View.OnClickListener {

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOnClick();
    }

    private void initOnClick() {
        binding.logout.setOnClickListener(this);
        binding.editProfile.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
        binding.paymentMethod.setOnClickListener(this);
        binding.myServices.setOnClickListener(this);
        binding.privacyPolicy.setOnClickListener(this);
        binding.termsConditions.setOnClickListener(this);
        binding.aboutUs.setOnClickListener(this);
        binding.myServices.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.logout) {
            LoginActivity.restart(getActivity());
            getActivity().finish();
        } else if (view == binding.editProfile) {
            EditProfileActivity.start(getActivity());
        } else if (view == binding.changePassword) {
            ChangePasswordActivity.start(getActivity());
        } else if (view == binding.paymentMethod) {

        } else if (view == binding.myServices) {
            MyServicesActivity.start(getActivity());
        } else if (view == binding.privacyPolicy) {
            PrivacyPolicyActivity.start(getActivity());
        } else if (view == binding.termsConditions) {
            TermConditionsActivity.start(getActivity());
        } else if (view == binding.aboutUs) {
            AboutUsActivity.start(getActivity());
        }
    }
}
