package com.tarcc.proin.proin.ui.hospital;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.tarcc.proin.proin.model.Hospital;
import java.util.ArrayList;



public class HospitalAdapter extends ArrayAdapter<Hospital> {
    public HospitalAdapter(@NonNull Context context, @NonNull ArrayList<Hospital> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = new HospitalItemView(getContext());
        }

        ((HospitalItemView) convertView).update(getItem(position));
        return convertView;
    }
}
