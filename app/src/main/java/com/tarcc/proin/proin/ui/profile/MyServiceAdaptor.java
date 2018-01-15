package com.tarcc.proin.proin.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.model.ProductPackage;

import java.util.List;

/**
 * Created by yee_l on 14/1/2018.
 */

public class MyServiceAdaptor extends ArrayAdapter<ProductPackage>{
    private List<ProductPackage> list;
    Activity context;

    public MyServiceAdaptor(Activity context,int resource, List<ProductPackage> list)
    {
        super(context,resource,list);
        this.list = list;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       View rowView = inflater.inflate(R.layout.myservices_list_item,parent,false);

        TextView textViewProductID = (TextView) rowView.findViewById(R.id.textViewProdID);
        TextView textViewCoverage = (TextView) rowView.findViewById(R.id.textViewCoverage);
        TextView textViewPremium = (TextView) rowView.findViewById(R.id.textViewPremium);
        TextView textViewStatus = (TextView) rowView.findViewById(R.id.textViewStatus);
        TextView textViewExpireDate = (TextView) rowView.findViewById(R.id.textViewExpireDate);
        TextView textViewTotPayYear = (TextView) rowView.findViewById(R.id.textViewTotPayYear);

        ProductPackage productPackage;
        productPackage = getItem(position);

        textViewProductID.setText("Product ID : " + productPackage.getProductID());
        textViewCoverage.setText("Coverage : " + productPackage.getCoverage());
        textViewPremium.setText("Premium(RM): " + productPackage.getPremium());
        textViewStatus.setText("Payment Mode : " + productPackage.getStatus());
        textViewExpireDate.setText("Insurance Expired Date : " + productPackage.getExpireDate());
        textViewTotPayYear.setText("Total Payment Year : " + productPackage.getTotPaymentYear());

        return rowView;
    }


}
