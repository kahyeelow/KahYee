package com.tarcc.proin.proin.ui.product;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.FragmentProductBinding;
import com.tarcc.proin.proin.databinding.FragmentProductOverviewBinding;
import com.tarcc.proin.proin.model.Benefit;
import com.tarcc.proin.proin.model.Product;

import java.util.ArrayList;



public class ProductOverviewFragment extends Fragment {

    public static ProductOverviewFragment newInstance(Product product) {

        Bundle args = new Bundle();
        args.putString("product", product.toJson());

        ProductOverviewFragment fragment = new ProductOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentProductOverviewBinding binding;
    private Product product;
    private ProductDetailsAdapter adapter;
    private ProductDetailsHeaderView headerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = Product.deserialize(getArguments().getString("product"));
        adapter = new ProductDetailsAdapter(getActivity(), new ArrayList<Benefit>());
        headerView = new ProductDetailsHeaderView(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_overview, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.listView.setDividerHeight(0);
        binding.listView.setDivider(null);
        binding.listView.setFooterDividersEnabled(false);
        binding.listView.setHeaderDividersEnabled(false);

        headerView.update(product);
        binding.listView.addHeaderView(headerView);
        binding.listView.setAdapter(adapter);
        adapter.addAll(product.getBenefits());
    }
}
