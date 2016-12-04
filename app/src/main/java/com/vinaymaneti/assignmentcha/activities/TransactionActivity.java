package com.vinaymaneti.assignmentcha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.adapter.TransactionAdapter;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.totalTextView)
    TextView totalTextView;

    @BindView(R.id.transactionRecyclerView)
    RecyclerView transactionRecyclerView;
    private String transactionName = null;
    private List<FirstSetTransactionModel> firstSetTransactionModels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        if (intent != null) {
            transactionName = intent.getStringExtra("transactionName");
            firstSetTransactionModels = intent.getParcelableArrayListExtra("data");
            Log.d("bundle data::-", firstSetTransactionModels.size() + "");
        }

        //here after converting all the currency to USD need to add
//        totalTextView.setText("Total :- " + sumOfAllTransaction() + "");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(transactionName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Load the data from Asset folder -json file
//        List<FirstSetTransactionModel> firstSetTransactionModels = loadJSONFromAsset(this, "transactions.json");
        //handle the data collection and bind it to the view  -- here i'm passing in sample fake data to display in recycler view
        TransactionAdapter productAdapter = new TransactionAdapter(this, firstSetTransactionModels);
        //attach the adapter to recycler view to populate data/items
        transactionRecyclerView.setAdapter(productAdapter);
        // set layout manager to position the items
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private double sumOfAllTransaction() {
        double sum = 0;
        for (FirstSetTransactionModel setTransactionModel : firstSetTransactionModels) {
            sum = sum + Double.parseDouble(setTransactionModel.getAmount());
        }
        return sum;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, ProductActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
