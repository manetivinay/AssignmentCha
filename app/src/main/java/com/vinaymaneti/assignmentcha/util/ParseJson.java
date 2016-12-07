package com.vinaymaneti.assignmentcha.util;

import android.content.Context;

import com.vinaymaneti.assignmentcha.R;
import com.vinaymaneti.assignmentcha.model.FirstSetRatesModel;
import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 04/12/16.
 */

public class ParseJson {
    public static List<FirstSetTransactionModel> loadJSONFromAsset(Context context, String jsonFileName) {
        List<FirstSetTransactionModel> firstSetTransactionModelArrayList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFileName);
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
                firstSetTransactionData.setAmount(jsonObject.getString(context.getString(R.string.json_transaction_amount)));
                firstSetTransactionData.setSku(jsonObject.getString(context.getString(R.string.json_transaction_sku)));
                firstSetTransactionData.setCurrency(jsonObject.getString(context.getString(R.string.json_transaction_currency)));

                //add all values to ArrayList
                firstSetTransactionModelArrayList.add(firstSetTransactionData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return firstSetTransactionModelArrayList;
    }


    public static List<FirstSetRatesModel> loadRatesJSONFromAsset(Context context, String jsonFileName) {
        List<FirstSetRatesModel> firstSetTransactionModels = new ArrayList<>();
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFileName);
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
                FirstSetRatesModel firstSetRatesModel = new FirstSetRatesModel();
                firstSetRatesModel.setFrom(jsonObject.getString(context.getString(R.string.json_rates_from)));
                firstSetRatesModel.setRate(jsonObject.getString(context.getString(R.string.json_rates_rate)));
                firstSetRatesModel.setTo(jsonObject.getString(context.getString(R.string.json_rates_to)));

                //add all values to ArrayList
                firstSetTransactionModels.add(firstSetRatesModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return firstSetTransactionModels;
    }
}
