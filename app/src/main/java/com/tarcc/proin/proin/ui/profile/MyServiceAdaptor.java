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
    private String displayProdName;


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
        TextView textViewProdName = (TextView)rowView.findViewById(R.id.textViewProductName);

        ProductPackage productPackage;
        productPackage = getItem(position);
        String prodID = productPackage.getProductID();

        textViewProductID.setText("Product ID : " + productPackage.getProductID());
        textViewProdName.setText("Product Name: " + displayProdName(prodID));


        return rowView;
    }

    public String displayProdName(String prodID){
        if(prodID.equals("P001"))
            displayProdName = "Accident Protection - Pro Total-Med Shield";
        else if(prodID.equals("P002"))
            displayProdName = "Critical Illness Protection - Pro CriticalCare";
        else if(prodID.equals("P003"))
            displayProdName = "Medical Protection - Pro Med-Booster";

        return displayProdName;
    }




}
