package com.vinaymaneti.assignmentcha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vinaymaneti.assignmentcha.adapter.ProductAdapter;
import com.vinaymaneti.assignmentcha.model.FakeDataForeRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ProductAdapter productAdapter = new ProductAdapter(this, FakeDataForeRecyclerView.createFakeDatProductList());
        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
