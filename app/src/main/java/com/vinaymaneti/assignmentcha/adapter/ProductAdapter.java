package com.vinaymaneti.assignmentcha.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 02/12/16.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    //All product list -- this we get initially when we parse json
    private List<FirstSetTransactionModel> allProductList;
    //Unique Product List -- this is to get the unique elements based on SKU and we store it in List
    private List<FirstSetTransactionModel> uniqueProductList;
    private Context mContext;

    public ProductAdapter(Context mContext, List<FirstSetTransactionModel> fakeDatProductList) {
        this.mContext = mContext;
        allProductList = fakeDatProductList;

        uniqueProductList = new ArrayList<>();
        //initially we store first element to uniqueProductList (List) -- because we we wan to compare if exist we won't add else we will add
        uniqueProductList.add(allProductList.get(0));
        //here we make for loop to get the first item from all the allProductList
        for (FirstSetTransactionModel allProductSku : allProductList) {
            // initially we maintain boolean flag if false we won't add to uniqueProductList else if it is true we will add to uniqueProductList
            boolean flag = false;
            // here we make for loop to get all the uniqueProductList
            for (FirstSetTransactionModel uniqueProductSku : uniqueProductList) {
                // here we compare  allProductSku is present in uniqueProductSku -- if it contains we won't add else we will add
                // if both uniqueProductSku and allProductSku are not equal it means that sku item is nor present then here the flag value is false
                if (uniqueProductSku.getSku().equals(allProductSku.getSku())) {
                    flag = true;
                }
            }
            // if flag value is false then add the element to uniqueProductList  - it mean negation of false is true try to add element to uniqueProductList
            if (!flag)
                uniqueProductList.add(allProductSku);

        }
        Log.d("UniqueProductList", uniqueProductList.size() + "");

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirstSetTransactionModel foreRecyclerView = uniqueProductList.get(position);
//        for (String s : uniList) {
//            holder.productName.setText(s);
//        }
        holder.productName.setText(foreRecyclerView.getSku());
        //holder.transcationName.setText(foreRecyclerView.getAmount());
    }

    @Override
    public int getItemCount() {
        return uniqueProductList.size();
    }

    public Context getContext() {
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.transcationName)
        TextView transcationName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
