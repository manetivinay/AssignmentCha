package com.vinaymaneti.assignmentcha.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.model.FakeDataForeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 02/12/16.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<FakeDataForeRecyclerView> mDataForeRecyclerViews;
    private Context mContext;

    public ProductAdapter(Context mContext, ArrayList<FakeDataForeRecyclerView> fakeDatProductList) {
        this.mContext = mContext;
        mDataForeRecyclerViews = fakeDatProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FakeDataForeRecyclerView foreRecyclerView = mDataForeRecyclerViews.get(position);
        holder.productName.setText(foreRecyclerView.getProductName());
        holder.transcationName.setText(foreRecyclerView.getProductName());
    }

    @Override
    public int getItemCount() {
        return mDataForeRecyclerViews.size();
    }

    public Context getContext() {
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productName)
        AppCompatTextView productName;
        @BindView(R.id.transcationName)
        AppCompatTextView transcationName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
