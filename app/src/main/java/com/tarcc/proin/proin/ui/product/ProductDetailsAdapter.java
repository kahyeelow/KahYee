package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tarcc.proin.proin.model.Benefit;

import java.util.ArrayList;
import java.util.List;



public class ProductDetailsAdapter extends ArrayAdapter<Benefit> {

    public ProductDetailsAdapter(@NonNull Context context, @NonNull ArrayList<Benefit> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = new ProductDetailsListItem(getContext());
        }

        ((ProductDetailsListItem) convertView).update(getItem(position));

        return convertView;
    }
}
