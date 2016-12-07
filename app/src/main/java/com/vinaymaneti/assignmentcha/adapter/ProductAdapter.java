package com.vinaymaneti.assignmentcha.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.activities.TransactionActivity;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;
import com.vinaymaneti.assignmentcha.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

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
    //to get the count of 1 particular transaction count- i took hash map
    Map<String, Integer> counts;

    public ProductAdapter(Context mContext, List<FirstSetTransactionModel> fakeDatProductList) {
        this.mContext = mContext;
        allProductList = fakeDatProductList;

        if (allProductList.size() > 0) {
            Collections.sort(allProductList, new Comparator<FirstSetTransactionModel>() {
                @Override
                public int compare(FirstSetTransactionModel o1, FirstSetTransactionModel o2) {
                    return o1.getSku().compareTo(o2.getSku());
                }
            });
        }
        counts = new HashMap<>();
        uniqueProductList = new ArrayList<>();
        //initially we store first element to uniqueProductList (List) -- because we we wan to compare if exist we won't add else we will add
        uniqueProductList.add(allProductList.get(0));

        //convert List<Object> to String[]
        List<String> strings = new ArrayList<>(allProductList.size());
        Collections.sort(strings);
        //here i make for look to get the sku name
        for (FirstSetTransactionModel setTransactionModel : allProductList) {
            strings.add(setTransactionModel != null ? setTransactionModel.getSku() : null);
        }

        // based on name increment the count
        for (String s : strings) {
            if (counts.containsKey(s)) {
                counts.put(s, counts.get(s) + 1);
            } else {
                counts.put(s, 1);
            }
        }

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
        //Log.d("UniqueProductList", uniqueProductList.size() + "");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FirstSetTransactionModel bindModel = uniqueProductList.get(position);
        holder.productName.setText(bindModel.getSku());
        //below map object is used to sort alphabetically from a - z
        Map<String, Integer> map = new TreeMap<String, Integer>(counts);
        for (Map.Entry<String, Integer> integerMap : map.entrySet()) {
            //here I make --  based on product name attach transaction count
            if (bindModel.getSku().equals(integerMap.getKey())) {
                holder.transactionName.setText(String.format(Locale.ENGLISH, "%d %s", integerMap.getValue(), " transactions"));
            }
        }
        holder.individualItemLinearLayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Map<String, List<FirstSetTransactionModel>> map = new HashMap<>();
                List<FirstSetTransactionModel> list1 = null;
                for (FirstSetTransactionModel student : allProductList) {
                    String key = student.getSku();
                    if (map.containsKey(key)) {
                        list1 = map.get(key);
                        list1.add(student);
                    } else {
                        List<FirstSetTransactionModel> list = new ArrayList<FirstSetTransactionModel>();
                        list.add(student);
                        map.put(key, list);
                    }
                }
                List<FirstSetTransactionModel> eachProductRelatedTransactions = map.get(bindModel.getSku());

                Intent intent = new Intent(getContext(), TransactionActivity.class);
                intent.putExtra(Constants.TRANSACTION_NAME, bindModel.getSku());
                intent.putParcelableArrayListExtra(Constants.DATA, (ArrayList<? extends Parcelable>) eachProductRelatedTransactions);
                getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return uniqueProductList.size();
    }

    public Context getContext() {
        return mContext;
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
}
