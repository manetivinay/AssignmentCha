package com.vinaymaneti.assignmentcha.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.adapter.ProductAdapter;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Here I use butter knife to eliminate boiler plate code
        ButterKnife.bind(this);
        // Load the data from Asset folder -json file
        List<FirstSetTransactionModel> firstSetTransactionModels = loadJSONFromAsset();
        //handle the data collection and bind it to the view  -- here i'm passing in sample fake data to display in recycler view
        ProductAdapter productAdapter = new ProductAdapter(this, loadJSONFromAsset());
        //attach the adapter to recycler view to populate data/items
        productRecyclerView.setAdapter(productAdapter);
        // set layout manager to position the items
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<FirstSetTransactionModel> loadJSONFromAsset() {
        List<FirstSetTransactionModel> firstSetTransactionModelArrayList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("transactions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FirstSetTransactionModel firstSetTransactionData = new FirstSetTransactionModel();
                firstSetTransactionData.setAmount(jsonObject.getString("amount"));
                firstSetTransactionData.setSku(jsonObject.getString("sku"));
                firstSetTransactionData.setCurrency(jsonObject.getString("currency"));

                //add all values to ArrayList
                firstSetTransactionModelArrayList.add(firstSetTransactionData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return firstSetTransactionModelArrayList;
    }
}
