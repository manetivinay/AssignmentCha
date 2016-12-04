package com.vinaymaneti.assignmentcha.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.adapter.ProductAdapter;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vinaymaneti.assignmentcha.util.ParseJson.loadJSONFromAsset;

public class ProductActivity extends AppCompatActivity {

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Here I use butter knife to eliminate boiler plate code
        ButterKnife.bind(this);
        // Load the data from Asset folder -json file
        List<FirstSetTransactionModel> firstSetTransactionModels = loadJSONFromAsset(this, "transactions.json");
        //handle the data collection and bind it to the view  -- here i'm passing in sample fake data to display in recycler view
        ProductAdapter productAdapter = new ProductAdapter(this, firstSetTransactionModels);
        //attach the adapter to recycler view to populate data/items
        productRecyclerView.setAdapter(productAdapter);
        // set layout manager to position the items
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
