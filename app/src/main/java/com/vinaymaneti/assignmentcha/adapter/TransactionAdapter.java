package com.vinaymaneti.assignmentcha.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 04/12/16.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {


    private List<FirstSetTransactionModel> mDataForeRecyclerViews;
    private Context mContext;

    public TransactionAdapter(Context context, List<FirstSetTransactionModel> fakeDataForeRecyclerViews) {
        mDataForeRecyclerViews = fakeDataForeRecyclerViews;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirstSetTransactionModel foreRecyclerView = mDataForeRecyclerViews.get(position);
        holder.productName.setText(CurrencyType.fromString(foreRecyclerView.getCurrency()) + foreRecyclerView.getAmount());
//        holder.transactionName.setVisibility(View.GONE);
        holder.transactionName.setText(foreRecyclerView.getConvertedCurrency());
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mDataForeRecyclerViews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.individualItemLinearLayout)
        LinearLayout individualItemLinearLayout;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.transactionName)
        TextView transactionName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public enum CurrencyType {
        USD("$"),
        CAD("¢"),
        GBP("£"),
        AUD("A$"),
        EUR("€");

        private String currencySym;

        CurrencyType(String currencySym) {
            this.currencySym = currencySym;
        }

        public String getCurrencySym() {
            return currencySym;
        }

        public static String fromString(String currencySym) {
            if (currencySym != null) {
                for (CurrencyType currencyType : CurrencyType.values()) {
                    if (currencySym.equalsIgnoreCase(currencyType.name())) {
                        return currencyType.currencySym;
                    }
                }
            }
            throw new IllegalArgumentException("No Constant with Currency symbol" + currencySym + " found");
        }
    }
}
