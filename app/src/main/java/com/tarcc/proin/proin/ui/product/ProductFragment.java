package com.tarcc.proin.proin.ui.product;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.FragmentProductBinding;
import com.tarcc.proin.proin.model.Benefit;
import com.tarcc.proin.proin.model.Product;

import java.util.ArrayList;


public class ProductFragment extends Fragment implements View.OnClickListener {


    public static ProductFragment newInstance() {

        Bundle args = new Bundle();

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentProductBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.accident.setOnClickListener(this);
        binding.criticalIll.setOnClickListener(this);
        binding.life.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.accident) {
            ProductDetailsActivity.start(getActivity(), getAccidentProduct());
        } else if (view == binding.criticalIll) {
            ProductDetailsActivity.start(getActivity(), getCriticalIllProduct());
        } else if (view == binding.life) {
            ProductDetailsActivity.start(getActivity(), getMedicalProduct());
        }
    }

    private Product getAccidentProduct() {
        ArrayList<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit().setName("ACCIDENTAL DEATH BENEFIT")
                .setDescription("Your family will receive a lump sum payment if you pass away due to an accident. We will pay twice the amount if the accident occurs in a public bus or train, in an elevator or in a fire in a public building.")
                .setDrawableId(R.drawable.accident1));

        benefits.add(new Benefit().setName("ACCIDENTAL DISMEMBERMENT BENEFIT")
                .setDescription("You will receive a lump sum payment if you lose a part of your body in an accident. We will pay twice the amount if the accident occurs in a public bus or train, in an elevator or in a fire in a public building.")
                .setDrawableId(R.drawable.accident2));

        benefits.add(new Benefit().setName("TOTAL & PERMANENT DISABILITY BENEFIT")
                .setDescription("This plan covers you if you suffer total & permanent disability due to an accident. We will pay twice the amount if the accident occurs in a public bus or train, in an elevator or in a fire in a public building.")
                .setDrawableId(R.drawable.accident3));

        benefits.add(new Benefit().setName("ENJOY ADDITIONAL COVERAGE WITH A-PLUS TOTAL ACCIDENTSHIELD")
                .setDescription("A-Plus Total AccidentShield will also provide you with a weekly allowance if you suffer from temporary disability due to an accident. We will also reimburse the medical costs you incur as a result of an injury caused by an accident (capped at 6% of the amount you are covered for).")
                .setDrawableId(R.drawable.accident4));

        Product product = new Product()
                .setProductId("P001")
                .setName("Accident Protection")
                .setDescription("No matter how old or how healthy you are, a serious accident can change your life significantly. \n" +
                        "You can add A-Plus AccidentShield and A-Plus Total AccidentShield to one of our traditional life or investment-linked plans to boost your coverage against accidents.")
                .setBenefits(benefits);

        return product;
    }

    private Product getCriticalIllProduct() {
        ArrayList<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit().setName("CRITICAL ILLNESS PROTECTION")
                .setDescription("We will give you a lump sum payment to support you during this challenging time.")
                .setDrawableId(R.drawable.ill1));

        benefits.add(new Benefit().setName("ADDITIONAL COVERAGE THROUGH ANNIVERSARY BONUS")
                .setDescription("You may enjoy additional coverage for every year that this add-on policy remains active.")
                .setDrawableId(R.drawable.ill2));


        Product product = new Product()
                .setProductId("P002")
                .setName("Critical Illness")
                .setDescription("While we hope for a long and healthy life, it is always best to be prepared for whatever that comes our way, even something as tragic as a critical illness.\n" +
                        "With A-Plus CriticalCare, you will be supported financially so you and your loved ones can focus on your recovery.")
                .setBenefits(benefits);

        return product;
    }

    private Product getMedicalProduct() {
        ArrayList<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit().setName("HIGH ANNUAL LIMIT OF UP TO RM1.5 MILLION")
                .setDescription("Enjoy up to RM1.5 million of medical protection every year.")
                .setDrawableId(R.drawable.medical1));

        benefits.add(new Benefit().setName("NO LIFETIME LIMIT")
                .setDescription("While there is a limit on how much you can claim each year, there is no limit on how much you can claim in a lifetime.")
                .setDrawableId(R.drawable.medical2));

        benefits.add(new Benefit().setName("DIFFERENT COVERAGE OPTIONS")
                .setDescription("There are six medical plans you can choose from, depending on your needs.")
                .setDrawableId(R.drawable.medical3));

        benefits.add(new Benefit().setName("HOSPITAL ROOM AND BOARD BOOSTER")
                .setDescription("Your hospital room and board limit may increase by 20% every two years, in the first 10 years of your plan.")
                .setDrawableId(R.drawable.medical4));

        Product product = new Product()
                .setProductId("P003")
                .setName("Medical Protection")
                .setDescription("A-Plus MedBooster complements A-Plus Med and A-Life Med Regular to increase your medical protection levels to help you cope with rising medical costs.")
                .setBenefits(benefits);

        return product;
    }
}
