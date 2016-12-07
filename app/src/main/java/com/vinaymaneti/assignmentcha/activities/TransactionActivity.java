package com.vinaymaneti.assignmentcha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.adapter.TransactionAdapter;
import com.vinaymaneti.assignmentcha.model.FirstSetRatesModel;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;
import com.vinaymaneti.assignmentcha.util.Constants;
import com.vinaymaneti.assignmentcha.util.ParseJson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionActivity extends AppCompatActivity {
    private static final String TAG = TransactionActivity.class.getSimpleName();
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

        List<FirstSetRatesModel> firstSetRatesModels = ParseJson.loadRatesJSONFromAsset(this, Constants.RATES_JSON);

        Intent intent = this.getIntent();
        if (intent != null) {
            transactionName = intent.getStringExtra(Constants.TRANSACTION_NAME);
            firstSetTransactionModels = intent.getParcelableArrayListExtra(Constants.DATA);
        }

        for (FirstSetTransactionModel setTransactionModel : firstSetTransactionModels) {
            double result = convertFromXToGBP(firstSetRatesModels, setTransactionModel);
            DecimalFormat decimalFormat = new DecimalFormat(getString(R.string.after_convert_to_gbp_decimal_format));
            double rawPercent = result * 1.00;
            setTransactionModel.setConvertedCurrency("" + decimalFormat.format(rawPercent));
        }

        //here after converting all the currency to USD need to add
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(',');
        DecimalFormat formatter = new DecimalFormat(getString(R.string.total_gbp_decimal_format), symbols);
        totalTextView.setText(getString(R.string.total_str) + TransactionAdapter.CurrencyType.fromString(getString(R.string.strig_gbp)) + formatter.format(sumOfAllTransaction()) + "");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(String.format("%s %s", getString(R.string.transaction_for), transactionName));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Load the data from Asset folder -json file
        //handle the data collection and bind it to the view  -- here i'm passing in sample fake data to display in recycler view
        TransactionAdapter productAdapter = new TransactionAdapter(this, firstSetTransactionModels);
        //attach the adapter to recycler view to populate data/items
        transactionRecyclerView.setAdapter(productAdapter);
        // set layout manager to position the items
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private double convertFromXToGBP(List<FirstSetRatesModel> firstSetRatesModels, FirstSetTransactionModel from) {
        //if from currency and is Equal to GBp no need to convert
        if (from.getCurrency().equalsIgnoreCase(getString(R.string.strig_gbp)))
            return Double.parseDouble(from.getAmount());
        //if from currency is different then compare with json rates and get the rate from json
        double rate = findRate(firstSetRatesModels, from.getCurrency(), getString(R.string.strig_gbp));
        //if rate is greater then value 0 then multiply it with amount
        if (rate != 0) {
            return Double.parseDouble(from.getAmount()) * rate;
        } else {
            //if rate is less than zero mean  there there is no direct conversion
            // then we need to convert to the currency into appropriate one to give final currency
            //then we ge teh model
            FirstSetRatesModel model;
            boolean repeatProcessConversion = false;
            double rateOfFromAndToCurrency;
            double newAmount;
            double newRate;
            double finalAmount = 0;
            double nextRepeatAmount = 0;
            String nextRepeatCurrency = null;
            do {
                if (nextRepeatAmount != 0)
                    model = findAvailable(firstSetRatesModels, nextRepeatCurrency);
                else
                    model = findAvailable(firstSetRatesModels, from.getCurrency());
                if (model != null) {
                    String toCurrency = model.getTo();
                    if (nextRepeatAmount != 0) {
                        rateOfFromAndToCurrency = findRate(firstSetRatesModels, model.getFrom(), model.getTo());
                        newAmount = nextRepeatAmount * rateOfFromAndToCurrency;
                    } else {
                        rateOfFromAndToCurrency = findRate(firstSetRatesModels, from.getCurrency(), toCurrency);
                        newAmount = Double.parseDouble(from.getAmount()) * rateOfFromAndToCurrency;
                    }

                    newRate = findRate(firstSetRatesModels, toCurrency, getString(R.string.strig_gbp));
                    if (newRate != 0) {
                        //if new one greater than multiply newAmount * newRate
                        repeatProcessConversion = true;
                        finalAmount = newAmount * newRate;
                    } else {
                        repeatProcessConversion = false;
                        nextRepeatAmount = newAmount;
                        nextRepeatCurrency = toCurrency;
                    }
                }
            } while (!repeatProcessConversion);
            return finalAmount;
        }
    }

//    private double getFromOutSide() {
//        // Do something
//        return 0;
//    }


    private double findRate(List<FirstSetRatesModel> firstSetRatesModels, String from, String to) {
        for (FirstSetRatesModel model : firstSetRatesModels) {
            if (model.getFrom().equalsIgnoreCase(from) && model.getTo().equalsIgnoreCase(to)) {
                return Double.parseDouble(model.getRate());
            }
        }
        return 0;
    }

    private FirstSetRatesModel findAvailable(List<FirstSetRatesModel> firstSetRatesModels, String from) {
        for (FirstSetRatesModel model : firstSetRatesModels) {
            if (model.getFrom().equalsIgnoreCase(from)) {
                return model;
            }
        }
        return null;
    }


    private double sumOfAllTransaction() {
        double sum = 0;
        for (FirstSetTransactionModel setTransactionModel : firstSetTransactionModels) {
            sum = sum + Double.parseDouble(setTransactionModel.getConvertedCurrency());
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
